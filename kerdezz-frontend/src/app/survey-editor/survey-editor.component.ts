import {Component} from '@angular/core';
import {SurveyTemplateService} from "../service/survey-template.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Question, Survey} from "../domain/survey";
import {AnswerService} from "../service/answer.service";

@Component({
  selector: 'app-edit-survey',
  templateUrl: './survey-editor.component.html',
  styleUrls: ['./survey-editor.component.css']
})
export class SurveyEditorComponent {
  survey: Survey = getNewSurvey();
  newQuestion: Question = getNewQuestion();
  responses: any[] = [];
  selectedResponse: any = {};
  questionMap: { [id: string]: string } = {};
  selectedResponseId: string | null;
  answersLoaded = false;

  constructor(
    private surveyService: SurveyTemplateService,
    private answerService: AnswerService,
    private router: Router,
    private route: ActivatedRoute
  ) {
  }

  ngOnInit() {
    let surveyId = this.route.snapshot.paramMap.get('id');
    console.log('surveyId: {}', surveyId);
    if (surveyId) {
      this.fetchSurvey(surveyId);
      this.loadResponses(surveyId);
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

  addEmail() {
    if (!this.survey.allowedEmails) {
      this.survey.allowedEmails = [];
    }
    this.survey.allowedEmails.push('');
  }

  removeEmail(index: number) {
    this.survey.allowedEmails.splice(index, 1);
  }

  trackByFn(index: number): number {
    return index;
  }

  loadResponses(surveyId: string) {
    console.log("fetch answers")
    this.answerService.fetchAnswers(surveyId).subscribe(value => {
      console.log('responses = ', JSON.stringify(value));
      this.responses = value;
      if (this.responses.length > 0) {
        this.selectedResponse = this.responses[0];
        this.selectedResponseId = this.selectedResponse.id;
        this.answersLoaded = true;
        console.log(JSON.stringify(this.selectedResponse));
      }
    });
  }

  updateResponse() {
    this.selectedResponse = this.responses.find(r => r.id === this.selectedResponseId);
  }

  prevResponse() {
    const index = this.responses.findIndex(r => r.id === this.selectedResponse.id);
    if (index > 0) {
      this.selectedResponse = this.responses[index - 1];
      this.selectedResponseId = this.selectedResponse.id;
    }
  }

  nextResponse() {
    const index = this.responses.findIndex(r => r.id === this.selectedResponse.id);
    if (index < this.responses.length - 1) {
      this.selectedResponse = this.responses[index + 1];
      this.selectedResponseId = this.selectedResponse.id;
    }
  }

  private fetchSurvey(surveyId: string) {
    this.surveyService.getSurvey(surveyId).subscribe({
      next: (survey) => {
        console.log(survey);
        this.survey = survey;
        this.survey.questions.forEach(question => {
          this.questionMap[question.id || ''] = question.questionText || '';
        });
      },
      error: (error) => {
        console.error(error);
      },
      complete: () => {
        console.log("get survey complete");
      }
    });
  }
}

function getNewQuestion(): Question {
  return {
    questionText: '',
    questionType: '',
    required: false,
    options: [],
    minValue: NaN,
    maxValue: NaN
  };
}

function getNewSurvey() {
  return {
    name: '',
    anonymous: false,
    multiCompletion: false,
    visibility: 'public',
    questions: [getNewQuestion()],
    allowedEmails: []
  };
}
