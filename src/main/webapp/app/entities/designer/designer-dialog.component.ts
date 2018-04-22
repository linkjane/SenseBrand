import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Designer } from './designer.model';
import { DesignerPopupService } from './designer-popup.service';
import { DesignerService } from './designer.service';
import { DesignerIdeaDetails, DesignerIdeaDetailsService } from '../designer-idea-details';
import { DesignerShow, DesignerShowService } from '../designer-show';

@Component({
    selector: 'jhi-designer-dialog',
    templateUrl: './designer-dialog.component.html'
})
export class DesignerDialogComponent implements OnInit {

    designer: Designer;
    isSaving: boolean;

    designerideadetails: DesignerIdeaDetails[];

    designershows: DesignerShow[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private designerService: DesignerService,
        private designerIdeaDetailsService: DesignerIdeaDetailsService,
        private designerShowService: DesignerShowService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.designerIdeaDetailsService.query()
            .subscribe((res: HttpResponse<DesignerIdeaDetails[]>) => { this.designerideadetails = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.designerShowService.query()
            .subscribe((res: HttpResponse<DesignerShow[]>) => { this.designershows = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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
        this.designer[field + 'FileSource'] = event.target;
        this.designer[field + 'ContentType'] = event.target.files[0].type;
        this.designer[field] = event.target.files[0].name;
        field = field + 'Base64Data';
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.designer, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.designer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.designerService.update(this.designer));
        } else {
            this.subscribeToSaveResponse(
                this.designerService.create(this.designer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Designer>>) {
        result.subscribe((res: HttpResponse<Designer>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Designer) {
        this.eventManager.broadcast({ name: 'designerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDesignerIdeaDetailsById(index: number, item: DesignerIdeaDetails) {
        return item.id;
    }

    trackDesignerShowById(index: number, item: DesignerShow) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-designer-popup',
    template: ''
})
export class DesignerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private designerPopupService: DesignerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.designerPopupService
                    .open(DesignerDialogComponent as Component, params['id']);
            } else {
                this.designerPopupService
                    .open(DesignerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
