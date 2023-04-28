package hu.gde.kerdezz.templateservice.web.dto

import hu.gde.kerdezz.templateservice.domain.QuestionType
import java.time.LocalDate

data class QuestionDto(
  val questionText: String,
  val questionType: QuestionType,
  val options: List<String>?,
  val minValue: Int?,
  val maxValue: Int?,
  val minDate: LocalDate?,
  val maxDate: LocalDate?
)
