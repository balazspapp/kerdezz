package hu.gde.kerdezz.templateservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class TemplateServiceApplication

fun main(args: Array<String>) {
  runApplication<TemplateServiceApplication>(*args)
}
