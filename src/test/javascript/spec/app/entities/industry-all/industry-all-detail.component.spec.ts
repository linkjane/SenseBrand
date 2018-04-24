/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { IndustryAllDetailComponent } from '../../../../../../main/webapp/app/entities/industry-all/industry-all-detail.component';
import { IndustryAllService } from '../../../../../../main/webapp/app/entities/industry-all/industry-all.service';
import { IndustryAll } from '../../../../../../main/webapp/app/entities/industry-all/industry-all.model';

describe('Component Tests', () => {

    describe('IndustryAll Management Detail Component', () => {
        let comp: IndustryAllDetailComponent;
        let fixture: ComponentFixture<IndustryAllDetailComponent>;
        let service: IndustryAllService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [IndustryAllDetailComponent],
                providers: [
                    IndustryAllService
                ]
            })
            .overrideTemplate(IndustryAllDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IndustryAllDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndustryAllService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new IndustryAll(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.industryAll).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
