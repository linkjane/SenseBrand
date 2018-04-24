/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { OurSightDetailComponent } from '../../../../../../main/webapp/app/entities/our-sight/our-sight-detail.component';
import { OurSightService } from '../../../../../../main/webapp/app/entities/our-sight/our-sight.service';
import { OurSight } from '../../../../../../main/webapp/app/entities/our-sight/our-sight.model';

describe('Component Tests', () => {

    describe('OurSight Management Detail Component', () => {
        let comp: OurSightDetailComponent;
        let fixture: ComponentFixture<OurSightDetailComponent>;
        let service: OurSightService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [OurSightDetailComponent],
                providers: [
                    OurSightService
                ]
            })
            .overrideTemplate(OurSightDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OurSightDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OurSightService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new OurSight(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.ourSight).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
