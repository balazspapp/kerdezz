package hu.gde.kerdezz.templateservice.web.dto

import hu.gde.kerdezz.templateservice.domain.Question
import hu.gde.kerdezz.templateservice.domain.Survey
import hu.gde.kerdezz.templateservice.domain.Visibility
import java.util.UUID

data class QuestionnaireDto(
  val id: String?,
  val name: String,
  val text: String?,
  val anonymous: Boolean,
  val multiCompletion: Boolean,
  val visibility: Visibility,
  val questions: List<QuestionDto>?
)

fun QuestionnaireDto.mapDtoToSurvey(): Survey {
  return Survey(
    this.id,
    this.name,
    this.text,
    this.anonymous,
    this.multiCompletion,
    this.visibility,
    this.questions?.map { it.mapToQuestion() } ?: listOf()
  )
}

fun  QuestionDto.mapToQuestion(): Question {
  return Question(
    id = this.id?: UUID.randomUUID().toString(),
    required = this.required,
    type = this.questionType,
    text = this.questionText,
    options = this.options?.map{ it.value },
    min = this.minValue,
    max = this.maxValue,
    minDate = this.minDate,
    maxDate = this.maxDate
  )
}

fun Survey.mapSurveyToDto(): QuestionnaireDto {
  return QuestionnaireDto(
    this.id,
    this.name,
    this.text,
    this.isAnonymous,
    this.isMultiple,
    this.visibility,
    this.questions.map { it.mapQuestionToDto() }
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
