package hu.gde.kerdezz.templateservice.web.dto

import java.time.LocalDate

data class NewQuestionDto (
  val questionText: String,
  val questionType: QuestionType,
  val options: List<String>?,
  val minValue: Int?,
  val maxValue: Int?,
  val minDate: LocalDate?,
  val maxDate: LocalDate?,
  val dependentQuestion: DependentQuestionDto?
  )
