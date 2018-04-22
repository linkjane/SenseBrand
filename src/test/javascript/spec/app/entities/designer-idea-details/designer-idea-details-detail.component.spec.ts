/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { DesignerIdeaDetailsDetailComponent } from '../../../../../../main/webapp/app/entities/designer-idea-details/designer-idea-details-detail.component';
import { DesignerIdeaDetailsService } from '../../../../../../main/webapp/app/entities/designer-idea-details/designer-idea-details.service';
import { DesignerIdeaDetails } from '../../../../../../main/webapp/app/entities/designer-idea-details/designer-idea-details.model';

describe('Component Tests', () => {

    describe('DesignerIdeaDetails Management Detail Component', () => {
        let comp: DesignerIdeaDetailsDetailComponent;
        let fixture: ComponentFixture<DesignerIdeaDetailsDetailComponent>;
        let service: DesignerIdeaDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [DesignerIdeaDetailsDetailComponent],
                providers: [
                    DesignerIdeaDetailsService
                ]
            })
            .overrideTemplate(DesignerIdeaDetailsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DesignerIdeaDetailsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignerIdeaDetailsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DesignerIdeaDetails(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.designerIdeaDetails).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
