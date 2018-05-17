/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { IndustryTopComponent } from '../../../../../../main/webapp/app/entities/industry-top/industry-top.component';
import { IndustryTopService } from '../../../../../../main/webapp/app/entities/industry-top/industry-top.service';
import { IndustryTop } from '../../../../../../main/webapp/app/entities/industry-top/industry-top.model';

describe('Component Tests', () => {

    describe('IndustryTop Management Component', () => {
        let comp: IndustryTopComponent;
        let fixture: ComponentFixture<IndustryTopComponent>;
        let service: IndustryTopService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [IndustryTopComponent],
                providers: [
                    IndustryTopService
                ]
            })
            .overrideTemplate(IndustryTopComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IndustryTopComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndustryTopService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new IndustryTop(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.industryTops[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
