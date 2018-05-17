import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IndustryFirstClass } from './industry-first-class.model';
import { IndustryFirstClassPopupService } from './industry-first-class-popup.service';
import { IndustryFirstClassService } from './industry-first-class.service';
import { IndustryAll, IndustryAllService } from '../industry-all';

@Component({
    selector: 'jhi-industry-first-class-dialog',
    templateUrl: './industry-first-class-dialog.component.html'
})
export class IndustryFirstClassDialogComponent implements OnInit {

    industryFirstClass: IndustryFirstClass;
    isSaving: boolean;

    industryalls: IndustryAll[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private industryFirstClassService: IndustryFirstClassService,
        private industryAllService: IndustryAllService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.industryAllService.query()
            .subscribe((res: HttpResponse<IndustryAll[]>) => { this.industryalls = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.industryFirstClass.id !== undefined) {
            this.subscribeToSaveResponse(
                this.industryFirstClassService.update(this.industryFirstClass));
        } else {
            this.subscribeToSaveResponse(
                this.industryFirstClassService.create(this.industryFirstClass));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IndustryFirstClass>>) {
        result.subscribe((res: HttpResponse<IndustryFirstClass>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: IndustryFirstClass) {
        this.eventManager.broadcast({ name: 'industryFirstClassListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackIndustryAllById(index: number, item: IndustryAll) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-industry-first-class-popup',
    template: ''
})
export class IndustryFirstClassPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private industryFirstClassPopupService: IndustryFirstClassPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.industryFirstClassPopupService
                    .open(IndustryFirstClassDialogComponent as Component, params['id']);
            } else {
                this.industryFirstClassPopupService
                    .open(IndustryFirstClassDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
