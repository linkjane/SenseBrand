import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { GrandAward } from './grand-award.model';
import { GrandAwardPopupService } from './grand-award-popup.service';
import { GrandAwardService } from './grand-award.service';

@Component({
    selector: 'jhi-grand-award-dialog',
    templateUrl: './grand-award-dialog.component.html'
})
export class GrandAwardDialogComponent implements OnInit {

    grandAward: GrandAward;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private grandAwardService: GrandAwardService,
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
        this.grandAward[field + 'FileSource'] = event.target;
        this.grandAward[field + 'ContentType'] = event.target.files[0].type;
        this.grandAward[field] = event.target.files[0].name;
        field = field + 'Base64Data';
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.grandAward, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.grandAward.id !== undefined) {
            this.subscribeToSaveResponse(
                this.grandAwardService.update(this.grandAward));
        } else {
            this.subscribeToSaveResponse(
                this.grandAwardService.create(this.grandAward));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<GrandAward>>) {
        result.subscribe((res: HttpResponse<GrandAward>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: GrandAward) {
        this.eventManager.broadcast({ name: 'grandAwardListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-grand-award-popup',
    template: ''
})
export class GrandAwardPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private grandAwardPopupService: GrandAwardPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.grandAwardPopupService
                    .open(GrandAwardDialogComponent as Component, params['id']);
            } else {
                this.grandAwardPopupService
                    .open(GrandAwardDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
