import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IndustryType } from './industry-type.model';
import { IndustryTypePopupService } from './industry-type-popup.service';
import { IndustryTypeService } from './industry-type.service';
import { Industry, IndustryService } from '../industry';

@Component({
    selector: 'jhi-industry-type-dialog',
    templateUrl: './industry-type-dialog.component.html'
})
export class IndustryTypeDialogComponent implements OnInit {

    industryType: IndustryType;
    isSaving: boolean;

    industries: Industry[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private industryTypeService: IndustryTypeService,
        private industryService: IndustryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.industryService.query()
            .subscribe((res: HttpResponse<Industry[]>) => { this.industries = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.industryType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.industryTypeService.update(this.industryType));
        } else {
            this.subscribeToSaveResponse(
                this.industryTypeService.create(this.industryType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IndustryType>>) {
        result.subscribe((res: HttpResponse<IndustryType>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: IndustryType) {
        this.eventManager.broadcast({ name: 'industryTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackIndustryById(index: number, item: Industry) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-industry-type-popup',
    template: ''
})
export class IndustryTypePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private industryTypePopupService: IndustryTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.industryTypePopupService
                    .open(IndustryTypeDialogComponent as Component, params['id']);
            } else {
                this.industryTypePopupService
                    .open(IndustryTypeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
