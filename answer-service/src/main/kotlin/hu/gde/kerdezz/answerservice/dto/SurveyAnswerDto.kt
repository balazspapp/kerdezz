package hu.gde.kerdezz.answerservice.dto

data class SurveyAnswerDto(
  val id: String?,
  val owner: String? = null,
  val surveyId: String,
  val answers: List<AnswerDto> = listOf()
)

