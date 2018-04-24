import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BrandRank } from './brand-rank.model';
import { BrandRankPopupService } from './brand-rank-popup.service';
import { BrandRankService } from './brand-rank.service';
import { Brand, BrandService } from '../brand';

@Component({
    selector: 'jhi-brand-rank-dialog',
    templateUrl: './brand-rank-dialog.component.html'
})
export class BrandRankDialogComponent implements OnInit {

    brandRank: BrandRank;
    isSaving: boolean;

    brands: Brand[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private brandRankService: BrandRankService,
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
        if (this.brandRank.id !== undefined) {
            this.subscribeToSaveResponse(
                this.brandRankService.update(this.brandRank));
        } else {
            this.subscribeToSaveResponse(
                this.brandRankService.create(this.brandRank));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<BrandRank>>) {
        result.subscribe((res: HttpResponse<BrandRank>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: BrandRank) {
        this.eventManager.broadcast({ name: 'brandRankListModification', content: 'OK'});
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
    selector: 'jhi-brand-rank-popup',
    template: ''
})
export class BrandRankPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private brandRankPopupService: BrandRankPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.brandRankPopupService
                    .open(BrandRankDialogComponent as Component, params['id']);
            } else {
                this.brandRankPopupService
                    .open(BrandRankDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
