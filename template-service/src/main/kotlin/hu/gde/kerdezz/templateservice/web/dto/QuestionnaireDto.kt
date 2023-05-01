package hu.gde.kerdezz.templateservice.web.dto

import hu.gde.kerdezz.templateservice.domain.Question
import hu.gde.kerdezz.templateservice.domain.Survey
import hu.gde.kerdezz.templateservice.domain.Visibility

data class QuestionnaireDto(
  val id: String?,
  val name: String,
  val text: String?,
  val anonymous: Boolean,
  val multiCompletion: Boolean,
  val visibility: Visibility,
  val questions: List<QuestionDto>?
)

fun mapDtoToSurvey(dto: QuestionnaireDto): Survey {
  return Survey(
    dto.id,
    dto.name,
    dto.text,
    dto.anonymous,
    dto.multiCompletion,
    dto.visibility,
    dto.questions?.map { mapToQuestion(it) } ?: listOf()
  )
}

fun mapToQuestion(questionDto: QuestionDto): Question {
  return Question(
    type = questionDto.questionType,
    text = questionDto.questionText,
    options = questionDto.options?.map{ it.value },
    min = questionDto.minValue,
    max = questionDto.maxValue,
    minDate = questionDto.minDate,
    maxDate = questionDto.maxDate
  )
}

fun mapSurveyToDto(survey: Survey): QuestionnaireDto {
  return QuestionnaireDto(
    survey.id,
    survey.name,
    survey.text,
    survey.isAnonymous,
    survey.isMultiple,
    survey.visibility,
    survey.questions.map { mapQuestionToDto(it) }
  )
}

fun mapQuestionToDto(question: Question): QuestionDto {
  return QuestionDto(
    questionType = question.type,
    questionText = question.text,
    options = question.options?.map { OptionDto(it) },
    minValue = question.min,
    maxValue = question.max,
    minDate = question.minDate,
    maxDate = question.maxDate
  )
}
