/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenseBrandTestModule } from '../../../test.module';
import { BrandSubDetailsDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/brand-sub-details/brand-sub-details-delete-dialog.component';
import { BrandSubDetailsService } from '../../../../../../main/webapp/app/entities/brand-sub-details/brand-sub-details.service';

describe('Component Tests', () => {

    describe('BrandSubDetails Management Delete Component', () => {
        let comp: BrandSubDetailsDeleteDialogComponent;
        let fixture: ComponentFixture<BrandSubDetailsDeleteDialogComponent>;
        let service: BrandSubDetailsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [BrandSubDetailsDeleteDialogComponent],
                providers: [
                    BrandSubDetailsService
                ]
            })
            .overrideTemplate(BrandSubDetailsDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BrandSubDetailsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BrandSubDetailsService);
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
