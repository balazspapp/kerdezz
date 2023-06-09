package hu.gde.kerdezz.templateservice.web.dto

import hu.gde.kerdezz.templateservice.domain.Question
import hu.gde.kerdezz.templateservice.domain.Survey
import hu.gde.kerdezz.templateservice.domain.Visibility
import java.util.UUID

data class SurveyDto(
  val id: String?,
  val owner: String?,
  val name: String,
  val text: String?,
  val anonymous: Boolean,
  val multiCompletion: Boolean,
  val visibility: Visibility,
  val questions: List<QuestionDto>?,
  val editable: Boolean = false,
  val allowedEmails: List<String>?
)

fun SurveyDto.mapDtoToSurvey(owner: String): Survey {
  return Survey(
    id = this.id,
    owner = owner,
    name = this.name,
    text = this.text,
    isAnonymous = this.anonymous,
    isMultiple = this.multiCompletion,
    visibility = this.visibility,
    questions = this.questions?.map { it.mapToQuestion() } ?: listOf(),
    allowedEmails = if (this.visibility == Visibility.invite_only) this.allowedEmails else null
  )
}

fun QuestionDto.mapToQuestion(): Question {
  return Question(
    id = this.id ?: UUID.randomUUID().toString(),
    required = this.required,
    type = this.questionType,
    text = this.questionText,
    options = this.options?.map { it.value },
    min = this.minValue,
    max = this.maxValue,
    minDate = this.minDate,
    maxDate = this.maxDate
  )
}

fun Survey.mapSurveyToDto(editable: Boolean): SurveyDto {
  return SurveyDto(
    this.id,
    this.owner,
    this.name,
    this.text,
    this.isAnonymous,
    this.isMultiple,
    this.visibility,
    this.questions.map { it.mapQuestionToDto() },
    editable,
    if (visibility == Visibility.invite_only) this.allowedEmails else null
  )
}

fun Question.mapQuestionToDto(): QuestionDto {
  return QuestionDto(
    id = this.id,
    required = this.required,
    questionType = this.type,
    questionText = this.text,
    options = this.options?.map { OptionDto(it) },
    minValue = this.min,
    maxValue = this.max,
    minDate = this.minDate,
    maxDate = this.maxDate
  )
}
