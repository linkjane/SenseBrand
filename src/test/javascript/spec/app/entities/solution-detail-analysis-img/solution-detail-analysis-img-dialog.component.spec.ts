/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenseBrandTestModule } from '../../../test.module';
import { SolutionDetailAnalysisImgDialogComponent } from '../../../../../../main/webapp/app/entities/solution-detail-analysis-img/solution-detail-analysis-img-dialog.component';
import { SolutionDetailAnalysisImgService } from '../../../../../../main/webapp/app/entities/solution-detail-analysis-img/solution-detail-analysis-img.service';
import { SolutionDetailAnalysisImg } from '../../../../../../main/webapp/app/entities/solution-detail-analysis-img/solution-detail-analysis-img.model';
import { SolutionDetailAnalysisService } from '../../../../../../main/webapp/app/entities/solution-detail-analysis';

describe('Component Tests', () => {

    describe('SolutionDetailAnalysisImg Management Dialog Component', () => {
        let comp: SolutionDetailAnalysisImgDialogComponent;
        let fixture: ComponentFixture<SolutionDetailAnalysisImgDialogComponent>;
        let service: SolutionDetailAnalysisImgService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [SolutionDetailAnalysisImgDialogComponent],
                providers: [
                    SolutionDetailAnalysisService,
                    SolutionDetailAnalysisImgService
                ]
            })
            .overrideTemplate(SolutionDetailAnalysisImgDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SolutionDetailAnalysisImgDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SolutionDetailAnalysisImgService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SolutionDetailAnalysisImg(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.solutionDetailAnalysisImg = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'solutionDetailAnalysisImgListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SolutionDetailAnalysisImg();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.solutionDetailAnalysisImg = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'solutionDetailAnalysisImgListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
