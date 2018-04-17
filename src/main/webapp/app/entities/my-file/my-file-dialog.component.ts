import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { MyFile } from './my-file.model';
import { MyFilePopupService } from './my-file-popup.service';
import { MyFileService } from './my-file.service';

@Component({
    selector: 'jhi-my-file-dialog',
    templateUrl: './my-file-dialog.component.html'
})
export class MyFileDialogComponent implements OnInit {

    myFile: MyFile;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private myFileService: MyFileService,
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
        this.dataUtils.clearInputImage(this.myFile, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.myFile.id !== undefined) {
            this.subscribeToSaveResponse(
                this.myFileService.update(this.myFile));
        } else {
            this.subscribeToSaveResponse(
                this.myFileService.create(this.myFile));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<MyFile>>) {
        result.subscribe((res: HttpResponse<MyFile>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: MyFile) {
        this.eventManager.broadcast({ name: 'myFileListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-my-file-popup',
    template: ''
})
export class MyFilePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private myFilePopupService: MyFilePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.myFilePopupService
                    .open(MyFileDialogComponent as Component, params['id']);
            } else {
                this.myFilePopupService
                    .open(MyFileDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
