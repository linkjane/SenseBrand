/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenseBrandTestModule } from '../../../test.module';
import { SolutionDetailAnalysisDialogComponent } from '../../../../../../main/webapp/app/entities/solution-detail-analysis/solution-detail-analysis-dialog.component';
import { SolutionDetailAnalysisService } from '../../../../../../main/webapp/app/entities/solution-detail-analysis/solution-detail-analysis.service';
import { SolutionDetailAnalysis } from '../../../../../../main/webapp/app/entities/solution-detail-analysis/solution-detail-analysis.model';
import { SolutionService } from '../../../../../../main/webapp/app/entities/solution';

describe('Component Tests', () => {

    describe('SolutionDetailAnalysis Management Dialog Component', () => {
        let comp: SolutionDetailAnalysisDialogComponent;
        let fixture: ComponentFixture<SolutionDetailAnalysisDialogComponent>;
        let service: SolutionDetailAnalysisService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [SolutionDetailAnalysisDialogComponent],
                providers: [
                    SolutionService,
                    SolutionDetailAnalysisService
                ]
            })
            .overrideTemplate(SolutionDetailAnalysisDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SolutionDetailAnalysisDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SolutionDetailAnalysisService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SolutionDetailAnalysis(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.solutionDetailAnalysis = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'solutionDetailAnalysisListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SolutionDetailAnalysis();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.solutionDetailAnalysis = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'solutionDetailAnalysisListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
