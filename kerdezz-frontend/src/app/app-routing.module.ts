import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";
import {SurveyEditorComponent} from "./survey-editor/survey-editor.component";

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: '', component: HomeComponent},
  {path: 'edit-survey', component: SurveyEditorComponent},
  {path: 'edit-survey/:id', component: SurveyEditorComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
