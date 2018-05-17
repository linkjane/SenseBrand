/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenseBrandTestModule } from '../../../test.module';
import { IndustryFirstClassDialogComponent } from '../../../../../../main/webapp/app/entities/industry-first-class/industry-first-class-dialog.component';
import { IndustryFirstClassService } from '../../../../../../main/webapp/app/entities/industry-first-class/industry-first-class.service';
import { IndustryFirstClass } from '../../../../../../main/webapp/app/entities/industry-first-class/industry-first-class.model';
import { IndustryAllService } from '../../../../../../main/webapp/app/entities/industry-all';

describe('Component Tests', () => {

    describe('IndustryFirstClass Management Dialog Component', () => {
        let comp: IndustryFirstClassDialogComponent;
        let fixture: ComponentFixture<IndustryFirstClassDialogComponent>;
        let service: IndustryFirstClassService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [IndustryFirstClassDialogComponent],
                providers: [
                    IndustryAllService,
                    IndustryFirstClassService
                ]
            })
            .overrideTemplate(IndustryFirstClassDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IndustryFirstClassDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndustryFirstClassService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new IndustryFirstClass(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.industryFirstClass = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'industryFirstClassListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new IndustryFirstClass();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.industryFirstClass = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'industryFirstClassListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
