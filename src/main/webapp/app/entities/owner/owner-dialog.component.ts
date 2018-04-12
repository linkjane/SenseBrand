import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Owner } from './owner.model';
import { OwnerPopupService } from './owner-popup.service';
import { OwnerService } from './owner.service';

@Component({
    selector: 'jhi-owner-dialog',
    templateUrl: './owner-dialog.component.html'
})
export class OwnerDialogComponent implements OnInit {

    owner: Owner;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private ownerService: OwnerService,
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
        if (this.owner.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ownerService.update(this.owner));
        } else {
            this.subscribeToSaveResponse(
                this.ownerService.create(this.owner));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Owner>>) {
        result.subscribe((res: HttpResponse<Owner>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Owner) {
        this.eventManager.broadcast({ name: 'ownerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-owner-popup',
    template: ''
})
export class OwnerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ownerPopupService: OwnerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ownerPopupService
                    .open(OwnerDialogComponent as Component, params['id']);
            } else {
                this.ownerPopupService
                    .open(OwnerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
