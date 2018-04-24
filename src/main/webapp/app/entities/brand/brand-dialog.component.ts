import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Brand } from './brand.model';
import { BrandPopupService } from './brand-popup.service';
import { BrandService } from './brand.service';
import { BrandRank, BrandRankService } from '../brand-rank';
import { BrandRegion, BrandRegionService } from '../brand-region';
import { IndustrySecondClass, IndustrySecondClassService } from '../industry-second-class';

@Component({
    selector: 'jhi-brand-dialog',
    templateUrl: './brand-dialog.component.html'
})
export class BrandDialogComponent implements OnInit {

    brand: Brand;
    isSaving: boolean;

    brandranks: BrandRank[];

    brandregions: BrandRegion[];

    industrysecondclasses: IndustrySecondClass[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private brandService: BrandService,
        private brandRankService: BrandRankService,
        private brandRegionService: BrandRegionService,
        private industrySecondClassService: IndustrySecondClassService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.brandRankService.query()
            .subscribe((res: HttpResponse<BrandRank[]>) => { this.brandranks = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.brandRegionService.query()
            .subscribe((res: HttpResponse<BrandRegion[]>) => { this.brandregions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.industrySecondClassService.query()
            .subscribe((res: HttpResponse<IndustrySecondClass[]>) => { this.industrysecondclasses = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return ;
        // return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        if (field.includes('http')) {
            const win = window.open();
            win.document.write(`<iframe src="${field}" frameborder="0" style="border:0; top:0px; left:0px; bottom:0px; right:0px; width:100%; height:100%;" allowfullscreen></iframe>`);
            return;
        }
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.brand[field + 'FileSource'] = event.target;
        this.brand[field + 'ContentType'] = event.target.files[0].type;
        this.brand[field] = event.target.files[0].name;
        field = field + 'Base64Data';
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.brand, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.brand.id !== undefined) {
            this.subscribeToSaveResponse(
                this.brandService.update(this.brand));
        } else {
            this.subscribeToSaveResponse(
                this.brandService.create(this.brand));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Brand>>) {
        result.subscribe((res: HttpResponse<Brand>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Brand) {
        this.eventManager.broadcast({ name: 'brandListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBrandRankById(index: number, item: BrandRank) {
        return item.id;
    }

    trackBrandRegionById(index: number, item: BrandRegion) {
        return item.id;
    }

    trackIndustrySecondClassById(index: number, item: IndustrySecondClass) {
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
    selector: 'jhi-brand-popup',
    template: ''
})
export class BrandPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private brandPopupService: BrandPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.brandPopupService
                    .open(BrandDialogComponent as Component, params['id']);
            } else {
                this.brandPopupService
                    .open(BrandDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
