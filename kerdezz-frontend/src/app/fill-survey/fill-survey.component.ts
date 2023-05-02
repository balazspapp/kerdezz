import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {FormGroup, FormBuilder, FormControl, Validators, FormArray} from '@angular/forms';
import {SurveyTemplateService} from '../service/survey-template.service';
import {AnswerService} from '../service/answer.service';
import {Answer, SurveyAnswer} from '../domain/survey-answer';
import {Question, Survey} from "../domain/survey";

@Component({
  selector: 'app-fill-survey',
  templateUrl: './fill-survey.component.html',
  styleUrls: ['./fill-survey.component.css']
})
export class FillSurveyComponent implements OnInit {
  template: Survey;
  answersForm: FormGroup;
  surveyForm: FormGroup;
  questions: Question[];
  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private surveyTemplateService: SurveyTemplateService,
    private answerService: AnswerService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
  }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.surveyTemplateService.getSurvey(id).subscribe((data: any) => {
        this.template = data;
        console.log('data:', data)
        this.initForm();
      });
    }
  }

  private initForm() {
    this.questions = this.template.questions;
    this.surveyForm = this.formBuilder.group({});
    this.questions.forEach((question) => {
      let controls;
      if (question.questionType === 'MULTI_CHOICE') {
        const formArray = this.formBuilder.array([], FillSurveyComponent.getRequiredValidator(question));
        question.options.forEach(() => formArray.push(this.formBuilder.control(false)));
        controls = {
          questionId: question.id,
          multiValue: formArray
        };
      } else {
        controls = {
          questionId: question.id,
          value: this.formBuilder.control(null, FillSurveyComponent.getRequiredValidator(question))
        };
      }
      this.surveyForm.addControl(
        this.getFormGroupName(question),
        this.formBuilder.group(controls)
      );
    });
  }

  private static getRequiredValidator(question: Question) {
    return question.required ? Validators.required : null;
  }

  submitForm() {
    const answers: Answer[] = this.convertToAnswers(this.surveyForm.value);
    console.log(answers);
  }

  convertToAnswers(obj: any): Answer[] {
    const result: Answer[] = [];

    for (const key in obj) {
      if (obj.hasOwnProperty(key)) {
        const item = obj[key];

        if (item.multiValue) {
          const multiValue: string[] = item.multiValue
            .map((value: boolean, index: number) => (value ? index.toString() : null))
            .filter((value: string | null) => value !== null);

          result.push({
            questionId: item.questionId,
            multiValue,
          });
        } else {
          result.push({
            questionId: item.questionId,
            value: item.value.toString(),
          });
        }
      }
    }

    return result;
  }

  getFormGroup(question: Question): FormGroup {
    return this.surveyForm.get(this.getFormGroupName(question)) as FormGroup;
  }

  getFormGroupName(question: Question): string {
    return `question_${question.id}`;
  }

}
