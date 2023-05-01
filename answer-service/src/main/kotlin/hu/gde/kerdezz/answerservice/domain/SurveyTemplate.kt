package hu.gde.kerdezz.answerservice.domain

import java.time.LocalDate

data class SurveyTemplate(
  val id: String?,
  val name: String,
  val text: String?,
  val isAnonymous: Boolean,
  val isMultiple: Boolean,
  val visibility: Visibility,
  val questions: List<Question>
)

data class Question(
  val id: String?,
  val required: Boolean = false,
  val type: QuestionType,
  val text: String,
  val options: List<String>?,
  val min: Int?,
  val max: Int?,
  val minDate: LocalDate?,
  val maxDate: LocalDate?
)

enum class QuestionType {
  SIMPLE_CHOICE, MULTI_CHOICE, OPEN_TEXT, DATE, NUMBER, EMAIL
}

enum class Visibility {
  public, invite_only, private
}
