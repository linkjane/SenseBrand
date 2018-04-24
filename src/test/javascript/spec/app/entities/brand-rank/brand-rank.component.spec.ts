/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { BrandRankComponent } from '../../../../../../main/webapp/app/entities/brand-rank/brand-rank.component';
import { BrandRankService } from '../../../../../../main/webapp/app/entities/brand-rank/brand-rank.service';
import { BrandRank } from '../../../../../../main/webapp/app/entities/brand-rank/brand-rank.model';

describe('Component Tests', () => {

    describe('BrandRank Management Component', () => {
        let comp: BrandRankComponent;
        let fixture: ComponentFixture<BrandRankComponent>;
        let service: BrandRankService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [BrandRankComponent],
                providers: [
                    BrandRankService
                ]
            })
            .overrideTemplate(BrandRankComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BrandRankComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BrandRankService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new BrandRank(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.brandRanks[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
