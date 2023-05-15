package hu.gde.kerdezz.statisticsservice.dto

data class StatSurveyAnswer(
  val surveyId: String,
  val answers: List<StatAnswer>
)
