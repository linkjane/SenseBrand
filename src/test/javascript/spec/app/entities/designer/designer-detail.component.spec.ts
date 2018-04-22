/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { DesignerDetailComponent } from '../../../../../../main/webapp/app/entities/designer/designer-detail.component';
import { DesignerService } from '../../../../../../main/webapp/app/entities/designer/designer.service';
import { Designer } from '../../../../../../main/webapp/app/entities/designer/designer.model';

describe('Component Tests', () => {

    describe('Designer Management Detail Component', () => {
        let comp: DesignerDetailComponent;
        let fixture: ComponentFixture<DesignerDetailComponent>;
        let service: DesignerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [DesignerDetailComponent],
                providers: [
                    DesignerService
                ]
            })
            .overrideTemplate(DesignerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DesignerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Designer(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.designer).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
