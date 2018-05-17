/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenseBrandTestModule } from '../../../test.module';
import { SolutionDetailImgDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/solution-detail-img/solution-detail-img-delete-dialog.component';
import { SolutionDetailImgService } from '../../../../../../main/webapp/app/entities/solution-detail-img/solution-detail-img.service';

describe('Component Tests', () => {

    describe('SolutionDetailImg Management Delete Component', () => {
        let comp: SolutionDetailImgDeleteDialogComponent;
        let fixture: ComponentFixture<SolutionDetailImgDeleteDialogComponent>;
        let service: SolutionDetailImgService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [SolutionDetailImgDeleteDialogComponent],
                providers: [
                    SolutionDetailImgService
                ]
            })
            .overrideTemplate(SolutionDetailImgDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SolutionDetailImgDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SolutionDetailImgService);
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
