import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./home/home.component";
import {SurveyEditorComponent} from "./survey-editor/survey-editor.component";
import {FillSurveyComponent} from "./fill-survey/fill-survey.component";
import {SurveyListComponent} from "./survey-list/survey-list.component";

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'home', component: HomeComponent},
  {path: 'survey-list', component: SurveyListComponent},
  {path: 'edit-survey', component: SurveyEditorComponent},
  {path: 'edit-survey/:id', component: SurveyEditorComponent},
  {path: 'fill-survey/:id', component: FillSurveyComponent},
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
