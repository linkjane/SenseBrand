/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { DesignerShowImgDetailComponent } from '../../../../../../main/webapp/app/entities/designer-show-img/designer-show-img-detail.component';
import { DesignerShowImgService } from '../../../../../../main/webapp/app/entities/designer-show-img/designer-show-img.service';
import { DesignerShowImg } from '../../../../../../main/webapp/app/entities/designer-show-img/designer-show-img.model';

describe('Component Tests', () => {

    describe('DesignerShowImg Management Detail Component', () => {
        let comp: DesignerShowImgDetailComponent;
        let fixture: ComponentFixture<DesignerShowImgDetailComponent>;
        let service: DesignerShowImgService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [DesignerShowImgDetailComponent],
                providers: [
                    DesignerShowImgService
                ]
            })
            .overrideTemplate(DesignerShowImgDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DesignerShowImgDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignerShowImgService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DesignerShowImg(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.designerShowImg).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
