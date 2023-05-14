package hu.gde.kerdezz.answerservice.mappers

import hu.gde.kerdezz.answerservice.domain.Answer
import hu.gde.kerdezz.answerservice.domain.SurveyAnswer
import hu.gde.kerdezz.answerservice.dto.AnswerDto
import hu.gde.kerdezz.answerservice.dto.SurveyAnswerDto
import hu.gde.kerdezz.answerservice.security.UserInfo

fun surveyAnswerToDto(surveyAnswer: SurveyAnswer): SurveyAnswerDto {
  return SurveyAnswerDto(
    id = surveyAnswer.id,
    owner = surveyAnswer.owner,
    email = surveyAnswer.email,
    surveyId = surveyAnswer.surveyId,
    createdAt = surveyAnswer.createdDate,
    answers = surveyAnswer.answers.map { AnswerDto(questionId = it.questionId, value = it.value, multiValue = it.multiValue) }
  )
}

fun mapDtoWithUser(surveyAnswerDto: SurveyAnswerDto, user: UserInfo): SurveyAnswer {
  return SurveyAnswer(
    id = null,
    email = user.email,
    owner = user.user,
    surveyId = surveyAnswerDto.surveyId,
    answers = surveyAnswerDto.answers.map { dtoToAnswer(it) }
  )
}

fun dtoToAnswer(answerDTO: AnswerDto): Answer {
  return Answer(
    questionId = answerDTO.questionId,
    value = answerDTO.value,
    multiValue = answerDTO.multiValue
  )
}
