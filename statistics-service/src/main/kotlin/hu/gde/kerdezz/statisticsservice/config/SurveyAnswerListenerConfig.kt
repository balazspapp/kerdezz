package hu.gde.kerdezz.statisticsservice.config

import hu.gde.kerdezz.statisticsservice.domain.SurveyAnswerDto
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.Consumer

@Configuration
class SurveyAnswerListenerConfig {
  private val logger = LoggerFactory.getLogger(javaClass)

  @Bean
  fun surveyAnswerTopic(): Consumer<SurveyAnswerDto> {
    return Consumer { surveyAnswerDto: SurveyAnswerDto ->
        logger.info("******************** Received: $surveyAnswerDto")
    }
  }
}
