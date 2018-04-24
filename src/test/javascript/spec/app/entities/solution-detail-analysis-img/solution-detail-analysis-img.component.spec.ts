/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { SolutionDetailAnalysisImgComponent } from '../../../../../../main/webapp/app/entities/solution-detail-analysis-img/solution-detail-analysis-img.component';
import { SolutionDetailAnalysisImgService } from '../../../../../../main/webapp/app/entities/solution-detail-analysis-img/solution-detail-analysis-img.service';
import { SolutionDetailAnalysisImg } from '../../../../../../main/webapp/app/entities/solution-detail-analysis-img/solution-detail-analysis-img.model';

describe('Component Tests', () => {

    describe('SolutionDetailAnalysisImg Management Component', () => {
        let comp: SolutionDetailAnalysisImgComponent;
        let fixture: ComponentFixture<SolutionDetailAnalysisImgComponent>;
        let service: SolutionDetailAnalysisImgService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [SolutionDetailAnalysisImgComponent],
                providers: [
                    SolutionDetailAnalysisImgService
                ]
            })
            .overrideTemplate(SolutionDetailAnalysisImgComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SolutionDetailAnalysisImgComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SolutionDetailAnalysisImgService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new SolutionDetailAnalysisImg(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.solutionDetailAnalysisImgs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
