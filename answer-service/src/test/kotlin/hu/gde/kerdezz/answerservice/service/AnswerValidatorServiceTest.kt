package hu.gde.kerdezz.answerservice.service

import hu.gde.kerdezz.answerservice.domain.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito
import java.time.LocalDate

internal class AnswerValidatorServiceTest {
  private lateinit var answerValidatorService: AnswerValidatorService
  private lateinit var surveyTemplateService: SurveyTemplateService

  @BeforeEach
  fun setUp() {
    surveyTemplateService = Mockito.mock(SurveyTemplateService::class.java)
    answerValidatorService = AnswerValidatorService(surveyTemplateService)
  }

  @Test
  fun `test validate with all required questions answered`() {
    // Arrange
    val surveyTemplate = SurveyTemplate(
      id = "survey_id",
      name = "Example Survey",
      text = "This is an example survey.",
      anonymous = true,
      multiCompletion = false,
      visibility = Visibility.public,
      questions = listOf(
        Question(
          id = "numberQuestion",
          required = true,
          questionText = "Select your hobbies:",
          questionType = QuestionType.NUMBER,
          options = null,
          minValue = 1,
          maxValue = 3,
          minDate = null,
          maxDate = null
        )
      )
    )
    val surveyAnswer = SurveyAnswer(
      surveyId = "survey_id",
      answers = mutableListOf(
        Answer(questionId = "numberQuestion", value = "2", multiValue = null),
      )
    )

    Mockito.`when`(surveyTemplateService.getSurveyById("survey_id")).thenReturn(surveyTemplate)

    // Act
    val result = answerValidatorService.validate(surveyAnswer)

    // Assert
    assertTrue(result)
  }

  @Test
  fun `test validate with missing required question`() {
    // Arrange
    val surveyTemplate = createSurveyTemplate()
    val surveyAnswer = createSurveyAnswer()
    val answers = surveyAnswer.answers as MutableList // Remove answer for the required question
    answers.removeAt(0);

    Mockito.`when`(surveyTemplateService.getSurveyById("survey_id")).thenReturn(surveyTemplate)

    // Act
    val result = answerValidatorService.validate(surveyAnswer)

    // Assert
    assertFalse(result)
  }

  private val simpleChoiceQuestion = Question(
    id = "question_1",
    required = true,
    questionText = "What is your favorite color?",
    questionType = QuestionType.SIMPLE_CHOICE,
    options = listOf(OptionDto("Red"), OptionDto("Blue"), OptionDto("Green")),
    minValue = null,
    maxValue = null,
    minDate = null,
    maxDate = null
  )

  private val multiChoiceQuestion = Question(
    id = "question_2",
    required = true,
    questionText = "Select your hobbies:",
    questionType = QuestionType.MULTI_CHOICE,
    options = listOf(OptionDto("Reading"), OptionDto("Sports"), OptionDto("Traveling")),
    minValue = null,
    maxValue = null,
    minDate = null,
    maxDate = null
  )

  private val dateQuestion = Question(
    id = "dateQuestion",
    required = true,
    questionText = "Birthday:",
    questionType = QuestionType.DATE,
    options = null,
    minValue = null,
    maxValue = null,
    minDate = null,
    maxDate = null
  )

  private val numberQuestion = Question(
    id = "numberQuestion",
    required = true,
    questionText = "Select your hobbies:",
    questionType = QuestionType.NUMBER,
    options = null,
    minValue = 1,
    maxValue = 3,
    minDate = null,
    maxDate = null
  )

  private val dateQuestion2 = Question(
    id = "dateQuestion2",
    required = true,
    questionText = "Select your hobbies:",
    questionType = QuestionType.DATE,
    options = null,
    minValue = null,
    maxValue = null,
    minDate = LocalDate.of(2000, 1, 1),
    maxDate = null
  )

  fun createSurveyTemplate(): SurveyTemplate {
    return SurveyTemplate(
      id = "survey_id",
      name = "Example Survey",
      text = "This is an example survey.",
      anonymous = true,
      multiCompletion = false,
      visibility = Visibility.public,
      questions = listOf(
        simpleChoiceQuestion,
        multiChoiceQuestion,
        dateQuestion,
        numberQuestion,
        dateQuestion2
      )
    )
  }

  fun createSurveyAnswer(): SurveyAnswer {
    return SurveyAnswer(
      surveyId = "survey_id",
      answers = mutableListOf(
        Answer(questionId = "question_1", value = "Red", multiValue = null),
        Answer(questionId = "question_2", value = null, multiValue = listOf("Reading", "Traveling")),
        Answer(questionId = "dateQuestion", value = "2020-01-01", multiValue = null),
        Answer(questionId = "dateQuestion2", value = "2020-01-02", multiValue = null),
        Answer(questionId = "numberQuestion", value = "2", multiValue = null),
      )
    )
  }


}
