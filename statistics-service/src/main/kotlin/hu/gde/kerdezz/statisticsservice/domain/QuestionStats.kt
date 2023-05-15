package hu.gde.kerdezz.statisticsservice.domain

import hu.gde.kerdezz.statisticsservice.dto.QuestionType
import jakarta.persistence.*

@Entity
@Table(name = "question_stats")
class QuestionStats(
  @Column(unique = true)
  var questionId: String? = null,
  var type: QuestionType? = null,
  var count: Int = 0,
  var sum: Double = 0.0,
  @ManyToOne
  var surveyStats: SurveyStats? = null,
  @OneToMany(mappedBy = "questionStats", cascade = [CascadeType.ALL], orphanRemoval = true)
  var questionStats: List<AnswerValueStats> = mutableListOf()
) : AbstractJpaPersistable<Long>()
