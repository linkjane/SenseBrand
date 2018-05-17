import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { SolutionCorrelation } from './solution-correlation.model';
import { SolutionCorrelationPopupService } from './solution-correlation-popup.service';
import { SolutionCorrelationService } from './solution-correlation.service';
import { Solution, SolutionService } from '../solution';

@Component({
    selector: 'jhi-solution-correlation-dialog',
    templateUrl: './solution-correlation-dialog.component.html'
})
export class SolutionCorrelationDialogComponent implements OnInit {

    solutionCorrelation: SolutionCorrelation;
    isSaving: boolean;

    solutions: Solution[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private solutionCorrelationService: SolutionCorrelationService,
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
        this.solutionCorrelation[field + 'FileSource'] = event.target;
        this.solutionCorrelation[field + 'ContentType'] = event.target.files[0].type;
        this.solutionCorrelation[field] = event.target.files[0].name;
        field = field + 'Base64Data';
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.solutionCorrelation, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.solutionCorrelation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.solutionCorrelationService.update(this.solutionCorrelation));
        } else {
            this.subscribeToSaveResponse(
                this.solutionCorrelationService.create(this.solutionCorrelation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<SolutionCorrelation>>) {
        result.subscribe((res: HttpResponse<SolutionCorrelation>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: SolutionCorrelation) {
        this.eventManager.broadcast({ name: 'solutionCorrelationListModification', content: 'OK'});
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
    selector: 'jhi-solution-correlation-popup',
    template: ''
})
export class SolutionCorrelationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private solutionCorrelationPopupService: SolutionCorrelationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.solutionCorrelationPopupService
                    .open(SolutionCorrelationDialogComponent as Component, params['id']);
            } else {
                this.solutionCorrelationPopupService
                    .open(SolutionCorrelationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
