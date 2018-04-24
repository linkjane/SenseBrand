/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { IndustrySecondClassComponent } from '../../../../../../main/webapp/app/entities/industry-second-class/industry-second-class.component';
import { IndustrySecondClassService } from '../../../../../../main/webapp/app/entities/industry-second-class/industry-second-class.service';
import { IndustrySecondClass } from '../../../../../../main/webapp/app/entities/industry-second-class/industry-second-class.model';

describe('Component Tests', () => {

    describe('IndustrySecondClass Management Component', () => {
        let comp: IndustrySecondClassComponent;
        let fixture: ComponentFixture<IndustrySecondClassComponent>;
        let service: IndustrySecondClassService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [IndustrySecondClassComponent],
                providers: [
                    IndustrySecondClassService
                ]
            })
            .overrideTemplate(IndustrySecondClassComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IndustrySecondClassComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndustrySecondClassService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new IndustrySecondClass(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.industrySecondClasses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
