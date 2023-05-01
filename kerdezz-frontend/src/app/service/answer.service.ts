import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SurveyAnswer} from "../domain/survey-answer";

@Injectable({
  providedIn: 'root'
})
export class AnswerService {
  private answerUrl = '/api/surveys/answers';

  constructor(private http: HttpClient) { }

  saveAnswers(surveyAnswers: SurveyAnswer) {
    return this.http.post(this.answerUrl, surveyAnswers);
  }
}
