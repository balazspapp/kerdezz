import { TestBed } from '@angular/core/testing';

import { SurveyStatsService } from './survey-stats.service';

describe('SurveyStatsServiceService', () => {
  let service: SurveyStatsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SurveyStatsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
