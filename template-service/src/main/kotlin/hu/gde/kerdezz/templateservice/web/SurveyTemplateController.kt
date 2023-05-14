package hu.gde.kerdezz.templateservice.web

import hu.gde.kerdezz.templateservice.domain.Survey
import hu.gde.kerdezz.templateservice.domain.Visibility
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
          if (it.owner != currentUser) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "have no permissions to update")
          }
        }
    }

    val invitedUsers = if (request.visibility == Visibility.invite_only) request.allowedEmails else null
    val survey = request.mapDtoToSurvey(currentUser).copy(allowedEmails = invitedUsers)
    surveyRepository.save(survey)
    return ResponseEntity.ok().build()
  }

  @GetMapping
  fun getTemplates(@RequestParam(required = false, defaultValue = "0") page: Int): ResponseEntity<Page<SurveyDto>> {
    logger.info("get questionnaires for page: {}", page)
    val pageSize = 10
    val currentUser = getCurrentUser()
    surveyRepository.findAll(PageRequest.of(page, pageSize))
    val surveys = surveyRepository.findByVisibilityOrOwner(Visibility.public, currentUser, PageRequest.of(page, pageSize))
    val dtos = surveys.map { it.mapSurveyToDto(isSurveyEditable(it, currentUser)) }
    return ResponseEntity.ok(dtos)
  }

  @GetMapping("/{id}")
  fun getTemplate(@PathVariable id: String): ResponseEntity<SurveyDto> {
    logger.info("get questionnaire with id: {}", id)
    val survey = surveyRepository.findById(id)
    return survey.map { ResponseEntity.ok(it.mapSurveyToDto(false)) }
      .orElse(ResponseEntity.notFound().build())
  }

  private fun isSurveyEditable(it: Survey, currentUser: String) = it.owner == currentUser
}

fun getCurrentUser(): String {
  return SecurityContextHolder.getContext().authentication?.name ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
}


