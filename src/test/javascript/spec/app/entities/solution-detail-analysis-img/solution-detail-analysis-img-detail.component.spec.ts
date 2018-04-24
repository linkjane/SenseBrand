/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { SolutionDetailAnalysisImgDetailComponent } from '../../../../../../main/webapp/app/entities/solution-detail-analysis-img/solution-detail-analysis-img-detail.component';
import { SolutionDetailAnalysisImgService } from '../../../../../../main/webapp/app/entities/solution-detail-analysis-img/solution-detail-analysis-img.service';
import { SolutionDetailAnalysisImg } from '../../../../../../main/webapp/app/entities/solution-detail-analysis-img/solution-detail-analysis-img.model';

describe('Component Tests', () => {

    describe('SolutionDetailAnalysisImg Management Detail Component', () => {
        let comp: SolutionDetailAnalysisImgDetailComponent;
        let fixture: ComponentFixture<SolutionDetailAnalysisImgDetailComponent>;
        let service: SolutionDetailAnalysisImgService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [SolutionDetailAnalysisImgDetailComponent],
                providers: [
                    SolutionDetailAnalysisImgService
                ]
            })
            .overrideTemplate(SolutionDetailAnalysisImgDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SolutionDetailAnalysisImgDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SolutionDetailAnalysisImgService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new SolutionDetailAnalysisImg(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.solutionDetailAnalysisImg).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
