/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenseBrandTestModule } from '../../../test.module';
import { DesignerSentimentDialogComponent } from '../../../../../../main/webapp/app/entities/designer-sentiment/designer-sentiment-dialog.component';
import { DesignerSentimentService } from '../../../../../../main/webapp/app/entities/designer-sentiment/designer-sentiment.service';
import { DesignerSentiment } from '../../../../../../main/webapp/app/entities/designer-sentiment/designer-sentiment.model';
import { DesignerService } from '../../../../../../main/webapp/app/entities/designer';

describe('Component Tests', () => {

    describe('DesignerSentiment Management Dialog Component', () => {
        let comp: DesignerSentimentDialogComponent;
        let fixture: ComponentFixture<DesignerSentimentDialogComponent>;
        let service: DesignerSentimentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [DesignerSentimentDialogComponent],
                providers: [
                    DesignerService,
                    DesignerSentimentService
                ]
            })
            .overrideTemplate(DesignerSentimentDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DesignerSentimentDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignerSentimentService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DesignerSentiment(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.designerSentiment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'designerSentimentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DesignerSentiment();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.designerSentiment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'designerSentimentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
