import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Reader } from './reader.model';
import { ReaderPopupService } from './reader-popup.service';
import { ReaderService } from './reader.service';

@Component({
    selector: 'jhi-reader-dialog',
    templateUrl: './reader-dialog.component.html'
})
export class ReaderDialogComponent implements OnInit {

    reader: Reader;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private readerService: ReaderService,
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

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.reader.id !== undefined) {
            this.subscribeToSaveResponse(
                this.readerService.update(this.reader));
        } else {
            this.subscribeToSaveResponse(
                this.readerService.create(this.reader));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Reader>>) {
        result.subscribe((res: HttpResponse<Reader>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Reader) {
        this.eventManager.broadcast({ name: 'readerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-reader-popup',
    template: ''
})
export class ReaderPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private readerPopupService: ReaderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.readerPopupService
                    .open(ReaderDialogComponent as Component, params['id']);
            } else {
                this.readerPopupService
                    .open(ReaderDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
