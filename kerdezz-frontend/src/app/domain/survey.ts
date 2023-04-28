export interface Survey {
  id: number;
  name: string;
  anonymous: boolean;
  multiCompletion: boolean;
  visibility: string;
  createdAt: Date;
  updatedAt: Date;
  questions: Question[];
}

export interface Question {
  id: number;
  text: string;
  type: string;
  required: boolean;
  options: Option[];
}

export interface Option {
  id: number;
  text: string;
}
