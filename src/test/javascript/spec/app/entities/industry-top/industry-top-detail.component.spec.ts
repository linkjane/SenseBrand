/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { IndustryTopDetailComponent } from '../../../../../../main/webapp/app/entities/industry-top/industry-top-detail.component';
import { IndustryTopService } from '../../../../../../main/webapp/app/entities/industry-top/industry-top.service';
import { IndustryTop } from '../../../../../../main/webapp/app/entities/industry-top/industry-top.model';

describe('Component Tests', () => {

    describe('IndustryTop Management Detail Component', () => {
        let comp: IndustryTopDetailComponent;
        let fixture: ComponentFixture<IndustryTopDetailComponent>;
        let service: IndustryTopService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [IndustryTopDetailComponent],
                providers: [
                    IndustryTopService
                ]
            })
            .overrideTemplate(IndustryTopDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IndustryTopDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndustryTopService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new IndustryTop(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.industryTop).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
