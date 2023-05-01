package hu.gde.kerdezz.answerservice.service

import hu.gde.kerdezz.answerservice.domain.SurveyTemplate
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class SurveyTemplateService(
  @Autowired private val restTemplate: RestTemplate,
  @Value("\${template-service-url}") private val templateServiceUrl: String
) {
  private val logger = LoggerFactory.getLogger(javaClass)

  fun getTemplateById(id: String): SurveyTemplate {
    val url = "$templateServiceUrl/$id"
    return try {
      restTemplate.getForObject(url, SurveyTemplate::class.java) ?: throw RuntimeException()
    } catch (ex: Exception) {
      logger.error("Could not get survey template", ex)
      throw RuntimeException("No template found")
    }
  }
}
