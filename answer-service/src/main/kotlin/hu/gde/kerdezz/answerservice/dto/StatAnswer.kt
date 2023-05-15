package hu.gde.kerdezz.answerservice.dto

data class StatAnswer(
  val type: QuestionType,
  val questionId: String,
  val value: String?,
  val multiValue: List<String>?
)

