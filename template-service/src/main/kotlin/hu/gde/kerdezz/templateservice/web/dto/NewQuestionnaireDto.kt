package hu.gde.kerdezz.templateservice.web.dto

data class NewQuestionnaireDto(
  val isAnonymous: Boolean,
  val isMultiple: Boolean,
  val isPublic: Boolean,
  val questions: List<NewQuestionDto>
)
