/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { DesignerAwardDetailComponent } from '../../../../../../main/webapp/app/entities/designer-award/designer-award-detail.component';
import { DesignerAwardService } from '../../../../../../main/webapp/app/entities/designer-award/designer-award.service';
import { DesignerAward } from '../../../../../../main/webapp/app/entities/designer-award/designer-award.model';

describe('Component Tests', () => {

    describe('DesignerAward Management Detail Component', () => {
        let comp: DesignerAwardDetailComponent;
        let fixture: ComponentFixture<DesignerAwardDetailComponent>;
        let service: DesignerAwardService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [DesignerAwardDetailComponent],
                providers: [
                    DesignerAwardService
                ]
            })
            .overrideTemplate(DesignerAwardDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DesignerAwardDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignerAwardService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DesignerAward(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.designerAward).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
