package hu.gde.kerdezz.answerservice.config

import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.reactive.function.client.WebClient


@Configuration
class WebClientConfig {

  @Bean
  fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
    return http.authorizeHttpRequests { it.anyRequest().authenticated() }
      .oauth2ResourceServer(OAuth2ResourceServerConfigurer<HttpSecurity?>::jwt)
      .build()
  }

  @Bean
  @LoadBalanced
  fun webClientBuilder(): WebClient.Builder {
    return WebClient.builder()
  }

  @Bean
  fun webClient(authorizedClientManager: OAuth2AuthorizedClientManager, webClientBuilder: WebClient.Builder): WebClient {
    val oauth2Client = ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager)
    return webClientBuilder
      .apply(oauth2Client.oauth2Configuration())
      .build()
  }

  @Bean
  fun authorizedClientManager(
    clientRegistrationRepository: ClientRegistrationRepository,
    authorizedClientRepository: OAuth2AuthorizedClientRepository
  ): OAuth2AuthorizedClientManager {
    val authorizedClientProvider: OAuth2AuthorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
      .clientCredentials()
      .build()
    val authorizedClientManager = DefaultOAuth2AuthorizedClientManager(
      clientRegistrationRepository, authorizedClientRepository
    )
    authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider)
    return authorizedClientManager
  }
}
