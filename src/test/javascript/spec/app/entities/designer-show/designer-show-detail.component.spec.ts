/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { DesignerShowDetailComponent } from '../../../../../../main/webapp/app/entities/designer-show/designer-show-detail.component';
import { DesignerShowService } from '../../../../../../main/webapp/app/entities/designer-show/designer-show.service';
import { DesignerShow } from '../../../../../../main/webapp/app/entities/designer-show/designer-show.model';

describe('Component Tests', () => {

    describe('DesignerShow Management Detail Component', () => {
        let comp: DesignerShowDetailComponent;
        let fixture: ComponentFixture<DesignerShowDetailComponent>;
        let service: DesignerShowService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [DesignerShowDetailComponent],
                providers: [
                    DesignerShowService
                ]
            })
            .overrideTemplate(DesignerShowDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DesignerShowDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignerShowService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DesignerShow(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.designerShow).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
