/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { IndustryFirstClassComponent } from '../../../../../../main/webapp/app/entities/industry-first-class/industry-first-class.component';
import { IndustryFirstClassService } from '../../../../../../main/webapp/app/entities/industry-first-class/industry-first-class.service';
import { IndustryFirstClass } from '../../../../../../main/webapp/app/entities/industry-first-class/industry-first-class.model';

describe('Component Tests', () => {

    describe('IndustryFirstClass Management Component', () => {
        let comp: IndustryFirstClassComponent;
        let fixture: ComponentFixture<IndustryFirstClassComponent>;
        let service: IndustryFirstClassService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [IndustryFirstClassComponent],
                providers: [
                    IndustryFirstClassService
                ]
            })
            .overrideTemplate(IndustryFirstClassComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IndustryFirstClassComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndustryFirstClassService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new IndustryFirstClass(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.industryFirstClasses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
