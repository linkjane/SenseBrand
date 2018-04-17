import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Test1 } from './test-1.model';
import { Test1PopupService } from './test-1-popup.service';
import { Test1Service } from './test-1.service';

@Component({
    selector: 'jhi-test-1-dialog',
    templateUrl: './test-1-dialog.component.html'
})
export class Test1DialogComponent implements OnInit {

    test1: Test1;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private test1Service: Test1Service,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    byteSize(field) {
        return ;
        // return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        if (field.includes('http')) {
            const win = window.open();
            win.document.write(`<iframe src="${field}" frameborder="0" style="border:0; top:0px; left:0px; bottom:0px; right:0px; width:100%; height:100%;" allowfullscreen></iframe>`);
            return;
        }
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.test1[field + 'FileSource'] = event.target;
        this.test1[field + 'ContentType'] = event.target.files[0].type;
        this.test1[field] = event.target.files[0].name;
        field = field + 'Base64Data';
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.test1, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.test1.id !== undefined) {
            this.subscribeToSaveResponse(
                this.test1Service.update(this.test1));
        } else {
            this.subscribeToSaveResponse(
                this.test1Service.create(this.test1));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Test1>>) {
        result.subscribe((res: HttpResponse<Test1>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Test1) {
        this.eventManager.broadcast({ name: 'test1ListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-test-1-popup',
    template: ''
})
export class Test1PopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private test1PopupService: Test1PopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.test1PopupService
                    .open(Test1DialogComponent as Component, params['id']);
            } else {
                this.test1PopupService
                    .open(Test1DialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
