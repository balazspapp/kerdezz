package hu.gde.kerdezz.answerservice.dto

import hu.gde.kerdezz.answerservice.domain.Answer
import hu.gde.kerdezz.answerservice.domain.SurveyAnswer

data class SurveyAnswerRequest(
  val surveyId: String,
  val answers: List<AnswerDto> = listOf()
)

data class AnswerDto(
  val questionId: String,
  val value: String?,
  val multiValue: List<String>?
)

fun requestToSurveyAnswer(surveyAnswerRequest: SurveyAnswerRequest, user: String): SurveyAnswer {
  return SurveyAnswer(
    id = null,
    user = user,
    surveyId = surveyAnswerRequest.surveyId,
    answers = surveyAnswerRequest.answers.map { dtoToAnswer(it) }
  )
}

fun dtoToAnswer(answerDTO: AnswerDto): Answer {
  return Answer(
    questionId = answerDTO.questionId,
    value = answerDTO.value,
    multiValue = answerDTO.multiValue
  )
}
