import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { SolutionDetailAnalysis } from './solution-detail-analysis.model';
import { SolutionDetailAnalysisPopupService } from './solution-detail-analysis-popup.service';
import { SolutionDetailAnalysisService } from './solution-detail-analysis.service';
import { Solution, SolutionService } from '../solution';

@Component({
    selector: 'jhi-solution-detail-analysis-dialog',
    templateUrl: './solution-detail-analysis-dialog.component.html'
})
export class SolutionDetailAnalysisDialogComponent implements OnInit {

    solutionDetailAnalysis: SolutionDetailAnalysis;
    isSaving: boolean;

    solutions: Solution[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private solutionDetailAnalysisService: SolutionDetailAnalysisService,
        private solutionService: SolutionService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.solutionService.query()
            .subscribe((res: HttpResponse<Solution[]>) => { this.solutions = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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
        this.solutionDetailAnalysis[field + 'FileSource'] = event.target;
        this.solutionDetailAnalysis[field + 'ContentType'] = event.target.files[0].type;
        this.solutionDetailAnalysis[field] = event.target.files[0].name;
        field = field + 'Base64Data';
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.solutionDetailAnalysis, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.solutionDetailAnalysis.id !== undefined) {
            this.subscribeToSaveResponse(
                this.solutionDetailAnalysisService.update(this.solutionDetailAnalysis));
        } else {
            this.subscribeToSaveResponse(
                this.solutionDetailAnalysisService.create(this.solutionDetailAnalysis));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<SolutionDetailAnalysis>>) {
        result.subscribe((res: HttpResponse<SolutionDetailAnalysis>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: SolutionDetailAnalysis) {
        this.eventManager.broadcast({ name: 'solutionDetailAnalysisListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSolutionById(index: number, item: Solution) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-solution-detail-analysis-popup',
    template: ''
})
export class SolutionDetailAnalysisPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private solutionDetailAnalysisPopupService: SolutionDetailAnalysisPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.solutionDetailAnalysisPopupService
                    .open(SolutionDetailAnalysisDialogComponent as Component, params['id']);
            } else {
                this.solutionDetailAnalysisPopupService
                    .open(SolutionDetailAnalysisDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
