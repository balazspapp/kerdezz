package hu.gde.kerdezz.answerservice.messaging

import hu.gde.kerdezz.answerservice.dto.SurveyAnswerDto
import java.util.function.Supplier


interface SurveyAnswerSupplier : Supplier<SurveyAnswerDto?>
