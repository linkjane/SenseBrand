import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Designer } from './designer.model';
import { DesignerPopupService } from './designer-popup.service';
import { DesignerService } from './designer.service';

@Component({
    selector: 'jhi-designer-dialog',
    templateUrl: './designer-dialog.component.html'
})
export class DesignerDialogComponent implements OnInit {

    designer: Designer;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private designerService: DesignerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
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
