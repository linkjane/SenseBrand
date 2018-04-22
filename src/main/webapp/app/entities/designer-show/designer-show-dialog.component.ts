import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DesignerShow } from './designer-show.model';
import { DesignerShowPopupService } from './designer-show-popup.service';
import { DesignerShowService } from './designer-show.service';
import { Designer, DesignerService } from '../designer';

@Component({
    selector: 'jhi-designer-show-dialog',
    templateUrl: './designer-show-dialog.component.html'
})
export class DesignerShowDialogComponent implements OnInit {

    designerShow: DesignerShow;
    isSaving: boolean;

    designers: Designer[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private designerShowService: DesignerShowService,
        private designerService: DesignerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.designerService
            .query({filter: 'designershow-is-null'})
            .subscribe((res: HttpResponse<Designer[]>) => {
                if (!this.designerShow.designer || !this.designerShow.designer.id) {
                    this.designers = res.body;
                } else {
                    this.designerService
                        .find(this.designerShow.designer.id)
                        .subscribe((subRes: HttpResponse<Designer>) => {
                            this.designers = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.designerShow.id !== undefined) {
            this.subscribeToSaveResponse(
                this.designerShowService.update(this.designerShow));
        } else {
            this.subscribeToSaveResponse(
                this.designerShowService.create(this.designerShow));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DesignerShow>>) {
        result.subscribe((res: HttpResponse<DesignerShow>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DesignerShow) {
        this.eventManager.broadcast({ name: 'designerShowListModification', content: 'OK'});
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
    selector: 'jhi-designer-show-popup',
    template: ''
})
export class DesignerShowPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private designerShowPopupService: DesignerShowPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.designerShowPopupService
                    .open(DesignerShowDialogComponent as Component, params['id']);
            } else {
                this.designerShowPopupService
                    .open(DesignerShowDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
