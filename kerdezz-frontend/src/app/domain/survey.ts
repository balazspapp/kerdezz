export interface Survey {
  id?: number;
  name: string;
  anonymous: boolean;
  multiCompletion: boolean;
  visibility: string;
  createdAt?: Date;
  updatedAt?: Date;
  questions: Question[];
}

export interface Question {
  id?: number;
  questionText?: string;
  questionType?: string;
  required?: boolean;
  options: Option[];
  min?: number;
  max?: number;
  minDate?: Date;
  maxDate?: Date;
}

export interface Option {
  value: string
}
