package hu.gde.kerdezz.templateservice.web.dto

import hu.gde.kerdezz.templateservice.domain.Question
import hu.gde.kerdezz.templateservice.domain.QuestionType
import hu.gde.kerdezz.templateservice.domain.Survey
import hu.gde.kerdezz.templateservice.domain.Visibility
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class QuestionnaireDtoKtTest {
  private val question = Question(
    "id",
    true,
    QuestionType.SIMPLE_CHOICE,
    "A question",
    null,
    null,
    null,
    null,
    null
  )

  private val questionDto = QuestionDto(
    id = "id",
    required = true,
    questionType = QuestionType.SIMPLE_CHOICE,
    questionText = "A question",
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
      visibility = Visibility.private,
      name = "Name",
      questions = listOf(questionDto),
      text = "text"
    )
    val actual = questionnaireDto.mapDtoToSurvey()
    val expected = Survey(
      id = null, name = "Name", isAnonymous = false, isMultiple = false, visibility = Visibility.private, text = "text",
      questions = listOf(question)
    )
    assertEquals(expected, actual)
  }

  @Test
  fun mapToQuestion() {
    assertEquals(question, questionDto.mapToQuestion())
  }
}
