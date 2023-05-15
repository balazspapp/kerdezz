package hu.gde.kerdezz.answerservice.dto

data class SurveyTemplate(
  val id: String,
  val owner: String,
  val name: String,
  val text: String?,
  val anonymous: Boolean,
  val multiCompletion: Boolean,
  val visibility: Visibility,
  val questions: List<Question>,
  val allowedEmails: List<String>?
)

