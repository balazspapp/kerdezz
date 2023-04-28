package hu.gde.kerdezz.templateservice.web.dto

import hu.gde.kerdezz.templateservice.domain.Question
import hu.gde.kerdezz.templateservice.domain.Survey

data class QuestionnaireDto(
  val id: String?,
  val isAnonymous: Boolean,
  val isMultiple: Boolean,
  val isPublic: Boolean,
  val name: String,
  val questions: List<QuestionDto>?
)

fun mapDtoToSurvey(dto: QuestionnaireDto): Survey {
  return Survey(
    dto.id,
    dto.name,
    dto.isAnonymous,
    dto.isMultiple,
    dto.isPublic,
    dto.questions?.map { mapToQuestion(it) } ?: listOf()
  )
}

fun mapToQuestion(questionDto: QuestionDto): Question {
  return Question(
    type = questionDto.questionType,
    text = questionDto.questionText,
    options = questionDto.options,
    min = questionDto.minValue,
    max = questionDto.maxValue,
    minDate = questionDto.minDate,
    maxDate = questionDto.maxDate
  )
}

fun mapSurveyToDto(survey: Survey): QuestionnaireDto {
  return QuestionnaireDto(
    survey.id,
    survey.isAnonymous,
    survey.isMultiple,
    survey.isPublic,
    survey.name,
    survey.questions.map { mapQuestionToDto(it) }
  )
}

fun mapQuestionToDto(question: Question): QuestionDto {
  return QuestionDto(
    questionType = question.type,
    questionText = question.text,
    options = question.options,
    minValue = question.min,
    maxValue = question.max,
    minDate = question.minDate,
    maxDate = question.maxDate
  )
}
