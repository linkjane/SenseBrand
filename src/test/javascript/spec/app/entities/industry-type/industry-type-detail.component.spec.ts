/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { IndustryTypeDetailComponent } from '../../../../../../main/webapp/app/entities/industry-type/industry-type-detail.component';
import { IndustryTypeService } from '../../../../../../main/webapp/app/entities/industry-type/industry-type.service';
import { IndustryType } from '../../../../../../main/webapp/app/entities/industry-type/industry-type.model';

describe('Component Tests', () => {

    describe('IndustryType Management Detail Component', () => {
        let comp: IndustryTypeDetailComponent;
        let fixture: ComponentFixture<IndustryTypeDetailComponent>;
        let service: IndustryTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [IndustryTypeDetailComponent],
                providers: [
                    IndustryTypeService
                ]
            })
            .overrideTemplate(IndustryTypeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IndustryTypeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndustryTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new IndustryType(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.industryType).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
