/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { SolutionDetailAnalysisComponent } from '../../../../../../main/webapp/app/entities/solution-detail-analysis/solution-detail-analysis.component';
import { SolutionDetailAnalysisService } from '../../../../../../main/webapp/app/entities/solution-detail-analysis/solution-detail-analysis.service';
import { SolutionDetailAnalysis } from '../../../../../../main/webapp/app/entities/solution-detail-analysis/solution-detail-analysis.model';

describe('Component Tests', () => {

    describe('SolutionDetailAnalysis Management Component', () => {
        let comp: SolutionDetailAnalysisComponent;
        let fixture: ComponentFixture<SolutionDetailAnalysisComponent>;
        let service: SolutionDetailAnalysisService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [SolutionDetailAnalysisComponent],
                providers: [
                    SolutionDetailAnalysisService
                ]
            })
            .overrideTemplate(SolutionDetailAnalysisComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SolutionDetailAnalysisComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SolutionDetailAnalysisService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new SolutionDetailAnalysis(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.solutionDetailAnalyses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
