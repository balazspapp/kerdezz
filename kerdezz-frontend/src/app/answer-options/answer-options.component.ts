import {Component, Input} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-answer-options',
  templateUrl: './answer-options.component.html',
  styleUrls: ['./answer-options.component.css']
})
export class AnswerOptionsComponent {
  @Input() options: string[];
  form: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit() {
    this.form = this.fb.group({
      optionList: this.fb.array([])
    });
  }

  createOption(): FormGroup {
    return this.fb.group({
      value: ['', Validators.required]
    });
  }

  addOption() {
    const optionList = this.form.get('optionList') as FormArray;
    optionList.push(this.createOption());
  }

  removeOption(index: number) {
    const optionList = this.form.get('optionList') as FormArray;
    optionList.removeAt(index);
  }
}
