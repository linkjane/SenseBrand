/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { IndustryAllComponent } from '../../../../../../main/webapp/app/entities/industry-all/industry-all.component';
import { IndustryAllService } from '../../../../../../main/webapp/app/entities/industry-all/industry-all.service';
import { IndustryAll } from '../../../../../../main/webapp/app/entities/industry-all/industry-all.model';

describe('Component Tests', () => {

    describe('IndustryAll Management Component', () => {
        let comp: IndustryAllComponent;
        let fixture: ComponentFixture<IndustryAllComponent>;
        let service: IndustryAllService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [IndustryAllComponent],
                providers: [
                    IndustryAllService
                ]
            })
            .overrideTemplate(IndustryAllComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IndustryAllComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndustryAllService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new IndustryAll(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.industryAlls[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
