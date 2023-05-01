package hu.gde.kerdezz.answerservice.service

import hu.gde.kerdezz.answerservice.domain.Answer
import hu.gde.kerdezz.answerservice.domain.Question
import hu.gde.kerdezz.answerservice.domain.QuestionType
import hu.gde.kerdezz.answerservice.domain.SurveyAnswer
import org.apache.commons.validator.routines.EmailValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeParseException

@Service
class SurveyValidatorService(
  @Autowired private val surveyTemplateService: SurveyTemplateService
) {

  fun validate(surveyAnswer: SurveyAnswer): Boolean {
    val surveyTemplate = surveyTemplateService.getTemplateById(surveyAnswer.surveyId)

    // Ellenőrizze, hogy az összes kötelező kérdésre válaszoltak-e
    if (!surveyTemplate.questions.all { it.required || surveyAnswer.answers.any { answer -> answer.questionId == it.id } }) {
      return false
    }

    return surveyAnswer.answers.all { answer ->
      val question = surveyTemplate.questions.find { it.id == answer.questionId }
      question != null && validateAnswer(answer, question)
    }
  }

  private fun validateAnswer(answer: Answer, question: Question): Boolean {
    return when (question.type) {
      QuestionType.SIMPLE_CHOICE -> question.options?.contains(answer.value) ?: false
      QuestionType.MULTI_CHOICE -> answer.multiValue?.all { question.options?.contains(it) ?: false } ?: false
      QuestionType.OPEN_TEXT -> {
        val length = answer.value?.length ?: 0
        (question.min == null || length >= question.min) && (question.max == null || length <= question.max)
      }
      QuestionType.DATE -> {
        val date = answer.value?.toLocalDateOrNull() ?: return false
        (question.minDate == null || date >= question.minDate) && (question.maxDate == null || date <= question.maxDate)
      }
      QuestionType.NUMBER -> {
        val value = answer.value?.toDoubleOrNull() ?: return false
        (question.min == null || value >= question.min) && (question.max == null || value <= question.max)
      }
      QuestionType.EMAIL -> answer.value?.isEmail() ?: false
    }
  }
}

fun String.toLocalDateOrNull(): LocalDate? {
  return try {
    LocalDate.parse(this)
  } catch (ex: DateTimeParseException) {
    null
  }
}

fun String.isEmail(): Boolean {
  val validator = EmailValidator.getInstance()
  return validator.isValid(this)
}
