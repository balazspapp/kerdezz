package hu.gde.kerdezz.templateservice.web

import hu.gde.kerdezz.templateservice.repository.SurveyRepository
import hu.gde.kerdezz.templateservice.web.dto.SurveyDto
import hu.gde.kerdezz.templateservice.web.dto.mapDtoToSurvey
import hu.gde.kerdezz.templateservice.web.dto.mapSurveyToDto
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("api/templates")
class SurveyTemplateController(val surveyRepository: SurveyRepository) {
  private val logger = LoggerFactory.getLogger(javaClass)

  @PostMapping
  fun saveOrUpdateTemplate(@RequestBody request: SurveyDto): ResponseEntity<Unit> {
    logger.info("Saving or updating questionnaire: {}", request)
    val currentUser = getCurrentUser()

    if (request.id != null) {
      surveyRepository.findById(request.id)
        .ifPresent {
          if (it.user != currentUser) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "have no permissions to update")
          }
        }
    }

    surveyRepository.save(request.mapDtoToSurvey(currentUser))
    return ResponseEntity.ok().build()
  }

  @GetMapping
  fun getTemplates(@RequestParam(required = false, defaultValue = "0") page: Int): ResponseEntity<Page<SurveyDto>> {
    logger.info("get questionnaires for page: {}", page)
    val pageSize = 10
    val surveys = surveyRepository.findAll(PageRequest.of(page, pageSize))
    val dtos = surveys.map { it.mapSurveyToDto() }
    return ResponseEntity.ok(dtos)
  }

  @GetMapping("/{id}")
  fun getTemplate(@PathVariable id: String): ResponseEntity<SurveyDto> {
    logger.info("get questionnaire with id: {}", id)
    val survey = surveyRepository.findById(id)
    return survey.map { ResponseEntity.ok(it.mapSurveyToDto()) }
      .orElse(ResponseEntity.notFound().build())
  }
}

fun getCurrentUser(): String {
  return SecurityContextHolder.getContext().authentication?.name ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
}


//val currentUser = getCurrentUser()
//if (request.id == null) {
//  surveyRepository.save(request.mapDtoToSurvey(currentUser))
//} else {
//  val oldSurvey = surveyRepository.findById(request.id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST))
//  if (oldSurvey.isPresent && currentUser == oldSurvey.)
//}
