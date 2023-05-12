package hu.gde.kerdezz.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@SpringBootApplication
@EnableDiscoveryClient
class GatewayApplication {
  @Bean
  fun customSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
    return http
      .authorizeExchange().anyExchange().authenticated()
      .and()
      .oauth2Login()
      .and()
      .csrf().disable()
      .build()
  }
}

fun main(args: Array<String>) {
  runApplication<GatewayApplication>(*args)
}
