package hu.gde.kerdezz.statisticsservice.messaging

import hu.gde.kerdezz.statisticsservice.dto.StatSurveyAnswer
import hu.gde.kerdezz.statisticsservice.service.AnswerStatsService
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import java.util.function.Consumer

open class StatSurveyAnswerConsumer(
  val answerStatsService: AnswerStatsService
) : Consumer<StatSurveyAnswer> {
  private val logger = LoggerFactory.getLogger(javaClass)

  @Transactional
  override fun accept(surveyAnswerDto: StatSurveyAnswer) {
    logger.info("Survey answer received, surveyId: {}", surveyAnswerDto.surveyId)
    answerStatsService.generate(surveyAnswerDto)
  }
}
