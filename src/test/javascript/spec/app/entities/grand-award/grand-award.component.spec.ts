/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { GrandAwardComponent } from '../../../../../../main/webapp/app/entities/grand-award/grand-award.component';
import { GrandAwardService } from '../../../../../../main/webapp/app/entities/grand-award/grand-award.service';
import { GrandAward } from '../../../../../../main/webapp/app/entities/grand-award/grand-award.model';

describe('Component Tests', () => {

    describe('GrandAward Management Component', () => {
        let comp: GrandAwardComponent;
        let fixture: ComponentFixture<GrandAwardComponent>;
        let service: GrandAwardService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [GrandAwardComponent],
                providers: [
                    GrandAwardService
                ]
            })
            .overrideTemplate(GrandAwardComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GrandAwardComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrandAwardService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new GrandAward(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.grandAwards[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
