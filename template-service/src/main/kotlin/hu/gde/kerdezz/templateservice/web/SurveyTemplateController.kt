package hu.gde.kerdezz.templateservice.web

import hu.gde.kerdezz.templateservice.repository.SurveyRepository
import hu.gde.kerdezz.templateservice.web.dto.QuestionnaireDto
import hu.gde.kerdezz.templateservice.web.dto.mapDtoToSurvey
import hu.gde.kerdezz.templateservice.web.dto.mapSurveyToDto
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/templates")
class SurveyTemplateController(val surveyRepository: SurveyRepository) {
  private val logger = LoggerFactory.getLogger(javaClass)

  @PostMapping
  fun createNewTemplate(@RequestBody request: QuestionnaireDto): ResponseEntity<Unit> {
    logger.info("new questionnaire: {}", request)
    surveyRepository.save(mapDtoToSurvey(request))
    return ResponseEntity.ok().build()
  }

  @GetMapping
  fun getTemplates(): ResponseEntity<Page<QuestionnaireDto>> {
    logger.info("get questionnaires")
    val surveys = surveyRepository.findAll(PageRequest.of(0, 10))
    val dtos = surveys.map { mapSurveyToDto(it) }
    return ResponseEntity.ok(dtos)
  }

  @GetMapping("/{id}")
  fun getTemplate(@PathVariable id: String): ResponseEntity<QuestionnaireDto> {
    logger.info("get questionnaire with id: {}", id)
    val survey = surveyRepository.findById(id)
    return survey.map { ResponseEntity.ok(mapSurveyToDto(it)) }
      .orElse(ResponseEntity.notFound().build())
  }
}
