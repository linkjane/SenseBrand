import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DesignerIdeaDetails } from './designer-idea-details.model';
import { DesignerIdeaDetailsService } from './designer-idea-details.service';

@Injectable()
export class DesignerIdeaDetailsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private designerIdeaDetailsService: DesignerIdeaDetailsService

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
                this.designerIdeaDetailsService.find(id)
                    .subscribe((designerIdeaDetailsResponse: HttpResponse<DesignerIdeaDetails>) => {
                        const designerIdeaDetails: DesignerIdeaDetails = designerIdeaDetailsResponse.body;
                        if (designerIdeaDetails.shareTime) {
                            designerIdeaDetails.shareTime = {
                                year: designerIdeaDetails.shareTime.getFullYear(),
                                month: designerIdeaDetails.shareTime.getMonth() + 1,
                                day: designerIdeaDetails.shareTime.getDate()
                            };
                        }
                        this.ngbModalRef = this.designerIdeaDetailsModalRef(component, designerIdeaDetails);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.designerIdeaDetailsModalRef(component, new DesignerIdeaDetails());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    designerIdeaDetailsModalRef(component: Component, designerIdeaDetails: DesignerIdeaDetails): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.designerIdeaDetails = designerIdeaDetails;
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
