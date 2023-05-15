package hu.gde.kerdezz.answerservice.dto

data class StatSurveyAnswer(
  val surveyId: String,
  val answers: List<StatAnswer>
)
