import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { SolutionDetailImg } from './solution-detail-img.model';
import { SolutionDetailImgPopupService } from './solution-detail-img-popup.service';
import { SolutionDetailImgService } from './solution-detail-img.service';
import { Solution, SolutionService } from '../solution';

@Component({
    selector: 'jhi-solution-detail-img-dialog',
    templateUrl: './solution-detail-img-dialog.component.html'
})
export class SolutionDetailImgDialogComponent implements OnInit {

    solutionDetailImg: SolutionDetailImg;
    isSaving: boolean;

    solutions: Solution[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private solutionDetailImgService: SolutionDetailImgService,
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
        this.solutionDetailImg[field + 'FileSource'] = event.target;
        this.solutionDetailImg[field + 'ContentType'] = event.target.files[0].type;
        this.solutionDetailImg[field] = event.target.files[0].name;
        field = field + 'Base64Data';
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.solutionDetailImg, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.solutionDetailImg.id !== undefined) {
            this.subscribeToSaveResponse(
                this.solutionDetailImgService.update(this.solutionDetailImg));
        } else {
            this.subscribeToSaveResponse(
                this.solutionDetailImgService.create(this.solutionDetailImg));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<SolutionDetailImg>>) {
        result.subscribe((res: HttpResponse<SolutionDetailImg>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: SolutionDetailImg) {
        this.eventManager.broadcast({ name: 'solutionDetailImgListModification', content: 'OK'});
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
    selector: 'jhi-solution-detail-img-popup',
    template: ''
})
export class SolutionDetailImgPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private solutionDetailImgPopupService: SolutionDetailImgPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.solutionDetailImgPopupService
                    .open(SolutionDetailImgDialogComponent as Component, params['id']);
            } else {
                this.solutionDetailImgPopupService
                    .open(SolutionDetailImgDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
