export interface AnswerStats {
  answerValue: string | null;
  count: number;
}

export interface QuestionStats {
  questionId: string | null;
  type: QuestionType | null;
  count: number;
  sum: number;
  answers: AnswerStats[];
  average: number;
}

export interface SurveyStats {
  surveyId: string | null;
  completionCount: number;
  questionStats: QuestionStats[];
}

export enum QuestionType {
  SIMPLE_CHOICE = 'SIMPLE_CHOICE',
  MULTI_CHOICE = 'MULTI_CHOICE',
  OPEN_TEXT = 'OPEN_TEXT',
  DATE = 'DATE',
  NUMBER = 'NUMBER',
  EMAIL = 'EMAIL'
}
