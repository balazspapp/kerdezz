package hu.gde.kerdezz.statisticsservice.service

import hu.gde.kerdezz.statisticsservice.domain.AnswerValueStats
import hu.gde.kerdezz.statisticsservice.domain.QuestionStats
import hu.gde.kerdezz.statisticsservice.domain.SurveyStats
import hu.gde.kerdezz.statisticsservice.dto.QuestionType
import hu.gde.kerdezz.statisticsservice.dto.StatSurveyAnswer
import hu.gde.kerdezz.statisticsservice.repository.AnswerValueStatsRepository
import hu.gde.kerdezz.statisticsservice.repository.QuestionStatsRepository
import hu.gde.kerdezz.statisticsservice.repository.SurveyStatsRepository
import org.springframework.stereotype.Service

@Service
class AnswerStatsService(
  val surveyStatsRepository: SurveyStatsRepository,
  val questionStatsRepository: QuestionStatsRepository,
  val answerValueStatsRepository: AnswerValueStatsRepository
) {
  fun generate(statSurveyAnswer: StatSurveyAnswer) {
    val surveyStats = surveyStatsRepository.findBySurveyId(statSurveyAnswer.surveyId)
      ?.also { it.completionCount += 1 }
      ?: SurveyStats(surveyId = statSurveyAnswer.surveyId, completionCount = 1).also { surveyStatsRepository.save(it) }

    val questionStatsMap = surveyStats.answers.associateBy { it.questionId }

    statSurveyAnswer.answers.forEach { statAnswer ->
      val questionStats = questionStatsMap[statAnswer.questionId]
        ?.also { it.count += 1 }
        ?: QuestionStats(
          questionId = statAnswer.questionId,
          type = statAnswer.type,
          count = 1,
          surveyStats = surveyStats
        ).also { questionStatsRepository.save(it) }

      if (statAnswer.value != null) {
        // if question type is NUMBER, try to convert value to number and add to sum
        if (statAnswer.type == QuestionType.NUMBER) {
          statAnswer.value.toDoubleOrNull()?.let { questionStats.sum += it }
        }

        answerValueStatsRepository.findByQuestionStatsAndAnswerValue(questionStats, statAnswer.value)
          ?.also { it.count += 1; answerValueStatsRepository.save(it) }
          ?: AnswerValueStats(
            count = 1,
            answerValue = statAnswer.value,
            questionStats = questionStats
          ).also { answerValueStatsRepository.save(it) }
      }

      statAnswer.multiValue?.forEach { value ->
        answerValueStatsRepository.findByQuestionStatsAndAnswerValue(questionStats, value)
          ?.also { it.count += 1; answerValueStatsRepository.save(it) }
          ?: AnswerValueStats(
            count = 1,
            answerValue = value,
            questionStats = questionStats
          ).also { answerValueStatsRepository.save(it) }
      }
    }
  }
}
