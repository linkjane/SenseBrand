import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IndustrySecondClass } from './industry-second-class.model';
import { IndustrySecondClassPopupService } from './industry-second-class-popup.service';
import { IndustrySecondClassService } from './industry-second-class.service';
import { IndustryFirstClass, IndustryFirstClassService } from '../industry-first-class';

@Component({
    selector: 'jhi-industry-second-class-dialog',
    templateUrl: './industry-second-class-dialog.component.html'
})
export class IndustrySecondClassDialogComponent implements OnInit {

    industrySecondClass: IndustrySecondClass;
    isSaving: boolean;

    industryfirstclasses: IndustryFirstClass[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private industrySecondClassService: IndustrySecondClassService,
        private industryFirstClassService: IndustryFirstClassService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.industryFirstClassService.query()
            .subscribe((res: HttpResponse<IndustryFirstClass[]>) => { this.industryfirstclasses = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.industrySecondClass.id !== undefined) {
            this.subscribeToSaveResponse(
                this.industrySecondClassService.update(this.industrySecondClass));
        } else {
            this.subscribeToSaveResponse(
                this.industrySecondClassService.create(this.industrySecondClass));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IndustrySecondClass>>) {
        result.subscribe((res: HttpResponse<IndustrySecondClass>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: IndustrySecondClass) {
        this.eventManager.broadcast({ name: 'industrySecondClassListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackIndustryFirstClassById(index: number, item: IndustryFirstClass) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-industry-second-class-popup',
    template: ''
})
export class IndustrySecondClassPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private industrySecondClassPopupService: IndustrySecondClassPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.industrySecondClassPopupService
                    .open(IndustrySecondClassDialogComponent as Component, params['id']);
            } else {
                this.industrySecondClassPopupService
                    .open(IndustrySecondClassDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
