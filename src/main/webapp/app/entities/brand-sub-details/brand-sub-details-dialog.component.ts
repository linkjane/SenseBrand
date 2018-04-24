import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { BrandSubDetails } from './brand-sub-details.model';
import { BrandSubDetailsPopupService } from './brand-sub-details-popup.service';
import { BrandSubDetailsService } from './brand-sub-details.service';
import { BrandSub, BrandSubService } from '../brand-sub';

@Component({
    selector: 'jhi-brand-sub-details-dialog',
    templateUrl: './brand-sub-details-dialog.component.html'
})
export class BrandSubDetailsDialogComponent implements OnInit {

    brandSubDetails: BrandSubDetails;
    isSaving: boolean;

    brandsubs: BrandSub[];
    createdTimeDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private brandSubDetailsService: BrandSubDetailsService,
        private brandSubService: BrandSubService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.brandSubService.query()
            .subscribe((res: HttpResponse<BrandSub[]>) => { this.brandsubs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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
        this.brandSubDetails[field + 'FileSource'] = event.target;
        this.brandSubDetails[field + 'ContentType'] = event.target.files[0].type;
        this.brandSubDetails[field] = event.target.files[0].name;
        field = field + 'Base64Data';
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.brandSubDetails, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.brandSubDetails.id !== undefined) {
            this.subscribeToSaveResponse(
                this.brandSubDetailsService.update(this.brandSubDetails));
        } else {
            this.subscribeToSaveResponse(
                this.brandSubDetailsService.create(this.brandSubDetails));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<BrandSubDetails>>) {
        result.subscribe((res: HttpResponse<BrandSubDetails>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: BrandSubDetails) {
        this.eventManager.broadcast({ name: 'brandSubDetailsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBrandSubById(index: number, item: BrandSub) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-brand-sub-details-popup',
    template: ''
})
export class BrandSubDetailsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private brandSubDetailsPopupService: BrandSubDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.brandSubDetailsPopupService
                    .open(BrandSubDetailsDialogComponent as Component, params['id']);
            } else {
                this.brandSubDetailsPopupService
                    .open(BrandSubDetailsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
