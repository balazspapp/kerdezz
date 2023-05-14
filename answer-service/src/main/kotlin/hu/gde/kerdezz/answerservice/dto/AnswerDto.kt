package hu.gde.kerdezz.answerservice.dto

data class AnswerDto(
  val questionId: String,
  val value: String?,
  val multiValue: List<String>?
)
