/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenseBrandTestModule } from '../../../test.module';
import { DesignerIdeaMediaDialogComponent } from '../../../../../../main/webapp/app/entities/designer-idea-media/designer-idea-media-dialog.component';
import { DesignerIdeaMediaService } from '../../../../../../main/webapp/app/entities/designer-idea-media/designer-idea-media.service';
import { DesignerIdeaMedia } from '../../../../../../main/webapp/app/entities/designer-idea-media/designer-idea-media.model';
import { DesignerService } from '../../../../../../main/webapp/app/entities/designer';

describe('Component Tests', () => {

    describe('DesignerIdeaMedia Management Dialog Component', () => {
        let comp: DesignerIdeaMediaDialogComponent;
        let fixture: ComponentFixture<DesignerIdeaMediaDialogComponent>;
        let service: DesignerIdeaMediaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [DesignerIdeaMediaDialogComponent],
                providers: [
                    DesignerService,
                    DesignerIdeaMediaService
                ]
            })
            .overrideTemplate(DesignerIdeaMediaDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DesignerIdeaMediaDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignerIdeaMediaService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DesignerIdeaMedia(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.designerIdeaMedia = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'designerIdeaMediaListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DesignerIdeaMedia();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.designerIdeaMedia = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'designerIdeaMediaListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
