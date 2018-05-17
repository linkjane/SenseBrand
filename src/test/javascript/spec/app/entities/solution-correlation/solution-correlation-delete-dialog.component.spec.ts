/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenseBrandTestModule } from '../../../test.module';
import { SolutionCorrelationDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/solution-correlation/solution-correlation-delete-dialog.component';
import { SolutionCorrelationService } from '../../../../../../main/webapp/app/entities/solution-correlation/solution-correlation.service';

describe('Component Tests', () => {

    describe('SolutionCorrelation Management Delete Component', () => {
        let comp: SolutionCorrelationDeleteDialogComponent;
        let fixture: ComponentFixture<SolutionCorrelationDeleteDialogComponent>;
        let service: SolutionCorrelationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [SolutionCorrelationDeleteDialogComponent],
                providers: [
                    SolutionCorrelationService
                ]
            })
            .overrideTemplate(SolutionCorrelationDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SolutionCorrelationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SolutionCorrelationService);
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
