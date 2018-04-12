import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Driver } from './driver.model';
import { DriverPopupService } from './driver-popup.service';
import { DriverService } from './driver.service';
import { Car, CarService } from '../car';

@Component({
    selector: 'jhi-driver-dialog',
    templateUrl: './driver-dialog.component.html'
})
export class DriverDialogComponent implements OnInit {

    driver: Driver;
    isSaving: boolean;

    cars: Car[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private driverService: DriverService,
        private carService: CarService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.carService.query()
            .subscribe((res: HttpResponse<Car[]>) => { this.cars = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.driver.id !== undefined) {
            this.subscribeToSaveResponse(
                this.driverService.update(this.driver));
        } else {
            this.subscribeToSaveResponse(
                this.driverService.create(this.driver));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Driver>>) {
        result.subscribe((res: HttpResponse<Driver>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Driver) {
        this.eventManager.broadcast({ name: 'driverListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCarById(index: number, item: Car) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-driver-popup',
    template: ''
})
export class DriverPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private driverPopupService: DriverPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.driverPopupService
                    .open(DriverDialogComponent as Component, params['id']);
            } else {
                this.driverPopupService
                    .open(DriverDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
