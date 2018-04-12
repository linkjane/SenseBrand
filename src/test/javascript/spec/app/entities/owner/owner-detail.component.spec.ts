/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { OwnerDetailComponent } from '../../../../../../main/webapp/app/entities/owner/owner-detail.component';
import { OwnerService } from '../../../../../../main/webapp/app/entities/owner/owner.service';
import { Owner } from '../../../../../../main/webapp/app/entities/owner/owner.model';

describe('Component Tests', () => {

    describe('Owner Management Detail Component', () => {
        let comp: OwnerDetailComponent;
        let fixture: ComponentFixture<OwnerDetailComponent>;
        let service: OwnerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [OwnerDetailComponent],
                providers: [
                    OwnerService
                ]
            })
            .overrideTemplate(OwnerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OwnerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OwnerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Owner(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.owner).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
