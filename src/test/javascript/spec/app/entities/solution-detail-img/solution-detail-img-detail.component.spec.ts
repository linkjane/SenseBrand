/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { SolutionDetailImgDetailComponent } from '../../../../../../main/webapp/app/entities/solution-detail-img/solution-detail-img-detail.component';
import { SolutionDetailImgService } from '../../../../../../main/webapp/app/entities/solution-detail-img/solution-detail-img.service';
import { SolutionDetailImg } from '../../../../../../main/webapp/app/entities/solution-detail-img/solution-detail-img.model';

describe('Component Tests', () => {

    describe('SolutionDetailImg Management Detail Component', () => {
        let comp: SolutionDetailImgDetailComponent;
        let fixture: ComponentFixture<SolutionDetailImgDetailComponent>;
        let service: SolutionDetailImgService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [SolutionDetailImgDetailComponent],
                providers: [
                    SolutionDetailImgService
                ]
            })
            .overrideTemplate(SolutionDetailImgDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SolutionDetailImgDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SolutionDetailImgService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new SolutionDetailImg(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.solutionDetailImg).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
