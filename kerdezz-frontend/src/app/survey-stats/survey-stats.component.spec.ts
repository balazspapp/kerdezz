import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SurveyStatsComponent } from './survey-stats.component';

describe('SurveyStatsComponentComponent', () => {
  let component: SurveyStatsComponent;
  let fixture: ComponentFixture<SurveyStatsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SurveyStatsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SurveyStatsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
