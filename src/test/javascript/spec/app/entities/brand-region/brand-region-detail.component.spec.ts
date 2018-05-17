/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { BrandRegionDetailComponent } from '../../../../../../main/webapp/app/entities/brand-region/brand-region-detail.component';
import { BrandRegionService } from '../../../../../../main/webapp/app/entities/brand-region/brand-region.service';
import { BrandRegion } from '../../../../../../main/webapp/app/entities/brand-region/brand-region.model';

describe('Component Tests', () => {

    describe('BrandRegion Management Detail Component', () => {
        let comp: BrandRegionDetailComponent;
        let fixture: ComponentFixture<BrandRegionDetailComponent>;
        let service: BrandRegionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [BrandRegionDetailComponent],
                providers: [
                    BrandRegionService
                ]
            })
            .overrideTemplate(BrandRegionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BrandRegionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BrandRegionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new BrandRegion(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.brandRegion).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
