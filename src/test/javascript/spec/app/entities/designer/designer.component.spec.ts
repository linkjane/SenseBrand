/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { DesignerComponent } from '../../../../../../main/webapp/app/entities/designer/designer.component';
import { DesignerService } from '../../../../../../main/webapp/app/entities/designer/designer.service';
import { Designer } from '../../../../../../main/webapp/app/entities/designer/designer.model';

describe('Component Tests', () => {

    describe('Designer Management Component', () => {
        let comp: DesignerComponent;
        let fixture: ComponentFixture<DesignerComponent>;
        let service: DesignerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [DesignerComponent],
                providers: [
                    DesignerService
                ]
            })
            .overrideTemplate(DesignerComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DesignerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Designer(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.designers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
