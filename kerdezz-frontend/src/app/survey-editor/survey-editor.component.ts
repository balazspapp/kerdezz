import {Component} from '@angular/core';
import {SurveyTemplateService} from "../service/survey-template.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Question, Survey} from "../domain/survey";

@Component({
  selector: 'app-edit-survey',
  templateUrl: './survey-editor.component.html',
  styleUrls: ['./survey-editor.component.css']
})
export class SurveyEditorComponent {
  survey: Survey = getNewSurvey();
  newQuestion: Question = getNewQuestion();

  constructor(
    private surveyService: SurveyTemplateService,
    private router: Router,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit() {
    let surveyId = this.route.snapshot.paramMap.get('id');
    console.log('surveyId: {}', surveyId);
    if (surveyId) {
      this.surveyService.getSurvey(surveyId).subscribe({
        next: (survey) => {
          console.log(survey);
          this.survey = survey;
        },
        error: (error) => {
          console.error(error);
        },
        complete: () => {
          console.log("saved complete");
        }
      });
    } else {
      this.survey = getNewSurvey()
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
    this.newQuestion = getNewQuestion();
  }

  removeQuestion(index: number) {
    this.survey.questions.splice(index, 1);
  }

  resetQuestion() {
    this.newQuestion = getNewQuestion();
  }
}

function getNewQuestion(): Question {
  return {
    questionText: '',
    questionType: '',
    required: false,
    options: [],
    min: NaN,
    max: NaN
  };
}

function getNewSurvey() {
  return {
    name: '',
    anonymous: false,
    multiCompletion: false,
    visibility: 'public',
    questions: [getNewQuestion()]
  };
}
