/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenseBrandTestModule } from '../../../test.module';
import { DesignerIdeaDetailsDialogComponent } from '../../../../../../main/webapp/app/entities/designer-idea-details/designer-idea-details-dialog.component';
import { DesignerIdeaDetailsService } from '../../../../../../main/webapp/app/entities/designer-idea-details/designer-idea-details.service';
import { DesignerIdeaDetails } from '../../../../../../main/webapp/app/entities/designer-idea-details/designer-idea-details.model';
import { DesignerService } from '../../../../../../main/webapp/app/entities/designer';

describe('Component Tests', () => {

    describe('DesignerIdeaDetails Management Dialog Component', () => {
        let comp: DesignerIdeaDetailsDialogComponent;
        let fixture: ComponentFixture<DesignerIdeaDetailsDialogComponent>;
        let service: DesignerIdeaDetailsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [DesignerIdeaDetailsDialogComponent],
                providers: [
                    DesignerService,
                    DesignerIdeaDetailsService
                ]
            })
            .overrideTemplate(DesignerIdeaDetailsDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DesignerIdeaDetailsDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignerIdeaDetailsService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DesignerIdeaDetails(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.designerIdeaDetails = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'designerIdeaDetailsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DesignerIdeaDetails();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.designerIdeaDetails = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'designerIdeaDetailsListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
