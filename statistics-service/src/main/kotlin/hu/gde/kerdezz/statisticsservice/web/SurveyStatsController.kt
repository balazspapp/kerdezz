package hu.gde.kerdezz.statisticsservice.web

import hu.gde.kerdezz.statisticsservice.dto.QuestionType
import hu.gde.kerdezz.statisticsservice.repository.SurveyStatsRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/stats")
class SurveyStatsController(
  private val surveyStatsRepository: SurveyStatsRepository
) {

  @GetMapping("{surveyId}")
  fun getSurveyStats(@PathVariable surveyId: String): ResponseEntity<SurveyStatsResponse> {
    val surveyStats = surveyStatsRepository.findBySurveyId(surveyId)
      ?: return ResponseEntity.notFound().build()

    val questionStatsDtos = surveyStats.answers.map { questionStats ->
      val answerStats = questionStats.questionStats.map { answerStats ->
        AnswerStats(
          answerValue = answerStats.answerValue,
          count = answerStats.count
        )
      }

      QuestionStatsDto(
        questionId = questionStats.questionId ?: throw IllegalStateException("missing question id"),
        type = questionStats.type ?: throw IllegalStateException("missing question type"),
        count = questionStats.count,
        sum = questionStats.sum,
        answers = answerStats,
        average = questionStats.sum / questionStats.count.toDouble()
      )
    }

    return ResponseEntity.ok(
      SurveyStatsResponse(
        surveyId = surveyStats.surveyId,
        completionCount = surveyStats.completionCount,
        questionStats = questionStatsDtos
      )
    )
  }
}

data class QuestionStatsDto(
  val questionId: String,
  val type: QuestionType,
  val count: Int,
  val sum: Double,
  val answers: List<AnswerStats>,
  val average: Double
)

data class AnswerStats(
  val answerValue: String?,
  val count: Long
)

data class SurveyStatsResponse(
  val surveyId: String?,
  val completionCount: Int,
  val questionStats: List<QuestionStatsDto>
)
