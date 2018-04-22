/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { DesignerShowComponent } from '../../../../../../main/webapp/app/entities/designer-show/designer-show.component';
import { DesignerShowService } from '../../../../../../main/webapp/app/entities/designer-show/designer-show.service';
import { DesignerShow } from '../../../../../../main/webapp/app/entities/designer-show/designer-show.model';

describe('Component Tests', () => {

    describe('DesignerShow Management Component', () => {
        let comp: DesignerShowComponent;
        let fixture: ComponentFixture<DesignerShowComponent>;
        let service: DesignerShowService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [DesignerShowComponent],
                providers: [
                    DesignerShowService
                ]
            })
            .overrideTemplate(DesignerShowComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DesignerShowComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignerShowService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DesignerShow(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.designerShows[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
