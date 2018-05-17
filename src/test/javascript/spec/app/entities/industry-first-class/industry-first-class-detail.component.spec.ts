/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { IndustryFirstClassDetailComponent } from '../../../../../../main/webapp/app/entities/industry-first-class/industry-first-class-detail.component';
import { IndustryFirstClassService } from '../../../../../../main/webapp/app/entities/industry-first-class/industry-first-class.service';
import { IndustryFirstClass } from '../../../../../../main/webapp/app/entities/industry-first-class/industry-first-class.model';

describe('Component Tests', () => {

    describe('IndustryFirstClass Management Detail Component', () => {
        let comp: IndustryFirstClassDetailComponent;
        let fixture: ComponentFixture<IndustryFirstClassDetailComponent>;
        let service: IndustryFirstClassService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [IndustryFirstClassDetailComponent],
                providers: [
                    IndustryFirstClassService
                ]
            })
            .overrideTemplate(IndustryFirstClassDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IndustryFirstClassDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndustryFirstClassService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new IndustryFirstClass(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.industryFirstClass).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
