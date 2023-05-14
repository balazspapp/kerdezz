package hu.gde.kerdezz.answerservice.repository

import hu.gde.kerdezz.answerservice.domain.SurveyAnswer
import org.springframework.data.mongodb.repository.MongoRepository

interface SurveyAnswerRepository : MongoRepository<SurveyAnswer, String> {
  fun findBySurveyId(surveyId: String): List<SurveyAnswer>
}
