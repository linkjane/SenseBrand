import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { SolutionDetailAnalysisImg } from './solution-detail-analysis-img.model';
import { SolutionDetailAnalysisImgPopupService } from './solution-detail-analysis-img-popup.service';
import { SolutionDetailAnalysisImgService } from './solution-detail-analysis-img.service';
import { SolutionDetailAnalysis, SolutionDetailAnalysisService } from '../solution-detail-analysis';

@Component({
    selector: 'jhi-solution-detail-analysis-img-dialog',
    templateUrl: './solution-detail-analysis-img-dialog.component.html'
})
export class SolutionDetailAnalysisImgDialogComponent implements OnInit {

    solutionDetailAnalysisImg: SolutionDetailAnalysisImg;
    isSaving: boolean;

    solutiondetailanalyses: SolutionDetailAnalysis[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private solutionDetailAnalysisImgService: SolutionDetailAnalysisImgService,
        private solutionDetailAnalysisService: SolutionDetailAnalysisService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.solutionDetailAnalysisService.query()
            .subscribe((res: HttpResponse<SolutionDetailAnalysis[]>) => { this.solutiondetailanalyses = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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
        this.solutionDetailAnalysisImg[field + 'FileSource'] = event.target;
        this.solutionDetailAnalysisImg[field + 'ContentType'] = event.target.files[0].type;
        this.solutionDetailAnalysisImg[field] = event.target.files[0].name;
        field = field + 'Base64Data';
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.solutionDetailAnalysisImg, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.solutionDetailAnalysisImg.id !== undefined) {
            this.subscribeToSaveResponse(
                this.solutionDetailAnalysisImgService.update(this.solutionDetailAnalysisImg));
        } else {
            this.subscribeToSaveResponse(
                this.solutionDetailAnalysisImgService.create(this.solutionDetailAnalysisImg));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<SolutionDetailAnalysisImg>>) {
        result.subscribe((res: HttpResponse<SolutionDetailAnalysisImg>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: SolutionDetailAnalysisImg) {
        this.eventManager.broadcast({ name: 'solutionDetailAnalysisImgListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSolutionDetailAnalysisById(index: number, item: SolutionDetailAnalysis) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-solution-detail-analysis-img-popup',
    template: ''
})
export class SolutionDetailAnalysisImgPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private solutionDetailAnalysisImgPopupService: SolutionDetailAnalysisImgPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.solutionDetailAnalysisImgPopupService
                    .open(SolutionDetailAnalysisImgDialogComponent as Component, params['id']);
            } else {
                this.solutionDetailAnalysisImgPopupService
                    .open(SolutionDetailAnalysisImgDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
