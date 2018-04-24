import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IndustryTop } from './industry-top.model';
import { IndustryTopPopupService } from './industry-top-popup.service';
import { IndustryTopService } from './industry-top.service';
import { Industry, IndustryService } from '../industry';

@Component({
    selector: 'jhi-industry-top-dialog',
    templateUrl: './industry-top-dialog.component.html'
})
export class IndustryTopDialogComponent implements OnInit {

    industryTop: IndustryTop;
    isSaving: boolean;

    industries: Industry[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private industryTopService: IndustryTopService,
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
        if (this.industryTop.id !== undefined) {
            this.subscribeToSaveResponse(
                this.industryTopService.update(this.industryTop));
        } else {
            this.subscribeToSaveResponse(
                this.industryTopService.create(this.industryTop));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IndustryTop>>) {
        result.subscribe((res: HttpResponse<IndustryTop>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: IndustryTop) {
        this.eventManager.broadcast({ name: 'industryTopListModification', content: 'OK'});
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
    selector: 'jhi-industry-top-popup',
    template: ''
})
export class IndustryTopPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private industryTopPopupService: IndustryTopPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.industryTopPopupService
                    .open(IndustryTopDialogComponent as Component, params['id']);
            } else {
                this.industryTopPopupService
                    .open(IndustryTopDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
