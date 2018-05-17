/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { BrandSubDetailsComponent } from '../../../../../../main/webapp/app/entities/brand-sub-details/brand-sub-details.component';
import { BrandSubDetailsService } from '../../../../../../main/webapp/app/entities/brand-sub-details/brand-sub-details.service';
import { BrandSubDetails } from '../../../../../../main/webapp/app/entities/brand-sub-details/brand-sub-details.model';

describe('Component Tests', () => {

    describe('BrandSubDetails Management Component', () => {
        let comp: BrandSubDetailsComponent;
        let fixture: ComponentFixture<BrandSubDetailsComponent>;
        let service: BrandSubDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [BrandSubDetailsComponent],
                providers: [
                    BrandSubDetailsService
                ]
            })
            .overrideTemplate(BrandSubDetailsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BrandSubDetailsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BrandSubDetailsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new BrandSubDetails(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.brandSubDetails[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
