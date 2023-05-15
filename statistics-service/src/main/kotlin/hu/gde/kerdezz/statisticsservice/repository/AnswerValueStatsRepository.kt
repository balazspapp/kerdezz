package hu.gde.kerdezz.statisticsservice.repository

import hu.gde.kerdezz.statisticsservice.domain.AnswerValueStats
import hu.gde.kerdezz.statisticsservice.domain.QuestionStats
import org.springframework.data.jpa.repository.JpaRepository

interface AnswerValueStatsRepository  : JpaRepository<AnswerValueStats, Long> {
  fun findByQuestionStatsAndAnswerValue(questionStats: QuestionStats, answerValue: String): AnswerValueStats?
}
