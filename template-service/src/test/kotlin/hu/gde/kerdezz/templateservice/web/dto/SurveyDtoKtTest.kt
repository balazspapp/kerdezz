package hu.gde.kerdezz.templateservice.web.dto

import hu.gde.kerdezz.templateservice.domain.Question
import hu.gde.kerdezz.templateservice.domain.QuestionType
import hu.gde.kerdezz.templateservice.domain.Survey
import hu.gde.kerdezz.templateservice.domain.Visibility
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SurveyDtoKtTest {
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
    val surveyDto = SurveyDto(
      id = null,
      name = "Name",
      anonymous = false,
      multiCompletion = false,
      visibility = Visibility.private,
      text = "text",
      questions = listOf(questionDto)
    )
    val expected = Survey(
      id = null,
      user = "userid",
      name = "Name",
      isAnonymous = false,
      isMultiple = false,
      visibility = Visibility.private,
      text = "text",
      questions = listOf(question)
    )

    val actual = surveyDto.mapDtoToSurvey("userid")

    assertEquals(expected, actual)
  }

  @Test
  fun mapToQuestion() {
    assertEquals(question, questionDto.mapToQuestion())
  }
}
