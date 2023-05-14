package hu.gde.kerdezz.answerservice.web

import hu.gde.kerdezz.answerservice.dto.SurveyAnswerDto
import hu.gde.kerdezz.answerservice.service.SurveyAnswerService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/surveys")
class SurveyAnswerController(
  private val surveyAnswerService: SurveyAnswerService
) {
  private val logger = LoggerFactory.getLogger(javaClass)

  @PostMapping("/answers")
  fun submitSurveyAnswers(@Valid @RequestBody surveyAnswerDto: SurveyAnswerDto): ResponseEntity<Unit> {
    logger.info("answers for template id: {}", surveyAnswerDto.surveyId)
    surveyAnswerService.save(surveyAnswerDto)
    return ResponseEntity.ok().build()
  }

  @GetMapping("/answers/{surveyId}")
  fun getSurveyAnswers(@PathVariable surveyId: String): ResponseEntity<List<SurveyAnswerDto>> {
    val surveyAnswers: List<SurveyAnswerDto> = surveyAnswerService.findAllBySurveyId(surveyId)
    return ResponseEntity.ok(surveyAnswers)
  }

}
