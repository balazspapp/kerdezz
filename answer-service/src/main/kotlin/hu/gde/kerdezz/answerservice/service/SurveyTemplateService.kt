package hu.gde.kerdezz.answerservice.service

import hu.gde.kerdezz.answerservice.domain.SurveyTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient

@Service
class SurveyTemplateService(
  @Autowired private val restTemplate: RestTemplate,
  @Value("\${template-service-url}") private val templateServiceUrl: String,
  @Autowired val webClient: WebClient
) {

  fun getSurveyById(id: String): SurveyTemplate {
    return this.webClient
      .get()
      .uri("$templateServiceUrl/$id")
      .attributes(clientRegistrationId("answer-service-client-credentials"))
      .retrieve()
      .bodyToMono(SurveyTemplate::class.java)
      .block() ?: throw RuntimeException("No template found")
  }
}
