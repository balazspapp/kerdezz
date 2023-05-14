import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {NgbCollapseModule, NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {SurveyEditorComponent} from './survey-editor/survey-editor.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {SurveyListComponent} from './survey-list/survey-list.component';
import {HttpClientModule} from "@angular/common/http";
import { QuestionEditorComponent } from './question-editor/question-editor.component';
import { AnswerOptionsComponent } from './answer-options/answer-options.component';
import { FillSurveyComponent } from './fill-survey/fill-survey.component';
import { AnswerListComponent } from './answer-list/answer-list.component';
import {LocalDatetimePipe} from "./util/local-datetime.pipe";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SurveyEditorComponent,
    SurveyListComponent,
    QuestionEditorComponent,
    AnswerOptionsComponent,
    FillSurveyComponent,
    AnswerListComponent,
    LocalDatetimePipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbCollapseModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
