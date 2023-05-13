package hu.gde.kerdezz.templateservice.domain

import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.time.LocalDate

@Document("surveyTemplates")
data class Survey(
  val id: String?,
  val user: String,
  val createdAt: Instant = Instant.now(),
  val name: String,
  val text: String?,
  val isAnonymous: Boolean,
  val isMultiple: Boolean,
  val visibility: Visibility,
  val questions: List<Question>,
  val allowedEmails: List<String>?
)

data class Question(
  val id: String,
  val required: Boolean = false,
  val type: QuestionType,
  val text: String,
  val options: List<String>?,
  val min: Int?,
  val max: Int?,
  val minDate: LocalDate?,
  val maxDate: LocalDate?
)
