/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { SolutionCorrelationComponent } from '../../../../../../main/webapp/app/entities/solution-correlation/solution-correlation.component';
import { SolutionCorrelationService } from '../../../../../../main/webapp/app/entities/solution-correlation/solution-correlation.service';
import { SolutionCorrelation } from '../../../../../../main/webapp/app/entities/solution-correlation/solution-correlation.model';

describe('Component Tests', () => {

    describe('SolutionCorrelation Management Component', () => {
        let comp: SolutionCorrelationComponent;
        let fixture: ComponentFixture<SolutionCorrelationComponent>;
        let service: SolutionCorrelationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [SolutionCorrelationComponent],
                providers: [
                    SolutionCorrelationService
                ]
            })
            .overrideTemplate(SolutionCorrelationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SolutionCorrelationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SolutionCorrelationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new SolutionCorrelation(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.solutionCorrelations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
