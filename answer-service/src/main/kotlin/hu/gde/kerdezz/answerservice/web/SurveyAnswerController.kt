package hu.gde.kerdezz.answerservice.web

import hu.gde.kerdezz.answerservice.domain.Answer
import hu.gde.kerdezz.answerservice.domain.SurveyAnswer
import hu.gde.kerdezz.answerservice.dto.AnswerDto
import hu.gde.kerdezz.answerservice.dto.SurveyAnswerDto
import hu.gde.kerdezz.answerservice.repository.SurveyAnswerRepository
import hu.gde.kerdezz.answerservice.service.AnswerValidatorService
import hu.gde.kerdezz.answerservice.service.SurveyTemplateService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/surveys")
class SurveyAnswerController(
  private val templateService: SurveyTemplateService,
  private val surveyAnswerRepository: SurveyAnswerRepository,
  private val answerValidatorService: AnswerValidatorService
) {
  private val logger = LoggerFactory.getLogger(javaClass)

  @PostMapping("/answers")
  fun submitSurveyAnswers(@Valid @RequestBody surveyAnswerDto: SurveyAnswerDto): ResponseEntity<Unit> {
    logger.info("answers for template id: {}", surveyAnswerDto.surveyId)
    if (!answerValidatorService.validate(surveyAnswerDto)) {
      logger.warn("Invalid answer, templateId: {}", surveyAnswerDto.surveyId)
      throw ResponseStatusException(HttpStatus.BAD_REQUEST)
    }
    surveyAnswerRepository.save(dtoToSurveyAnswer(surveyAnswerDto, getCurrentUser()))
    return ResponseEntity.ok().build()
  }

  @GetMapping("/answers/{surveyId}")
  fun getSurveyAnswers(@PathVariable surveyId: String): ResponseEntity<List<SurveyAnswerDto>> {
    val currentUser = getCurrentUser()
    if (!templateService.isOwnerOfSurvey(currentUser, surveyId)) {
      logger.warn("User {} is not owner of the survey with id {}", currentUser, surveyId)
      throw ResponseStatusException(HttpStatus.FORBIDDEN)
    }
    val surveyAnswers: List<SurveyAnswerDto> = surveyAnswerRepository.findBySurveyId(surveyId).map { surveyAnswer -> surveyAnswerToDto(surveyAnswer) }
    return ResponseEntity.ok(surveyAnswers)
  }

  private fun surveyAnswerToDto(surveyAnswer: SurveyAnswer): SurveyAnswerDto {
    return SurveyAnswerDto(
      id = surveyAnswer.id,
      owner = surveyAnswer.owner,
      surveyId = surveyAnswer.surveyId,
      answers = surveyAnswer.answers.map { AnswerDto(questionId = it.questionId, value = it.value, multiValue = it.multiValue) }
    )
  }
}

fun getCurrentUser(): String {
  return SecurityContextHolder.getContext().authentication?.name ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
}

fun dtoToSurveyAnswer(surveyAnswerDto: SurveyAnswerDto, user: String): SurveyAnswer {
  return SurveyAnswer(
    id = null,
    owner = user,
    surveyId = surveyAnswerDto.surveyId,
    answers = surveyAnswerDto.answers.map { dtoToAnswer(it) }
  )
}

fun dtoToAnswer(answerDTO: AnswerDto): Answer {
  return Answer(
    questionId = answerDTO.questionId,
    value = answerDTO.value,
    multiValue = answerDTO.multiValue
  )
}
