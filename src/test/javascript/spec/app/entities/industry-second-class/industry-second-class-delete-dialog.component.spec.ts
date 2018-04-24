/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenseBrandTestModule } from '../../../test.module';
import { IndustrySecondClassDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/industry-second-class/industry-second-class-delete-dialog.component';
import { IndustrySecondClassService } from '../../../../../../main/webapp/app/entities/industry-second-class/industry-second-class.service';

describe('Component Tests', () => {

    describe('IndustrySecondClass Management Delete Component', () => {
        let comp: IndustrySecondClassDeleteDialogComponent;
        let fixture: ComponentFixture<IndustrySecondClassDeleteDialogComponent>;
        let service: IndustrySecondClassService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [IndustrySecondClassDeleteDialogComponent],
                providers: [
                    IndustrySecondClassService
                ]
            })
            .overrideTemplate(IndustrySecondClassDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IndustrySecondClassDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndustrySecondClassService);
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
