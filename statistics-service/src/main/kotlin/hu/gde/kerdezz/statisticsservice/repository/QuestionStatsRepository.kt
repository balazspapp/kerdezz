package hu.gde.kerdezz.statisticsservice.repository

import hu.gde.kerdezz.statisticsservice.domain.QuestionStats
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionStatsRepository  : JpaRepository<QuestionStats, Long>
