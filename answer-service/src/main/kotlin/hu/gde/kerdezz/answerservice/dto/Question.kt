package hu.gde.kerdezz.answerservice.dto

import java.time.LocalDate

data class Question(
  val id: String,
  val required: Boolean = false,
  val questionText: String,
  val questionType: QuestionType,
  val options: List<Option>?,
  val minValue: Int?,
  val maxValue: Int?,
  val minDate: LocalDate?,
  val maxDate: LocalDate?
)
