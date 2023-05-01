import {Component} from '@angular/core';
import {SurveyTemplateService} from '../service/survey-template.service';
import {Survey} from "../domain/survey";
import {Router} from "@angular/router";

@Component({
  selector: 'app-survey-list',
  templateUrl: './survey-list.component.html',
  styleUrls: ['./survey-list.component.css']
})
export class SurveyListComponent {
  surveys: any;
  totalPages: number;
  pageNumber: number = 1;

  constructor(private surveyTemplateService: SurveyTemplateService, private router: Router) {
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

  goToEditSurvey(id: string) {
    this.router.navigate(['edit-survey', id]);
  }

}
