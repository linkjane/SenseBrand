import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BrandSub } from './brand-sub.model';
import { BrandSubPopupService } from './brand-sub-popup.service';
import { BrandSubService } from './brand-sub.service';

@Component({
    selector: 'jhi-brand-sub-dialog',
    templateUrl: './brand-sub-dialog.component.html'
})
export class BrandSubDialogComponent implements OnInit {

    brandSub: BrandSub;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private brandSubService: BrandSubService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.brandSub.id !== undefined) {
            this.subscribeToSaveResponse(
                this.brandSubService.update(this.brandSub));
        } else {
            this.subscribeToSaveResponse(
                this.brandSubService.create(this.brandSub));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<BrandSub>>) {
        result.subscribe((res: HttpResponse<BrandSub>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: BrandSub) {
        this.eventManager.broadcast({ name: 'brandSubListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-brand-sub-popup',
    template: ''
})
export class BrandSubPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private brandSubPopupService: BrandSubPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.brandSubPopupService
                    .open(BrandSubDialogComponent as Component, params['id']);
            } else {
                this.brandSubPopupService
                    .open(BrandSubDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
