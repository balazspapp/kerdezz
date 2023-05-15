package hu.gde.kerdezz.statisticsservice.repository

import hu.gde.kerdezz.statisticsservice.domain.SurveyStats
import org.springframework.data.jpa.repository.JpaRepository

interface SurveyStatsRepository : JpaRepository<SurveyStats, Long> {
  fun findBySurveyId(surveyId: String): SurveyStats?
}
