/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenseBrandTestModule } from '../../../test.module';
import { IndustrySecondClassDialogComponent } from '../../../../../../main/webapp/app/entities/industry-second-class/industry-second-class-dialog.component';
import { IndustrySecondClassService } from '../../../../../../main/webapp/app/entities/industry-second-class/industry-second-class.service';
import { IndustrySecondClass } from '../../../../../../main/webapp/app/entities/industry-second-class/industry-second-class.model';
import { IndustryFirstClassService } from '../../../../../../main/webapp/app/entities/industry-first-class';

describe('Component Tests', () => {

    describe('IndustrySecondClass Management Dialog Component', () => {
        let comp: IndustrySecondClassDialogComponent;
        let fixture: ComponentFixture<IndustrySecondClassDialogComponent>;
        let service: IndustrySecondClassService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [IndustrySecondClassDialogComponent],
                providers: [
                    IndustryFirstClassService,
                    IndustrySecondClassService
                ]
            })
            .overrideTemplate(IndustrySecondClassDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IndustrySecondClassDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndustrySecondClassService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new IndustrySecondClass(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.industrySecondClass = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'industrySecondClassListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new IndustrySecondClass();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.industrySecondClass = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'industrySecondClassListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
