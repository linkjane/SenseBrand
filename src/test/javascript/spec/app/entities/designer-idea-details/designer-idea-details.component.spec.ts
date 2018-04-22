/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { DesignerIdeaDetailsComponent } from '../../../../../../main/webapp/app/entities/designer-idea-details/designer-idea-details.component';
import { DesignerIdeaDetailsService } from '../../../../../../main/webapp/app/entities/designer-idea-details/designer-idea-details.service';
import { DesignerIdeaDetails } from '../../../../../../main/webapp/app/entities/designer-idea-details/designer-idea-details.model';

describe('Component Tests', () => {

    describe('DesignerIdeaDetails Management Component', () => {
        let comp: DesignerIdeaDetailsComponent;
        let fixture: ComponentFixture<DesignerIdeaDetailsComponent>;
        let service: DesignerIdeaDetailsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [DesignerIdeaDetailsComponent],
                providers: [
                    DesignerIdeaDetailsService
                ]
            })
            .overrideTemplate(DesignerIdeaDetailsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DesignerIdeaDetailsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignerIdeaDetailsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DesignerIdeaDetails(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.designerIdeaDetails[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
