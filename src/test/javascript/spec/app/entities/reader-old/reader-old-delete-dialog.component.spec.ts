/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenseBrandTestModule } from '../../../test.module';
import { ReaderOldDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/reader-old/reader-old-delete-dialog.component';
import { ReaderOldService } from '../../../../../../main/webapp/app/entities/reader-old/reader-old.service';

describe('Component Tests', () => {

    describe('ReaderOld Management Delete Component', () => {
        let comp: ReaderOldDeleteDialogComponent;
        let fixture: ComponentFixture<ReaderOldDeleteDialogComponent>;
        let service: ReaderOldService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [ReaderOldDeleteDialogComponent],
                providers: [
                    ReaderOldService
                ]
            })
            .overrideTemplate(ReaderOldDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReaderOldDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReaderOldService);
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
