package hu.gde.kerdezz.statisticsservice.domain

import java.time.Instant

data class SurveyAnswerDto(
  val id: String?,
  val owner: String? = null,
  val email: String? = null,
  val surveyId: String,
  val createdAt: Instant?= null,
  val answers: List<AnswerDto> = listOf()
)

