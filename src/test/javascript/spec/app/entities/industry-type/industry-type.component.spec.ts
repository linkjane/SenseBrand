/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { IndustryTypeComponent } from '../../../../../../main/webapp/app/entities/industry-type/industry-type.component';
import { IndustryTypeService } from '../../../../../../main/webapp/app/entities/industry-type/industry-type.service';
import { IndustryType } from '../../../../../../main/webapp/app/entities/industry-type/industry-type.model';

describe('Component Tests', () => {

    describe('IndustryType Management Component', () => {
        let comp: IndustryTypeComponent;
        let fixture: ComponentFixture<IndustryTypeComponent>;
        let service: IndustryTypeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [IndustryTypeComponent],
                providers: [
                    IndustryTypeService
                ]
            })
            .overrideTemplate(IndustryTypeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IndustryTypeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndustryTypeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new IndustryType(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.industryTypes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
