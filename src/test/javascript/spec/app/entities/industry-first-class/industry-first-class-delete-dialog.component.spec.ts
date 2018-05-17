/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenseBrandTestModule } from '../../../test.module';
import { IndustryFirstClassDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/industry-first-class/industry-first-class-delete-dialog.component';
import { IndustryFirstClassService } from '../../../../../../main/webapp/app/entities/industry-first-class/industry-first-class.service';

describe('Component Tests', () => {

    describe('IndustryFirstClass Management Delete Component', () => {
        let comp: IndustryFirstClassDeleteDialogComponent;
        let fixture: ComponentFixture<IndustryFirstClassDeleteDialogComponent>;
        let service: IndustryFirstClassService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [IndustryFirstClassDeleteDialogComponent],
                providers: [
                    IndustryFirstClassService
                ]
            })
            .overrideTemplate(IndustryFirstClassDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IndustryFirstClassDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IndustryFirstClassService);
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
