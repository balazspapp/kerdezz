import {Component} from '@angular/core';
import {SurveyTemplateService} from '../service/survey-template.service';
import {Survey} from "../domain/survey";

@Component({
  selector: 'app-survey-list',
  templateUrl: './survey-list.component.html',
  styleUrls: ['./survey-list.component.css']
})
export class SurveyListComponent {
  surveys: any;
  totalPages: number;
  pageNumber: number = 1;

  constructor(public surveyTemplateService: SurveyTemplateService) {
  }

  ngOnInit(): void {
    this.getSurveys();
  }

  getSurveys() {
    this.surveyTemplateService.getSurveys(this.pageNumber - 1)
      .subscribe({
        next: (response) => {
          console.log(response);
          this.surveys = response.surveys;
          this.totalPages = response.totalPages;
        },
        error: (error) => {
          console.log(error);
        },
        complete: () => {
          console.log('Complete');
        }
      });
  }
}
