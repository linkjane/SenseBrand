import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DesignerIdeaMedia } from './designer-idea-media.model';
import { DesignerIdeaMediaService } from './designer-idea-media.service';

@Injectable()
export class DesignerIdeaMediaPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private designerIdeaMediaService: DesignerIdeaMediaService

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
                this.designerIdeaMediaService.find(id)
                    .subscribe((designerIdeaMediaResponse: HttpResponse<DesignerIdeaMedia>) => {
                        const designerIdeaMedia: DesignerIdeaMedia = designerIdeaMediaResponse.body;
                        if (designerIdeaMedia.shareTime) {
                            designerIdeaMedia.shareTime = {
                                year: designerIdeaMedia.shareTime.getFullYear(),
                                month: designerIdeaMedia.shareTime.getMonth() + 1,
                                day: designerIdeaMedia.shareTime.getDate()
                            };
                        }
                        this.ngbModalRef = this.designerIdeaMediaModalRef(component, designerIdeaMedia);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.designerIdeaMediaModalRef(component, new DesignerIdeaMedia());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    designerIdeaMediaModalRef(component: Component, designerIdeaMedia: DesignerIdeaMedia): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.designerIdeaMedia = designerIdeaMedia;
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
