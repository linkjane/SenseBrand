import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DesignerIdeaDetails } from './designer-idea-details.model';
import { DesignerIdeaDetailsPopupService } from './designer-idea-details-popup.service';
import { DesignerIdeaDetailsService } from './designer-idea-details.service';
import { Designer, DesignerService } from '../designer';

@Component({
    selector: 'jhi-designer-idea-details-dialog',
    templateUrl: './designer-idea-details-dialog.component.html'
})
export class DesignerIdeaDetailsDialogComponent implements OnInit {

    designerIdeaDetails: DesignerIdeaDetails;
    isSaving: boolean;

    designers: Designer[];
    shareTimeDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private designerIdeaDetailsService: DesignerIdeaDetailsService,
        private designerService: DesignerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.designerService
            .query({filter: 'ideadetails-is-null'})
            .subscribe((res: HttpResponse<Designer[]>) => {
                if (!this.designerIdeaDetails.designer || !this.designerIdeaDetails.designer.id) {
                    this.designers = res.body;
                } else {
                    this.designerService
                        .find(this.designerIdeaDetails.designer.id)
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
        if (this.designerIdeaDetails.id !== undefined) {
            this.subscribeToSaveResponse(
                this.designerIdeaDetailsService.update(this.designerIdeaDetails));
        } else {
            this.subscribeToSaveResponse(
                this.designerIdeaDetailsService.create(this.designerIdeaDetails));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DesignerIdeaDetails>>) {
        result.subscribe((res: HttpResponse<DesignerIdeaDetails>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DesignerIdeaDetails) {
        this.eventManager.broadcast({ name: 'designerIdeaDetailsListModification', content: 'OK'});
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
    selector: 'jhi-designer-idea-details-popup',
    template: ''
})
export class DesignerIdeaDetailsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private designerIdeaDetailsPopupService: DesignerIdeaDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.designerIdeaDetailsPopupService
                    .open(DesignerIdeaDetailsDialogComponent as Component, params['id']);
            } else {
                this.designerIdeaDetailsPopupService
                    .open(DesignerIdeaDetailsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
