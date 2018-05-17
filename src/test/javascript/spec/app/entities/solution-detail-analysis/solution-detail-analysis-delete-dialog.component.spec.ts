/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenseBrandTestModule } from '../../../test.module';
import { SolutionDetailAnalysisDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/solution-detail-analysis/solution-detail-analysis-delete-dialog.component';
import { SolutionDetailAnalysisService } from '../../../../../../main/webapp/app/entities/solution-detail-analysis/solution-detail-analysis.service';

describe('Component Tests', () => {

    describe('SolutionDetailAnalysis Management Delete Component', () => {
        let comp: SolutionDetailAnalysisDeleteDialogComponent;
        let fixture: ComponentFixture<SolutionDetailAnalysisDeleteDialogComponent>;
        let service: SolutionDetailAnalysisService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [SolutionDetailAnalysisDeleteDialogComponent],
                providers: [
                    SolutionDetailAnalysisService
                ]
            })
            .overrideTemplate(SolutionDetailAnalysisDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SolutionDetailAnalysisDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SolutionDetailAnalysisService);
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
