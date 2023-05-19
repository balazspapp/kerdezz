import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {SurveyStats} from "../domain/statistics";

@Injectable({
  providedIn: 'root'
})
export class SurveyStatsService {

  constructor(private http: HttpClient) {}

  getSurveyStats(surveyId: string): Observable<SurveyStats> {
    const url = `/api/stats/${surveyId}`;
    return this.http.get<SurveyStats>(url);
  }
}
