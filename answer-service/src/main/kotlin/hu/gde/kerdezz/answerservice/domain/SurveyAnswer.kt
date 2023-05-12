package hu.gde.kerdezz.answerservice.domain

import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("surveyAnswers")
data class SurveyAnswer(
  val id: String?,
  val user: String,
  val createdDate: Instant = Instant.now(),
  val surveyId: String,
  val answers: List<Answer>
)

data class Answer(
  val questionId: String,
  val value: String?,
  val multiValue: List<String>?
)
