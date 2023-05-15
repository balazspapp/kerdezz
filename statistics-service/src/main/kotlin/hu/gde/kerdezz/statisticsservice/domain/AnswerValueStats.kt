package hu.gde.kerdezz.statisticsservice.domain

import jakarta.persistence.*

@Entity
@Table(
  name = "answer_value_stats", uniqueConstraints = [
    UniqueConstraint(columnNames = ["answer_column", "question_stats_id"])
  ]
)
class AnswerValueStats(
  var count: Long = 0L,
  @Column(name = "answer_column", nullable = false)
  var answerValue: String? = null,
  @ManyToOne
  @JoinColumn(name = "question_stats_id", nullable = false)
  var questionStats: QuestionStats? = null
) : AbstractJpaPersistable<Long>()
