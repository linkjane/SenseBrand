import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { DesignerSentiment } from './designer-sentiment.model';
import { DesignerSentimentPopupService } from './designer-sentiment-popup.service';
import { DesignerSentimentService } from './designer-sentiment.service';
import { Designer, DesignerService } from '../designer';

@Component({
    selector: 'jhi-designer-sentiment-dialog',
    templateUrl: './designer-sentiment-dialog.component.html'
})
export class DesignerSentimentDialogComponent implements OnInit {

    designerSentiment: DesignerSentiment;
    isSaving: boolean;

    designers: Designer[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private designerSentimentService: DesignerSentimentService,
        private designerService: DesignerService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.designerService.query()
            .subscribe((res: HttpResponse<Designer[]>) => { this.designers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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
        this.designerSentiment[field + 'FileSource'] = event.target;
        this.designerSentiment[field + 'ContentType'] = event.target.files[0].type;
        this.designerSentiment[field] = event.target.files[0].name;
        field = field + 'Base64Data';
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.designerSentiment, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.designerSentiment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.designerSentimentService.update(this.designerSentiment));
        } else {
            this.subscribeToSaveResponse(
                this.designerSentimentService.create(this.designerSentiment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DesignerSentiment>>) {
        result.subscribe((res: HttpResponse<DesignerSentiment>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DesignerSentiment) {
        this.eventManager.broadcast({ name: 'designerSentimentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDesignerById(index: number, item: Designer) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-designer-sentiment-popup',
    template: ''
})
export class DesignerSentimentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private designerSentimentPopupService: DesignerSentimentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.designerSentimentPopupService
                    .open(DesignerSentimentDialogComponent as Component, params['id']);
            } else {
                this.designerSentimentPopupService
                    .open(DesignerSentimentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
