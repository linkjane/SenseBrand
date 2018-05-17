/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { DesignerShowImgComponent } from '../../../../../../main/webapp/app/entities/designer-show-img/designer-show-img.component';
import { DesignerShowImgService } from '../../../../../../main/webapp/app/entities/designer-show-img/designer-show-img.service';
import { DesignerShowImg } from '../../../../../../main/webapp/app/entities/designer-show-img/designer-show-img.model';

describe('Component Tests', () => {

    describe('DesignerShowImg Management Component', () => {
        let comp: DesignerShowImgComponent;
        let fixture: ComponentFixture<DesignerShowImgComponent>;
        let service: DesignerShowImgService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [DesignerShowImgComponent],
                providers: [
                    DesignerShowImgService
                ]
            })
            .overrideTemplate(DesignerShowImgComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DesignerShowImgComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignerShowImgService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DesignerShowImg(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.designerShowImgs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
