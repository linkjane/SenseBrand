/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { BrandRankDetailComponent } from '../../../../../../main/webapp/app/entities/brand-rank/brand-rank-detail.component';
import { BrandRankService } from '../../../../../../main/webapp/app/entities/brand-rank/brand-rank.service';
import { BrandRank } from '../../../../../../main/webapp/app/entities/brand-rank/brand-rank.model';

describe('Component Tests', () => {

    describe('BrandRank Management Detail Component', () => {
        let comp: BrandRankDetailComponent;
        let fixture: ComponentFixture<BrandRankDetailComponent>;
        let service: BrandRankService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [BrandRankDetailComponent],
                providers: [
                    BrandRankService
                ]
            })
            .overrideTemplate(BrandRankDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BrandRankDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BrandRankService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new BrandRank(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.brandRank).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
