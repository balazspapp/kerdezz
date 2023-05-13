package hu.gde.kerdezz.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository
import org.springframework.security.web.server.SecurityWebFilterChain

@SpringBootApplication
@EnableDiscoveryClient
class GatewayApplication {
  @Bean
  fun customSecurityFilterChain(http: ServerHttpSecurity, reactiveClientRegistrationRepository: ReactiveClientRegistrationRepository): SecurityWebFilterChain {
//    val oidcClientInitiatedLogoutSuccessHandler = OidcClientInitiatedServerLogoutSuccessHandler(reactiveClientRegistrationRepository)
//    oidcClientInitiatedLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}/home")
    return http
      .authorizeExchange().anyExchange().authenticated()
      .and()
      .oauth2Login()
      .and()
      .csrf().disable()
      .build()
    //todo: fix logout
//      .csrf().and().cors().disable()
//      .logout().logoutSuccessHandler(oidcClientInitiatedLogoutSuccessHandler)
//      .and().build()
  }
}

fun main(args: Array<String>) {
  runApplication<GatewayApplication>(*args)
}
