/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenseBrandTestModule } from '../../../test.module';
import { DesignerShowImgDialogComponent } from '../../../../../../main/webapp/app/entities/designer-show-img/designer-show-img-dialog.component';
import { DesignerShowImgService } from '../../../../../../main/webapp/app/entities/designer-show-img/designer-show-img.service';
import { DesignerShowImg } from '../../../../../../main/webapp/app/entities/designer-show-img/designer-show-img.model';
import { DesignerService } from '../../../../../../main/webapp/app/entities/designer';

describe('Component Tests', () => {

    describe('DesignerShowImg Management Dialog Component', () => {
        let comp: DesignerShowImgDialogComponent;
        let fixture: ComponentFixture<DesignerShowImgDialogComponent>;
        let service: DesignerShowImgService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [DesignerShowImgDialogComponent],
                providers: [
                    DesignerService,
                    DesignerShowImgService
                ]
            })
            .overrideTemplate(DesignerShowImgDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DesignerShowImgDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignerShowImgService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DesignerShowImg(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.designerShowImg = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'designerShowImgListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DesignerShowImg();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.designerShowImg = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'designerShowImgListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
