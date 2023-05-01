import {Component} from '@angular/core';
import {SurveyTemplateService} from "../service/survey-template.service";
import {Router} from "@angular/router";
import {Question, Survey} from "../domain/survey";

@Component({
  selector: 'app-edit-survey',
  templateUrl: './survey-editor.component.html',
  styleUrls: ['./survey-editor.component.css']
})
export class SurveyEditorComponent {
  survey: Survey;
  newQuestion: Question = SurveyEditorComponent.getNewQuestion();

  constructor(private surveyService: SurveyTemplateService, private router: Router) {
    this.survey = {
      name: '',
      anonymous: false,
      multiCompletion: false,
      visibility:'public',
      questions: [SurveyEditorComponent.getNewQuestion()]
    }
  }

  saveSurvey() {
    this.surveyService.saveSurvey(this.survey).subscribe({
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

  addQuestion() {
    this.survey.questions.push(this.newQuestion);
    this.newQuestion = SurveyEditorComponent.getNewQuestion();
  }

  private static getNewQuestion() {
    return {
      id: NaN,
      questionText: '',
      questionType: '',
      required: false,
      options: [],
      min: NaN,
      max: NaN
    };
  }

  removeQuestion(index: number) {
    this.survey.questions.splice(index, 1);
  }

  resetQuestion() {
    this.newQuestion = SurveyEditorComponent.getNewQuestion();
  }
}
