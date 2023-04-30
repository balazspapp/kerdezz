import {Component, Input} from '@angular/core';
import {Question} from "../domain/survey";

@Component({
  selector: 'app-question-editor',
  templateUrl: './question-editor.component.html',
  styleUrls: ['./question-editor.component.css']
})
export class QuestionEditorComponent {
  @Input() question: Question;
}
