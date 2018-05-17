import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Solution } from './solution.model';
import { SolutionPopupService } from './solution-popup.service';
import { SolutionService } from './solution.service';

@Component({
    selector: 'jhi-solution-dialog',
    templateUrl: './solution-dialog.component.html'
})
export class SolutionDialogComponent implements OnInit {

    solution: Solution;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private solutionService: SolutionService,
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
        this.solution[field + 'FileSource'] = event.target;
        this.solution[field + 'ContentType'] = event.target.files[0].type;
        this.solution[field] = event.target.files[0].name;
        field = field + 'Base64Data';
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.solution, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.solution.id !== undefined) {
            this.subscribeToSaveResponse(
                this.solutionService.update(this.solution));
        } else {
            this.subscribeToSaveResponse(
                this.solutionService.create(this.solution));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Solution>>) {
        result.subscribe((res: HttpResponse<Solution>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Solution) {
        this.eventManager.broadcast({ name: 'solutionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-solution-popup',
    template: ''
})
export class SolutionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private solutionPopupService: SolutionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.solutionPopupService
                    .open(SolutionDialogComponent as Component, params['id']);
            } else {
                this.solutionPopupService
                    .open(SolutionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
