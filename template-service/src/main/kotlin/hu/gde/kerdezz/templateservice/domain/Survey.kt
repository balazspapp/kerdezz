package hu.gde.kerdezz.templateservice.domain

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document("surveyTemplates")
data class Survey(
  val id: String?,
  val name: String,
  val isAnonymous: Boolean,
  val isMultiple: Boolean,
  val isPublic: Boolean,
  val questions: List<Question>
)

data class Question(
  val type: QuestionType,
  val text: String,
  val options: List<String>?,
  val min: Int?,
  val max: Int?,
  val minDate: LocalDate?,
  val maxDate: LocalDate?
)
