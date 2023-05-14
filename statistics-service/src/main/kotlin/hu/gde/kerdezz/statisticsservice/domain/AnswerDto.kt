package hu.gde.kerdezz.statisticsservice.domain

data class AnswerDto(
  val questionId: String,
  val value: String?,
  val multiValue: List<String>?
)
