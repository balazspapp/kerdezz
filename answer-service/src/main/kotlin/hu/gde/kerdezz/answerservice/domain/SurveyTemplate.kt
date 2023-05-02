package hu.gde.kerdezz.answerservice.domain

import java.time.LocalDate

data class SurveyTemplate(
  val id: String?,
  val name: String,
  val text: String?,
  val anonymous: Boolean,
  val multiCompletion: Boolean,
  val visibility: Visibility,
  val questions: List<Question>?
)

data class Question(
  val id: String?,
  val required: Boolean = false,
  val questionText: String,
  val questionType: QuestionType,
  val options: List<OptionDto>?,
  val minValue: Int?,
  val maxValue: Int?,
  val minDate: LocalDate?,
  val maxDate: LocalDate?
)

data class OptionDto (val value: String)

enum class QuestionType {
  SIMPLE_CHOICE, MULTI_CHOICE, OPEN_TEXT, DATE, NUMBER, EMAIL
}

enum class Visibility {
  public, invite_only, private
}
