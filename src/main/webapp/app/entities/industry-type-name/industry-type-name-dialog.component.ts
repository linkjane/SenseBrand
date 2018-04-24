import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IndustryTypeName } from './industry-type-name.model';
import { IndustryTypeNamePopupService } from './industry-type-name-popup.service';
import { IndustryTypeNameService } from './industry-type-name.service';
import { IndustryType, IndustryTypeService } from '../industry-type';

@Component({
    selector: 'jhi-industry-type-name-dialog',
    templateUrl: './industry-type-name-dialog.component.html'
})
export class IndustryTypeNameDialogComponent implements OnInit {

    industryTypeName: IndustryTypeName;
    isSaving: boolean;

    industrytypes: IndustryType[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private industryTypeNameService: IndustryTypeNameService,
        private industryTypeService: IndustryTypeService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.industryTypeService.query()
            .subscribe((res: HttpResponse<IndustryType[]>) => { this.industrytypes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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
        this.industryTypeName[field + 'FileSource'] = event.target;
        this.industryTypeName[field + 'ContentType'] = event.target.files[0].type;
        this.industryTypeName[field] = event.target.files[0].name;
        field = field + 'Base64Data';
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.industryTypeName, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.industryTypeName.id !== undefined) {
            this.subscribeToSaveResponse(
                this.industryTypeNameService.update(this.industryTypeName));
        } else {
            this.subscribeToSaveResponse(
                this.industryTypeNameService.create(this.industryTypeName));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IndustryTypeName>>) {
        result.subscribe((res: HttpResponse<IndustryTypeName>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: IndustryTypeName) {
        this.eventManager.broadcast({ name: 'industryTypeNameListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackIndustryTypeById(index: number, item: IndustryType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-industry-type-name-popup',
    template: ''
})
export class IndustryTypeNamePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private industryTypeNamePopupService: IndustryTypeNamePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.industryTypeNamePopupService
                    .open(IndustryTypeNameDialogComponent as Component, params['id']);
            } else {
                this.industryTypeNamePopupService
                    .open(IndustryTypeNameDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
