package hu.gde.kerdezz.answerservice.service

import hu.gde.kerdezz.answerservice.domain.*
import org.apache.commons.validator.routines.EmailValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeParseException

@Service
class AnswerValidatorService(
  @Autowired private val surveyTemplateService: SurveyTemplateService
) {

  fun validate(surveyAnswer: SurveyAnswer): Boolean {
    val surveyTemplate = surveyTemplateService.getTemplateById(surveyAnswer.surveyId)

    if (isAllRequiredQuestionsAnswered(surveyTemplate, surveyAnswer) == false) {
      return false
    }

    return surveyAnswer.answers.all { answer ->
      val question = surveyTemplate.questions?.find { it.id == answer.questionId }
      question != null && validateAnswer(answer, question)
    }
  }

  private fun isAllRequiredQuestionsAnswered(
    surveyTemplate: SurveyTemplate,
    surveyAnswer: SurveyAnswer
  ) = surveyTemplate.questions?.all { !it.required || surveyAnswer.answers.any { answer -> answer.questionId == it.id } }

  private fun validateAnswer(answer: Answer, question: Question): Boolean {
    return when (question.questionType) {
      QuestionType.SIMPLE_CHOICE -> question.options?.map { it.value }?.contains(answer.value) ?: false
      QuestionType.MULTI_CHOICE -> validateMultiChoiceAnswer(answer, question)
      QuestionType.OPEN_TEXT -> {
        val length = answer.value?.length ?: 0
        (question.minValue == null || length >= question.minValue) && (question.maxValue == null || length <= question.maxValue)
      }
      QuestionType.DATE -> {
        val date = answer.value?.toLocalDate() ?: return false
        (question.minDate == null || date >= question.minDate) && (question.maxDate == null || date <= question.maxDate)
      }
      QuestionType.NUMBER -> {
        val value = answer.value?.toIntOrNull() ?: return false
        (question.minValue == null || value >= question.minValue) && (question.maxValue == null || value <= question.maxValue)
      }
      QuestionType.EMAIL -> answer.value?.isEmail() ?: false
    }
  }

  private fun validateMultiChoiceAnswer(answer: Answer, question: Question): Boolean {
    return answer.multiValue?.all {
      question.options?.map { optionDto ->
        optionDto.value
      }?.contains(it) ?: false
    } ?: false
  }
}

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
