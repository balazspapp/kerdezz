package hu.gde.kerdezz.statisticsservice.domain

import jakarta.persistence.*

@Entity
@Table(name = "survey_stats", indexes = [Index(name = "idx_survey_stats_survey_id", columnList = "surveyId")])
class SurveyStats(
  @Column(unique = true)
  var surveyId: String? = null,
  var completionCount: Int = 0,
  @OneToMany(mappedBy = "surveyStats", cascade = [CascadeType.ALL], orphanRemoval = true)
  var answers: List<QuestionStats> = listOf()
) : AbstractJpaPersistable<Long>()

