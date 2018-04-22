/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { DesignerIdeaMediaComponent } from '../../../../../../main/webapp/app/entities/designer-idea-media/designer-idea-media.component';
import { DesignerIdeaMediaService } from '../../../../../../main/webapp/app/entities/designer-idea-media/designer-idea-media.service';
import { DesignerIdeaMedia } from '../../../../../../main/webapp/app/entities/designer-idea-media/designer-idea-media.model';

describe('Component Tests', () => {

    describe('DesignerIdeaMedia Management Component', () => {
        let comp: DesignerIdeaMediaComponent;
        let fixture: ComponentFixture<DesignerIdeaMediaComponent>;
        let service: DesignerIdeaMediaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [DesignerIdeaMediaComponent],
                providers: [
                    DesignerIdeaMediaService
                ]
            })
            .overrideTemplate(DesignerIdeaMediaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DesignerIdeaMediaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignerIdeaMediaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DesignerIdeaMedia(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.designerIdeaMedias[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
