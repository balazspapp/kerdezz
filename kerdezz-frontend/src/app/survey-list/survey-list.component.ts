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

  constructor(public surveyTemplateService: SurveyTemplateService) {
  }

  ngOnInit(): void {
    this.surveyTemplateService.getSurveys()
      .subscribe({
        next: (surveys) => {
          this.surveys = surveys;
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
