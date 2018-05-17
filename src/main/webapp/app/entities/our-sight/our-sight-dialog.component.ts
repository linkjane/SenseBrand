import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { OurSight } from './our-sight.model';
import { OurSightPopupService } from './our-sight-popup.service';
import { OurSightService } from './our-sight.service';

@Component({
    selector: 'jhi-our-sight-dialog',
    templateUrl: './our-sight-dialog.component.html'
})
export class OurSightDialogComponent implements OnInit {

    ourSight: OurSight;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private ourSightService: OurSightService,
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
        this.ourSight[field + 'FileSource'] = event.target;
        this.ourSight[field + 'ContentType'] = event.target.files[0].type;
        this.ourSight[field] = event.target.files[0].name;
        field = field + 'Base64Data';
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.ourSight, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ourSight.id !== undefined) {
            this.subscribeToSaveResponse(
                this.ourSightService.update(this.ourSight));
        } else {
            this.subscribeToSaveResponse(
                this.ourSightService.create(this.ourSight));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<OurSight>>) {
        result.subscribe((res: HttpResponse<OurSight>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: OurSight) {
        this.eventManager.broadcast({ name: 'ourSightListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-our-sight-popup',
    template: ''
})
export class OurSightPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ourSightPopupService: OurSightPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ourSightPopupService
                    .open(OurSightDialogComponent as Component, params['id']);
            } else {
                this.ourSightPopupService
                    .open(OurSightDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
