/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { SolutionDetailImgComponent } from '../../../../../../main/webapp/app/entities/solution-detail-img/solution-detail-img.component';
import { SolutionDetailImgService } from '../../../../../../main/webapp/app/entities/solution-detail-img/solution-detail-img.service';
import { SolutionDetailImg } from '../../../../../../main/webapp/app/entities/solution-detail-img/solution-detail-img.model';

describe('Component Tests', () => {

    describe('SolutionDetailImg Management Component', () => {
        let comp: SolutionDetailImgComponent;
        let fixture: ComponentFixture<SolutionDetailImgComponent>;
        let service: SolutionDetailImgService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [SolutionDetailImgComponent],
                providers: [
                    SolutionDetailImgService
                ]
            })
            .overrideTemplate(SolutionDetailImgComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SolutionDetailImgComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SolutionDetailImgService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new SolutionDetailImg(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.solutionDetailImgs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
