import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {SurveyTemplateService} from "../service/survey-template.service";
import {AnswerService} from "../service/answer.service";
import {SurveyAnswer} from "../domain/survey-answer";

@Component({
  selector: 'app-fill-survey',
  templateUrl: './fill-survey.component.html',
  styleUrls: ['./fill-survey.component.css']
})
export class FillSurveyComponent implements OnInit {
  template: any;
  answers: any = {};

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private surveyTemplateService: SurveyTemplateService,
    private answerService: AnswerService,
    private router: Router
  ) {
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.surveyTemplateService.getSurvey(id).subscribe((data: any) => {
        this.template = data;
        // Initialize the answers object for each question
        for (const question of this.template.questions) {
          this.answers[question.id] = {questionId: question.id};
        }
      });
    }
  }

  saveAnswer() {
    const surveyAnswers: SurveyAnswer = {
      surveyId: this.template.id,
      answers: Object.keys(this.answers).map((questionId: any) => this.answers[questionId])
    };
    this.answerService.saveAnswers(surveyAnswers).subscribe((data: any) => {
      alert('Kérdőív mentve!');
      this.router.navigate(['/surveys']);
    });
  }
}
