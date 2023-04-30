import {Component, Input} from '@angular/core';
import {Option} from "../domain/survey";

@Component({
  selector: 'app-answer-options',
  templateUrl: './answer-options.component.html',
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
