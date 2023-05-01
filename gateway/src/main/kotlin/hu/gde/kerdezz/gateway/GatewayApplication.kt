package hu.gde.kerdezz.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableDiscoveryClient
class GatewayApplication {
  @Bean
  fun gatewayRoutes(builder: RouteLocatorBuilder): RouteLocator = builder.routes()
    .route("templateService") { r ->
      r.path("/api/templates/**")
        .uri("http://localhost:8082")
    }
    .route("answerService") { r ->
      r.path("/api/surveys/**")
        .uri("http://localhost:8083")
    }
    .build()
}

fun main(args: Array<String>) {
  runApplication<GatewayApplication>(*args)
}
