import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { DesignerShowImg } from './designer-show-img.model';
import { DesignerShowImgPopupService } from './designer-show-img-popup.service';
import { DesignerShowImgService } from './designer-show-img.service';
import { Designer, DesignerService } from '../designer';

@Component({
    selector: 'jhi-designer-show-img-dialog',
    templateUrl: './designer-show-img-dialog.component.html'
})
export class DesignerShowImgDialogComponent implements OnInit {

    designerShowImg: DesignerShowImg;
    isSaving: boolean;

    designers: Designer[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private designerShowImgService: DesignerShowImgService,
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
        this.designerShowImg[field + 'FileSource'] = event.target;
        this.designerShowImg[field + 'ContentType'] = event.target.files[0].type;
        this.designerShowImg[field] = event.target.files[0].name;
        field = field + 'Base64Data';
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.designerShowImg, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.designerShowImg.id !== undefined) {
            this.subscribeToSaveResponse(
                this.designerShowImgService.update(this.designerShowImg));
        } else {
            this.subscribeToSaveResponse(
                this.designerShowImgService.create(this.designerShowImg));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DesignerShowImg>>) {
        result.subscribe((res: HttpResponse<DesignerShowImg>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DesignerShowImg) {
        this.eventManager.broadcast({ name: 'designerShowImgListModification', content: 'OK'});
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
    selector: 'jhi-designer-show-img-popup',
    template: ''
})
export class DesignerShowImgPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private designerShowImgPopupService: DesignerShowImgPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.designerShowImgPopupService
                    .open(DesignerShowImgDialogComponent as Component, params['id']);
            } else {
                this.designerShowImgPopupService
                    .open(DesignerShowImgDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
