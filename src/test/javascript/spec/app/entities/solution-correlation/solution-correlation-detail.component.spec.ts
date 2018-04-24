/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { SolutionCorrelationDetailComponent } from '../../../../../../main/webapp/app/entities/solution-correlation/solution-correlation-detail.component';
import { SolutionCorrelationService } from '../../../../../../main/webapp/app/entities/solution-correlation/solution-correlation.service';
import { SolutionCorrelation } from '../../../../../../main/webapp/app/entities/solution-correlation/solution-correlation.model';

describe('Component Tests', () => {

    describe('SolutionCorrelation Management Detail Component', () => {
        let comp: SolutionCorrelationDetailComponent;
        let fixture: ComponentFixture<SolutionCorrelationDetailComponent>;
        let service: SolutionCorrelationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [SolutionCorrelationDetailComponent],
                providers: [
                    SolutionCorrelationService
                ]
            })
            .overrideTemplate(SolutionCorrelationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SolutionCorrelationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SolutionCorrelationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new SolutionCorrelation(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.solutionCorrelation).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
