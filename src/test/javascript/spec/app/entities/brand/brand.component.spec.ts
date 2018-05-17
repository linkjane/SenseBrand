/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { BrandComponent } from '../../../../../../main/webapp/app/entities/brand/brand.component';
import { BrandService } from '../../../../../../main/webapp/app/entities/brand/brand.service';
import { Brand } from '../../../../../../main/webapp/app/entities/brand/brand.model';

describe('Component Tests', () => {

    describe('Brand Management Component', () => {
        let comp: BrandComponent;
        let fixture: ComponentFixture<BrandComponent>;
        let service: BrandService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [BrandComponent],
                providers: [
                    BrandService
                ]
            })
            .overrideTemplate(BrandComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BrandComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BrandService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Brand(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.brands[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
