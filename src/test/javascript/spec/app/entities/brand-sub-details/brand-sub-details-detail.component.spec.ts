/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { BrandSubDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/brand-sub-details/brand-sub-details-detail.component';
import { BrandSubDetailsService } from '../../../../../../main/webapp/app/entities/brand-sub-details/brand-sub-details.service';
import { BrandSubDetails } from '../../../../../../main/webapp/app/entities/brand-sub-details/brand-sub-details.model';

describe('Component Tests', () => {

    describe('BrandSubDetails Management Detail Component', () => {
        let comp: BrandSubDetailsDetailComponent;
        let fixture: ComponentFixture<BrandSubDetailsDetailComponent>;
        let service: BrandSubDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [BrandSubDetailsDetailComponent],
                providers: [
                    BrandSubDetailsService
                ]
            })
            .overrideTemplate(BrandSubDetailsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BrandSubDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BrandSubDetailsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new BrandSubDetails(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.brandSubDetails).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
