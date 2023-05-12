package hu.gde.kerdezz.answerservice.web

import hu.gde.kerdezz.answerservice.dto.SurveyAnswerRequest
import hu.gde.kerdezz.answerservice.dto.requestToSurveyAnswer
import hu.gde.kerdezz.answerservice.repository.SurveyAnswerRepository
import hu.gde.kerdezz.answerservice.service.AnswerValidatorService
import hu.gde.kerdezz.answerservice.service.SurveyTemplateService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
  fun submitSurveyAnswers(@Valid @RequestBody surveyAnswerRequest: SurveyAnswerRequest): ResponseEntity<Unit> {
    logger.info("answers for template id: {}", surveyAnswerRequest.surveyId)
    if (!answerValidatorService.validate(surveyAnswerRequest)) {
      logger.warn("Invalid answer, templateId: {}", surveyAnswerRequest.surveyId)
      throw ResponseStatusException(HttpStatus.BAD_REQUEST)
    }
    surveyAnswerRepository.save(requestToSurveyAnswer(surveyAnswerRequest, getCurrentUser()))
    return ResponseEntity.ok().build()
  }
}

fun getCurrentUser(): String {
  return SecurityContextHolder.getContext().authentication?.name ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
}
