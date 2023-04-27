package hu.gde.kerdezz.templateservice.web.dto

data class DependentQuestionDto(
  val questionId: Long,
  val dependentOptions: List<String>
)

