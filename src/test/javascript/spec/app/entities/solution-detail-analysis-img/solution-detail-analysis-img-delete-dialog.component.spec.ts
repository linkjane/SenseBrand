/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenseBrandTestModule } from '../../../test.module';
import { SolutionDetailAnalysisImgDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/solution-detail-analysis-img/solution-detail-analysis-img-delete-dialog.component';
import { SolutionDetailAnalysisImgService } from '../../../../../../main/webapp/app/entities/solution-detail-analysis-img/solution-detail-analysis-img.service';

describe('Component Tests', () => {

    describe('SolutionDetailAnalysisImg Management Delete Component', () => {
        let comp: SolutionDetailAnalysisImgDeleteDialogComponent;
        let fixture: ComponentFixture<SolutionDetailAnalysisImgDeleteDialogComponent>;
        let service: SolutionDetailAnalysisImgService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [SolutionDetailAnalysisImgDeleteDialogComponent],
                providers: [
                    SolutionDetailAnalysisImgService
                ]
            })
            .overrideTemplate(SolutionDetailAnalysisImgDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SolutionDetailAnalysisImgDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SolutionDetailAnalysisImgService);
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
