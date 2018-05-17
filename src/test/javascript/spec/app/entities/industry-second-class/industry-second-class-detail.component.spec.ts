/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { IndustrySecondClassDetailComponent } from '../../../../../../main/webapp/app/entities/industry-second-class/industry-second-class-detail.component';
import { IndustrySecondClassService } from '../../../../../../main/webapp/app/entities/industry-second-class/industry-second-class.service';
import { IndustrySecondClass } from '../../../../../../main/webapp/app/entities/industry-second-class/industry-second-class.model';

describe('Component Tests', () => {

    describe('IndustrySecondClass Management Detail Component', () => {
        let comp: IndustrySecondClassDetailComponent;
        let fixture: ComponentFixture<IndustrySecondClassDetailComponent>;
        let service: IndustrySecondClassService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [IndustrySecondClassDetailComponent],
                providers: [
                    IndustrySecondClassService
                ]
            })
            .overrideTemplate(IndustrySecondClassDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IndustrySecondClassDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndustrySecondClassService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new IndustrySecondClass(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.industrySecondClass).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
