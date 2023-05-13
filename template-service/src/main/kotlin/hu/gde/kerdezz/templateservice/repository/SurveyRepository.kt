package hu.gde.kerdezz.templateservice.repository

import hu.gde.kerdezz.templateservice.domain.Survey
import hu.gde.kerdezz.templateservice.domain.Visibility
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository


interface SurveyRepository : MongoRepository<Survey, String> {
  fun findByVisibilityOrUser(visibility: Visibility, user: String, pageable: Pageable): Page<Survey>
}
