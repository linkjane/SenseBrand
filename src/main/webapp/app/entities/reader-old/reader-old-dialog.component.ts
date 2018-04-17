import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { ReaderOld } from './reader-old.model';
import { ReaderOldPopupService } from './reader-old-popup.service';
import { ReaderOldService } from './reader-old.service';

@Component({
    selector: 'jhi-reader-old-dialog',
    templateUrl: './reader-old-dialog.component.html'
})
export class ReaderOldDialogComponent implements OnInit {

    readerOld: ReaderOld;
    isSaving: boolean;
    localTimeDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private readerOldService: ReaderOldService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.readerOld, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.readerOld.id !== undefined) {
            this.subscribeToSaveResponse(
                this.readerOldService.update(this.readerOld));
        } else {
            this.subscribeToSaveResponse(
                this.readerOldService.create(this.readerOld));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ReaderOld>>) {
        result.subscribe((res: HttpResponse<ReaderOld>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ReaderOld) {
        this.eventManager.broadcast({ name: 'readerOldListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-reader-old-popup',
    template: ''
})
export class ReaderOldPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private readerOldPopupService: ReaderOldPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.readerOldPopupService
                    .open(ReaderOldDialogComponent as Component, params['id']);
            } else {
                this.readerOldPopupService
                    .open(ReaderOldDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
