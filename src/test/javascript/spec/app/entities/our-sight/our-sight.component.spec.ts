/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { OurSightComponent } from '../../../../../../main/webapp/app/entities/our-sight/our-sight.component';
import { OurSightService } from '../../../../../../main/webapp/app/entities/our-sight/our-sight.service';
import { OurSight } from '../../../../../../main/webapp/app/entities/our-sight/our-sight.model';

describe('Component Tests', () => {

    describe('OurSight Management Component', () => {
        let comp: OurSightComponent;
        let fixture: ComponentFixture<OurSightComponent>;
        let service: OurSightService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [OurSightComponent],
                providers: [
                    OurSightService
                ]
            })
            .overrideTemplate(OurSightComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OurSightComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OurSightService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new OurSight(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.ourSights[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
