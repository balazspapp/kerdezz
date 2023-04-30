package hu.gde.kerdezz.templateservice.web.dto

import hu.gde.kerdezz.templateservice.domain.Question
import hu.gde.kerdezz.templateservice.domain.QuestionType
import hu.gde.kerdezz.templateservice.domain.Survey
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class QuestionnaireDtoKtTest {
  private val question = Question(
    QuestionType.SIMPLE_CHOICE,
    "A question",
    null,
    null,
    null,
    null,
    null
  )

  private val questionDto = QuestionDto(
    questionText = "A question",
    questionType = QuestionType.SIMPLE_CHOICE,
    options = null,
    minValue = null,
    maxValue = null,
    minDate = null,
    maxDate = null
  )

  @Test
  fun mapDtoToSurvey() {
    val questionnaireDto = QuestionnaireDto(
      id = null,
      anonymous = false,
      multiCompletion = false,
      isPublic = false,
      name = "Name",
      questions = listOf(questionDto)
    )
    val actual = mapDtoToSurvey(questionnaireDto)
    val expected = Survey(
      id = null, name = "Name", isAnonymous = false, isMultiple = false, isPublic = false,
      questions = listOf(question)
    )
    assertEquals(expected, actual)
  }

  @Test
  fun mapToQuestion() {
    assertEquals(question, mapToQuestion(questionDto))
  }
}
