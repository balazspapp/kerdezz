package hu.gde.kerdezz.answerservice.domain

data class SurveyAnswer(
  val surveyId: String,
  val answers: List<Answer>
)

data class Answer(
  val questionId: String,
  val value: String?,
  val multiValue: List<String>?
)
