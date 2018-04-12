/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { OwnerComponent } from '../../../../../../main/webapp/app/entities/owner/owner.component';
import { OwnerService } from '../../../../../../main/webapp/app/entities/owner/owner.service';
import { Owner } from '../../../../../../main/webapp/app/entities/owner/owner.model';

describe('Component Tests', () => {

    describe('Owner Management Component', () => {
        let comp: OwnerComponent;
        let fixture: ComponentFixture<OwnerComponent>;
        let service: OwnerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [OwnerComponent],
                providers: [
                    OwnerService
                ]
            })
            .overrideTemplate(OwnerComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OwnerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OwnerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Owner(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.owners[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
