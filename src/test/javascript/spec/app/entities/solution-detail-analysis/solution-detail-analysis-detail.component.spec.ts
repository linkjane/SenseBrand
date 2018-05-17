/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { SolutionDetailAnalysisDetailComponent } from '../../../../../../main/webapp/app/entities/solution-detail-analysis/solution-detail-analysis-detail.component';
import { SolutionDetailAnalysisService } from '../../../../../../main/webapp/app/entities/solution-detail-analysis/solution-detail-analysis.service';
import { SolutionDetailAnalysis } from '../../../../../../main/webapp/app/entities/solution-detail-analysis/solution-detail-analysis.model';

describe('Component Tests', () => {

    describe('SolutionDetailAnalysis Management Detail Component', () => {
        let comp: SolutionDetailAnalysisDetailComponent;
        let fixture: ComponentFixture<SolutionDetailAnalysisDetailComponent>;
        let service: SolutionDetailAnalysisService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [SolutionDetailAnalysisDetailComponent],
                providers: [
                    SolutionDetailAnalysisService
                ]
            })
            .overrideTemplate(SolutionDetailAnalysisDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SolutionDetailAnalysisDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SolutionDetailAnalysisService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new SolutionDetailAnalysis(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.solutionDetailAnalysis).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
