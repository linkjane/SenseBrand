import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { DesignerIdeaMedia } from './designer-idea-media.model';
import { DesignerIdeaMediaPopupService } from './designer-idea-media-popup.service';
import { DesignerIdeaMediaService } from './designer-idea-media.service';
import { Designer, DesignerService } from '../designer';

@Component({
    selector: 'jhi-designer-idea-media-dialog',
    templateUrl: './designer-idea-media-dialog.component.html'
})
export class DesignerIdeaMediaDialogComponent implements OnInit {

    designerIdeaMedia: DesignerIdeaMedia;
    isSaving: boolean;

    designers: Designer[];
    shareTimeDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private designerIdeaMediaService: DesignerIdeaMediaService,
        private designerService: DesignerService,
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
        this.designerIdeaMedia[field + 'FileSource'] = event.target;
        this.designerIdeaMedia[field + 'ContentType'] = event.target.files[0].type;
        this.designerIdeaMedia[field] = event.target.files[0].name;
        field = field + 'Base64Data';
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.designerIdeaMedia.id !== undefined) {
            this.subscribeToSaveResponse(
                this.designerIdeaMediaService.update(this.designerIdeaMedia));
        } else {
            this.subscribeToSaveResponse(
                this.designerIdeaMediaService.create(this.designerIdeaMedia));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DesignerIdeaMedia>>) {
        result.subscribe((res: HttpResponse<DesignerIdeaMedia>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DesignerIdeaMedia) {
        this.eventManager.broadcast({ name: 'designerIdeaMediaListModification', content: 'OK'});
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
    selector: 'jhi-designer-idea-media-popup',
    template: ''
})
export class DesignerIdeaMediaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private designerIdeaMediaPopupService: DesignerIdeaMediaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.designerIdeaMediaPopupService
                    .open(DesignerIdeaMediaDialogComponent as Component, params['id']);
            } else {
                this.designerIdeaMediaPopupService
                    .open(DesignerIdeaMediaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
