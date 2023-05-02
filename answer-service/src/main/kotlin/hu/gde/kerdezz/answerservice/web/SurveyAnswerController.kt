package hu.gde.kerdezz.answerservice.web

import hu.gde.kerdezz.answerservice.domain.SurveyAnswer
import hu.gde.kerdezz.answerservice.repository.SurveyAnswerRepository
import hu.gde.kerdezz.answerservice.service.SurveyTemplateService
import hu.gde.kerdezz.answerservice.service.AnswerValidatorService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/surveys")
class SurveyAnswerController(
  private val templateService: SurveyTemplateService,
  private val surveyAnswerService: SurveyAnswerRepository,
  private val answerValidatorService: AnswerValidatorService
) {
  private val logger = LoggerFactory.getLogger(javaClass)

  @PostMapping("/answers")
  fun submitSurveyAnswers(@Valid @RequestBody surveyAnswer: SurveyAnswer): ResponseEntity<Unit> {
    val template = templateService.getTemplateById(surveyAnswer.surveyId)
    logger.info("template id: {}, name: {}", template?.id, template?.name)
    val valid = answerValidatorService.validate(surveyAnswer)
    if (!valid) {
      throw ResponseStatusException(HttpStatus.BAD_REQUEST)
    }
    surveyAnswerService.save(surveyAnswer)
    return ResponseEntity.ok().build()
  }
}
