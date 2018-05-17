/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { DesignerIdeaMediaDetailComponent } from '../../../../../../main/webapp/app/entities/designer-idea-media/designer-idea-media-detail.component';
import { DesignerIdeaMediaService } from '../../../../../../main/webapp/app/entities/designer-idea-media/designer-idea-media.service';
import { DesignerIdeaMedia } from '../../../../../../main/webapp/app/entities/designer-idea-media/designer-idea-media.model';

describe('Component Tests', () => {

    describe('DesignerIdeaMedia Management Detail Component', () => {
        let comp: DesignerIdeaMediaDetailComponent;
        let fixture: ComponentFixture<DesignerIdeaMediaDetailComponent>;
        let service: DesignerIdeaMediaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [DesignerIdeaMediaDetailComponent],
                providers: [
                    DesignerIdeaMediaService
                ]
            })
            .overrideTemplate(DesignerIdeaMediaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DesignerIdeaMediaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignerIdeaMediaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DesignerIdeaMedia(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.designerIdeaMedia).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
