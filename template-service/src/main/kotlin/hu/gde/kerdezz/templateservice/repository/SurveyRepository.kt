package hu.gde.kerdezz.templateservice.repository

import hu.gde.kerdezz.templateservice.domain.Survey
import org.springframework.data.mongodb.repository.MongoRepository

interface SurveyRepository : MongoRepository<Survey, String> {
}
