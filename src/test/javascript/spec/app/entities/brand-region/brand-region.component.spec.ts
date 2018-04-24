/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { BrandRegionComponent } from '../../../../../../main/webapp/app/entities/brand-region/brand-region.component';
import { BrandRegionService } from '../../../../../../main/webapp/app/entities/brand-region/brand-region.service';
import { BrandRegion } from '../../../../../../main/webapp/app/entities/brand-region/brand-region.model';

describe('Component Tests', () => {

    describe('BrandRegion Management Component', () => {
        let comp: BrandRegionComponent;
        let fixture: ComponentFixture<BrandRegionComponent>;
        let service: BrandRegionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [BrandRegionComponent],
                providers: [
                    BrandRegionService
                ]
            })
            .overrideTemplate(BrandRegionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BrandRegionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BrandRegionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new BrandRegion(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.brandRegions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
