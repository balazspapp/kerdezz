package hu.gde.kerdezz.answerservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@EnableDiscoveryClient
@SpringBootApplication
class AnswerServiceApplication {
  @LoadBalanced
  @Bean
  fun restTemplate() = RestTemplate()
}

fun main(args: Array<String>) {
  runApplication<AnswerServiceApplication>(*args)
}
