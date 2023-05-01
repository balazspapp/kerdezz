import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";
import {SurveyEditorComponent} from "./survey-editor/survey-editor.component";
import {FillSurveyComponent} from "./fill-survey/fill-survey.component";

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: '', component: HomeComponent},
  {path: 'edit-survey', component: SurveyEditorComponent},
  {path: 'edit-survey/:id', component: SurveyEditorComponent},
  {path: 'fill-survey/:id', component: FillSurveyComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
