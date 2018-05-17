import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BrandRegion } from './brand-region.model';
import { BrandRegionPopupService } from './brand-region-popup.service';
import { BrandRegionService } from './brand-region.service';
import { Brand, BrandService } from '../brand';

@Component({
    selector: 'jhi-brand-region-dialog',
    templateUrl: './brand-region-dialog.component.html'
})
export class BrandRegionDialogComponent implements OnInit {

    brandRegion: BrandRegion;
    isSaving: boolean;

    brands: Brand[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private brandRegionService: BrandRegionService,
        private brandService: BrandService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.brandService.query()
            .subscribe((res: HttpResponse<Brand[]>) => { this.brands = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.brandRegion.id !== undefined) {
            this.subscribeToSaveResponse(
                this.brandRegionService.update(this.brandRegion));
        } else {
            this.subscribeToSaveResponse(
                this.brandRegionService.create(this.brandRegion));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<BrandRegion>>) {
        result.subscribe((res: HttpResponse<BrandRegion>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: BrandRegion) {
        this.eventManager.broadcast({ name: 'brandRegionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBrandById(index: number, item: Brand) {
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
    selector: 'jhi-brand-region-popup',
    template: ''
})
export class BrandRegionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private brandRegionPopupService: BrandRegionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.brandRegionPopupService
                    .open(BrandRegionDialogComponent as Component, params['id']);
            } else {
                this.brandRegionPopupService
                    .open(BrandRegionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
