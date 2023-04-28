package hu.gde.kerdezz.templateservice.web

import hu.gde.kerdezz.templateservice.web.dto.NewQuestionnaireDto
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/templates")
class SurveyTemplateController {
  private val logger = LoggerFactory.getLogger(javaClass)

  @PostMapping
  fun getTemplates(@RequestBody request: NewQuestionnaireDto): ResponseEntity<String> {
    logger.info("new questionnaire: {}", request)
    return ResponseEntity.ok("ok")
  }
}
