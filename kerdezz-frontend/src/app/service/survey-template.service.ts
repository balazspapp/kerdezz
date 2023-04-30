import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Survey} from "../domain/survey";
import {map, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SurveyTemplateService {
  private baseUrl = '/api/templates';

  constructor(private http: HttpClient) {
  }

  saveSurvey(survey: Survey) {
    const url = `${this.baseUrl}`;
    return this.http.post<string>(url, survey);
  }

  getSurveys(): Observable<Survey[]> {
    const url = `${this.baseUrl}`;
    return this.http.get<any>(url).pipe(
      map(response => {
        return response.content;
      })
    );
  }

}
