package hu.gde.kerdezz.templateservice.web.dto

import hu.gde.kerdezz.templateservice.domain.QuestionType
import java.time.LocalDate

data class QuestionDto(
  val questionText: String,
  val questionType: QuestionType,
  val options: List<OptionDto>?,
  val minValue: Int?,
  val maxValue: Int?,
  val minDate: LocalDate?,
  val maxDate: LocalDate?
)

data class OptionDto (val value: String)
