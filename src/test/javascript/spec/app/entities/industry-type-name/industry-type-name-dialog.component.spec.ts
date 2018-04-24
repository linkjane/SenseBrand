/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenseBrandTestModule } from '../../../test.module';
import { IndustryTypeNameDialogComponent } from '../../../../../../main/webapp/app/entities/industry-type-name/industry-type-name-dialog.component';
import { IndustryTypeNameService } from '../../../../../../main/webapp/app/entities/industry-type-name/industry-type-name.service';
import { IndustryTypeName } from '../../../../../../main/webapp/app/entities/industry-type-name/industry-type-name.model';
import { IndustryTypeService } from '../../../../../../main/webapp/app/entities/industry-type';

describe('Component Tests', () => {

    describe('IndustryTypeName Management Dialog Component', () => {
        let comp: IndustryTypeNameDialogComponent;
        let fixture: ComponentFixture<IndustryTypeNameDialogComponent>;
        let service: IndustryTypeNameService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [IndustryTypeNameDialogComponent],
                providers: [
                    IndustryTypeService,
                    IndustryTypeNameService
                ]
            })
            .overrideTemplate(IndustryTypeNameDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IndustryTypeNameDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndustryTypeNameService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new IndustryTypeName(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.industryTypeName = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'industryTypeNameListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new IndustryTypeName();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.industryTypeName = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'industryTypeNameListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
