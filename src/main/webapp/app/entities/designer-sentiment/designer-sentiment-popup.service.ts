import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DesignerSentiment } from './designer-sentiment.model';
import { DesignerSentimentService } from './designer-sentiment.service';

@Injectable()
export class DesignerSentimentPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private designerSentimentService: DesignerSentimentService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.designerSentimentService.find(id)
                    .subscribe((designerSentimentResponse: HttpResponse<DesignerSentiment>) => {
                        const designerSentiment: DesignerSentiment = designerSentimentResponse.body;
                        this.ngbModalRef = this.designerSentimentModalRef(component, designerSentiment);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.designerSentimentModalRef(component, new DesignerSentiment());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    designerSentimentModalRef(component: Component, designerSentiment: DesignerSentiment): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.designerSentiment = designerSentiment;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
