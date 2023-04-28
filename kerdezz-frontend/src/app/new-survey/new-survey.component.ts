import { Component } from '@angular/core';
import {SurveyTemplateService} from "../service/survey-template.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-new-survey',
  templateUrl: './new-survey.component.html',
  styleUrls: ['./new-survey.component.css']
})
export class NewSurveyComponent {
  surveyName?: string;
  anonymous: boolean = false;
  multiCompletion: boolean = false;
  visibility: string = "public";

  constructor(private surveyService: SurveyTemplateService, private router:Router) {}

  saveSurvey() {
    const survey = {
      name: this.surveyName,
      anonymous: this.anonymous,
      multiCompletion: this.multiCompletion,
      visibility: this.visibility
    };
    this.surveyService.saveSurvey(survey).subscribe({
      next: () => {
        console.log("saved next");
        this.router.navigate(['/']);
      },
      error: (error) => {
        console.error(error);
        return null;
      },
      complete: () => {
        console.log("saved complete");
        this.router.navigate(['/']);
      }
    });
  }
}
