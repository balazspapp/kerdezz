package hu.gde.kerdezz.statisticsservice.config

import hu.gde.kerdezz.statisticsservice.dto.StatSurveyAnswer
import hu.gde.kerdezz.statisticsservice.messaging.StatSurveyAnswerConsumer
import hu.gde.kerdezz.statisticsservice.service.AnswerStatsService
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.function.Consumer

@Configuration
class SurveyAnswerListenerConfig {

  @Bean
  fun surveyAnswerTopic(answerStatsService: AnswerStatsService): Consumer<StatSurveyAnswer> = StatSurveyAnswerConsumer(answerStatsService)
}
