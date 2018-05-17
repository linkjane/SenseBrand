/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenseBrandTestModule } from '../../../test.module';
import { SolutionCorrelationDialogComponent } from '../../../../../../main/webapp/app/entities/solution-correlation/solution-correlation-dialog.component';
import { SolutionCorrelationService } from '../../../../../../main/webapp/app/entities/solution-correlation/solution-correlation.service';
import { SolutionCorrelation } from '../../../../../../main/webapp/app/entities/solution-correlation/solution-correlation.model';
import { SolutionService } from '../../../../../../main/webapp/app/entities/solution';

describe('Component Tests', () => {

    describe('SolutionCorrelation Management Dialog Component', () => {
        let comp: SolutionCorrelationDialogComponent;
        let fixture: ComponentFixture<SolutionCorrelationDialogComponent>;
        let service: SolutionCorrelationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [SolutionCorrelationDialogComponent],
                providers: [
                    SolutionService,
                    SolutionCorrelationService
                ]
            })
            .overrideTemplate(SolutionCorrelationDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SolutionCorrelationDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SolutionCorrelationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SolutionCorrelation(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.solutionCorrelation = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'solutionCorrelationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SolutionCorrelation();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.solutionCorrelation = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'solutionCorrelationListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
