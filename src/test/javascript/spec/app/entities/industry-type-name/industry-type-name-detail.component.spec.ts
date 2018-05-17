/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { IndustryTypeNameDetailComponent } from '../../../../../../main/webapp/app/entities/industry-type-name/industry-type-name-detail.component';
import { IndustryTypeNameService } from '../../../../../../main/webapp/app/entities/industry-type-name/industry-type-name.service';
import { IndustryTypeName } from '../../../../../../main/webapp/app/entities/industry-type-name/industry-type-name.model';

describe('Component Tests', () => {

    describe('IndustryTypeName Management Detail Component', () => {
        let comp: IndustryTypeNameDetailComponent;
        let fixture: ComponentFixture<IndustryTypeNameDetailComponent>;
        let service: IndustryTypeNameService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [IndustryTypeNameDetailComponent],
                providers: [
                    IndustryTypeNameService
                ]
            })
            .overrideTemplate(IndustryTypeNameDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IndustryTypeNameDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndustryTypeNameService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new IndustryTypeName(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.industryTypeName).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
