/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { IndustryTypeNameComponent } from '../../../../../../main/webapp/app/entities/industry-type-name/industry-type-name.component';
import { IndustryTypeNameService } from '../../../../../../main/webapp/app/entities/industry-type-name/industry-type-name.service';
import { IndustryTypeName } from '../../../../../../main/webapp/app/entities/industry-type-name/industry-type-name.model';

describe('Component Tests', () => {

    describe('IndustryTypeName Management Component', () => {
        let comp: IndustryTypeNameComponent;
        let fixture: ComponentFixture<IndustryTypeNameComponent>;
        let service: IndustryTypeNameService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [IndustryTypeNameComponent],
                providers: [
                    IndustryTypeNameService
                ]
            })
            .overrideTemplate(IndustryTypeNameComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IndustryTypeNameComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndustryTypeNameService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new IndustryTypeName(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.industryTypeNames[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
