/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenseBrandTestModule } from '../../../test.module';
import { DesignerSentimentDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/designer-sentiment/designer-sentiment-delete-dialog.component';
import { DesignerSentimentService } from '../../../../../../main/webapp/app/entities/designer-sentiment/designer-sentiment.service';

describe('Component Tests', () => {

    describe('DesignerSentiment Management Delete Component', () => {
        let comp: DesignerSentimentDeleteDialogComponent;
        let fixture: ComponentFixture<DesignerSentimentDeleteDialogComponent>;
        let service: DesignerSentimentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [DesignerSentimentDeleteDialogComponent],
                providers: [
                    DesignerSentimentService
                ]
            })
            .overrideTemplate(DesignerSentimentDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DesignerSentimentDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignerSentimentService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
