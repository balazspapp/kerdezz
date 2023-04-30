import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {LoginComponent} from './login/login.component';
import {NgbCollapseModule} from "@ng-bootstrap/ng-bootstrap";
import {SurveyEditorComponent} from './survey-editor/survey-editor.component';
import {FormsModule} from "@angular/forms";
import {SurveyListComponent} from './survey-list/survey-list.component';
import {HttpClientModule} from "@angular/common/http";
import { QuestionEditorComponent } from './question-editor/question-editor.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    SurveyEditorComponent,
    SurveyListComponent,
    QuestionEditorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbCollapseModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
