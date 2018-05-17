import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { DesignerAward } from './designer-award.model';
import { DesignerAwardPopupService } from './designer-award-popup.service';
import { DesignerAwardService } from './designer-award.service';
import { Designer, DesignerService } from '../designer';

@Component({
    selector: 'jhi-designer-award-dialog',
    templateUrl: './designer-award-dialog.component.html'
})
export class DesignerAwardDialogComponent implements OnInit {

    designerAward: DesignerAward;
    isSaving: boolean;

    designers: Designer[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private designerAwardService: DesignerAwardService,
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
        this.designerAward[field + 'FileSource'] = event.target;
        this.designerAward[field + 'ContentType'] = event.target.files[0].type;
        this.designerAward[field] = event.target.files[0].name;
        field = field + 'Base64Data';
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.designerAward, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.designerAward.id !== undefined) {
            this.subscribeToSaveResponse(
                this.designerAwardService.update(this.designerAward));
        } else {
            this.subscribeToSaveResponse(
                this.designerAwardService.create(this.designerAward));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DesignerAward>>) {
        result.subscribe((res: HttpResponse<DesignerAward>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DesignerAward) {
        this.eventManager.broadcast({ name: 'designerAwardListModification', content: 'OK'});
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
    selector: 'jhi-designer-award-popup',
    template: ''
})
export class DesignerAwardPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private designerAwardPopupService: DesignerAwardPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.designerAwardPopupService
                    .open(DesignerAwardDialogComponent as Component, params['id']);
            } else {
                this.designerAwardPopupService
                    .open(DesignerAwardDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
