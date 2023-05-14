export class Survey {
  id?: string;
  name: string;
  anonymous: boolean;
  multiCompletion: boolean;
  visibility: string;
  questions: Question[];
  allowedEmails: string[] = [];
}

export interface Question {
  id?: string;
  questionText?: string;
  questionType?: string;
  required: boolean;
  options: Option[];
  minValue?: number;
  maxValue?: number;
  minDate?: Date;
  maxDate?: Date;
}

export interface Option {
  value: string
}
