import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IndustryAll } from './industry-all.model';
import { IndustryAllPopupService } from './industry-all-popup.service';
import { IndustryAllService } from './industry-all.service';

@Component({
    selector: 'jhi-industry-all-dialog',
    templateUrl: './industry-all-dialog.component.html'
})
export class IndustryAllDialogComponent implements OnInit {

    industryAll: IndustryAll;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private industryAllService: IndustryAllService,
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
        if (this.industryAll.id !== undefined) {
            this.subscribeToSaveResponse(
                this.industryAllService.update(this.industryAll));
        } else {
            this.subscribeToSaveResponse(
                this.industryAllService.create(this.industryAll));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IndustryAll>>) {
        result.subscribe((res: HttpResponse<IndustryAll>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: IndustryAll) {
        this.eventManager.broadcast({ name: 'industryAllListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-industry-all-popup',
    template: ''
})
export class IndustryAllPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private industryAllPopupService: IndustryAllPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.industryAllPopupService
                    .open(IndustryAllDialogComponent as Component, params['id']);
            } else {
                this.industryAllPopupService
                    .open(IndustryAllDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
