package hu.gde.kerdezz.answerservice.service

import hu.gde.kerdezz.answerservice.dto.*
import org.apache.commons.validator.routines.EmailValidator
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeParseException

@Service
class AnswerValidatorService {

  fun validate(surveyAnswer: SurveyAnswerDto, surveyTemplate: SurveyTemplate): Boolean {
    if (isAllRequiredQuestionsAnswered(surveyTemplate, surveyAnswer.answers) == false) {
      return false
    }

    return surveyAnswer.answers.all { answer ->
      val question = surveyTemplate.questions?.find { it.id == answer.questionId }
      question != null && validateAnswer(answer, question)
    }
  }

  private fun isAllRequiredQuestionsAnswered(
    surveyTemplate: SurveyTemplate,
    answerDtos: List<AnswerDto>
  ) = surveyTemplate.questions?.all { !it.required || answerDtos.any { answer -> answer.questionId == it.id } }

  private fun validateAnswer(answer: AnswerDto, question: Question): Boolean {
    return when (question.questionType) {
      QuestionType.SIMPLE_CHOICE -> validateSimpleChoice(question, answer)
      QuestionType.MULTI_CHOICE -> validateMultiChoiceAnswer(answer, question)
      QuestionType.OPEN_TEXT -> validateOpenText(answer, question)
      QuestionType.DATE -> validateDate(answer, question)
      QuestionType.NUMBER -> validateNumber(answer, question)
      QuestionType.EMAIL -> validateEmail(answer)
    }
  }

}

fun validateOpenText(
  answer: AnswerDto,
  question: Question
): Boolean {
  val length = answer.value?.length ?: 0
  return (question.minValue == null || length >= question.minValue) && (question.maxValue == null || length <= question.maxValue)
}

fun validateMultiChoiceAnswer(answer: AnswerDto, question: Question): Boolean {
  return answer.multiValue?.all {
    question.options?.map { optionDto ->
      optionDto.value
    }?.contains(it) ?: false
  } ?: false
}

fun validateSimpleChoice(question: Question, answer: AnswerDto) =
  question.options?.map { it.value }?.contains(answer.value) ?: false

fun String.toLocalDate(): LocalDate {
  return try {
    LocalDate.parse(this)
  } catch (ex: DateTimeParseException) {
    throw IllegalArgumentException()
  }
}

fun String.isEmail(): Boolean {
  val validator = EmailValidator.getInstance()
  return validator.isValid(this)
}

fun validateDate(answer: AnswerDto, question: Question): Boolean {
  val date = answer.value?.toLocalDate() ?: return false
  return (question.minDate == null || date >= question.minDate) && (question.maxDate == null || date <= question.maxDate)
}

fun validateNumber(
  answer: AnswerDto,
  question: Question
): Boolean {
  val value = answer.value?.toIntOrNull() ?: return false
  return (question.minValue == null || value >= question.minValue) && (question.maxValue == null || value <= question.maxValue)
}

fun validateEmail(answer: AnswerDto) = answer.value?.isEmail() ?: false
