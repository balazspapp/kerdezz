import {Component, Input, OnInit} from '@angular/core';
import {AnswerStats, SurveyStats} from "../domain/statistics";
import {SurveyStatsService} from "../service/survey-stats.service";

@Component({
  selector: 'app-survey-stats',
  template: `
    <div *ngIf="surveyStats">
      <h2>Survey Stats</h2>
      <div *ngFor="let question of surveyStats.questionStats" class="card p-3 my-3 shadow-sm">
        <div class="card-body">
          <div *ngIf="question.questionId">
            <h5 class="card-title">{{ questionMap[question.questionId] }}</h5>
          </div>
          <div class="card-text">
            <p>Válaszok eloszlása:</p>
            <table class="table table-striped">
              <thead>
              <tr>
                <th>
                  Válasz értéke
                </th>
                <th class="w-25">
                  Előfordulások száma
                </th>
              </tr>
              </thead>
              <tbody>
              <tr *ngFor="let answer of question.answers">
                <td>
                  {{ answer.answerValue }}
                </td>
                <td class="w-25">
                  {{ answer.count }}
                </td>
              </tr>
              </tbody>
            </table>
            <p *ngIf="question.type === 'NUMBER'">Átlag: {{ question.average }}</p>
            <p *ngIf="question.type !== 'OPEN_TEXT'">Leggyakoribb válasz: {{ getMostFrequentAnswer(question.answers) }}</p>
          </div>
        </div>
      </div>
    </div>
  `,
})
export class SurveyStatsComponent implements OnInit {
  @Input() surveyId: string | undefined;
  @Input() questionMap: any;
  surveyStats: SurveyStats | undefined;

  constructor(private surveyStatsService: SurveyStatsService) {
  }

  ngOnInit() {
    console.log("survey stats component init")
    if (this.surveyId != null) {
      this.surveyStatsService.getSurveyStats(this.surveyId)
        .subscribe(stats => {
          this.surveyStats = stats;
          console.log(JSON.stringify(this.surveyStats));
        });
    }
  }

  getMostFrequentAnswer(answers: AnswerStats[]): string {
    let mostFrequentAnswer = '';
    let maxCount = 0;

    for (const answer of answers) {
      if (answer.count > maxCount) {
        maxCount = answer.count;
        mostFrequentAnswer = answer.answerValue || '';
      }
    }

    return mostFrequentAnswer;
  }
}
