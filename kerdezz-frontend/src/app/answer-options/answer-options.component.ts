import {Component, Input} from '@angular/core';
import {Option} from "../domain/survey";

@Component({
  selector: 'app-answer-options',
  template:`<div class="form-group">
    <div class="row py-1" *ngFor="let item of options; let i = index">
      <div class="col-11">
        <input type="text" class="form-control" [(ngModel)]="options[i].value"/>
      </div>
      <div class="col-1">
        <button class="btn btn-close" (click)="removeOption(i)"></button>
      </div>
    </div>
    <div class="row py-1">
      <div class="col-3">
        <button class="btn btn-light rounded-0" (click)="addOption()">
          <i class="bi bi-arrow-return-right" (click)="addOption()"></i>
          Új lehetőség
        </button>
      </div>
    </div>
  </div>`,
  styleUrls: ['./answer-options.component.css']
})
export class AnswerOptionsComponent {
  @Input() options: Option[];

  addOption() {
    this.options.push({value: ''});
  }

  removeOption(index: number) {
    this.options.splice(index, 1);
  }
}
