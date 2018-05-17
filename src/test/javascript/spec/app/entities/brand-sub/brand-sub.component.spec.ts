/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { BrandSubComponent } from '../../../../../../main/webapp/app/entities/brand-sub/brand-sub.component';
import { BrandSubService } from '../../../../../../main/webapp/app/entities/brand-sub/brand-sub.service';
import { BrandSub } from '../../../../../../main/webapp/app/entities/brand-sub/brand-sub.model';

describe('Component Tests', () => {

    describe('BrandSub Management Component', () => {
        let comp: BrandSubComponent;
        let fixture: ComponentFixture<BrandSubComponent>;
        let service: BrandSubService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [BrandSubComponent],
                providers: [
                    BrandSubService
                ]
            })
            .overrideTemplate(BrandSubComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BrandSubComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BrandSubService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new BrandSub(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.brandSubs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
