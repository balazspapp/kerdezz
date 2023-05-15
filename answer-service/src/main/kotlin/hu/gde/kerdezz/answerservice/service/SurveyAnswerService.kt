package hu.gde.kerdezz.answerservice.service

import hu.gde.kerdezz.answerservice.dto.*
import hu.gde.kerdezz.answerservice.mappers.mapDtoWithUser
import hu.gde.kerdezz.answerservice.mappers.surveyAnswerToDto
import hu.gde.kerdezz.answerservice.repository.SurveyAnswerRepository
import hu.gde.kerdezz.answerservice.security.getCurrentUser
import hu.gde.kerdezz.answerservice.security.getCurrentUserInfo
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class SurveyAnswerService(
  private val streamBridge: StreamBridge,
  private val templateService: SurveyTemplateService,
  private val surveyAnswerRepository: SurveyAnswerRepository,
  private val answerValidatorService: AnswerValidatorService
) {
  private val logger = LoggerFactory.getLogger(javaClass)

  fun save(surveyAnswerDto: SurveyAnswerDto) {
    val surveyTemplate = templateService.getSurveyById(surveyAnswerDto.surveyId)
    validateAnswer(surveyAnswerDto, surveyTemplate)

    val answerMap: Map<String, AnswerDto> = surveyAnswerDto.answers.associateBy { it.questionId }
    val statSurveyAnswer = StatSurveyAnswer(
      surveyId = surveyAnswerDto.surveyId,
      answers = surveyTemplate.questions.map {
        StatAnswer(
          questionId = it.id,
          type = it.questionType,
          value = answerMap[it.id]?.value,
          multiValue = answerMap[it.id]?.multiValue
        )
      })
    this.streamBridge.send("surveyAnswerSupplier-out-0", statSurveyAnswer)
    surveyAnswerRepository.save(mapDtoWithUser(surveyAnswerDto, getCurrentUserInfo(surveyTemplate.anonymous)))
  }

  fun findAllBySurveyId(surveyId: String): List<SurveyAnswerDto> {
    val currentUser = getCurrentUser()
    if (!templateService.isOwnerOfSurvey(currentUser, surveyId)) {
      logger.warn("User {} is not owner of the survey with id {}", currentUser, surveyId)
      throw ResponseStatusException(HttpStatus.FORBIDDEN)
    }
    return surveyAnswerRepository.findBySurveyId(surveyId).map { surveyAnswer -> surveyAnswerToDto(surveyAnswer) }
  }

  private fun validateAnswer(surveyAnswerDto: SurveyAnswerDto, surveyTemplate: SurveyTemplate) {
    if (!answerValidatorService.validate(surveyAnswerDto, surveyTemplate)) {
      logger.warn("Invalid answer, templateId: {}", surveyAnswerDto.surveyId)
      throw ResponseStatusException(HttpStatus.BAD_REQUEST)
    }
  }
}
