export interface SurveyAnswer {
  surveyId: string,
  answers: Answer[]
}

export interface Answer {
  questionId: string,
  value?: string,
  multiValue?: string[]
}
