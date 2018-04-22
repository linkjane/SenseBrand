import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DesignerShowImg } from './designer-show-img.model';
import { DesignerShowImgService } from './designer-show-img.service';

@Injectable()
export class DesignerShowImgPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private designerShowImgService: DesignerShowImgService

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
                this.designerShowImgService.find(id)
                    .subscribe((designerShowImgResponse: HttpResponse<DesignerShowImg>) => {
                        const designerShowImg: DesignerShowImg = designerShowImgResponse.body;
                        this.ngbModalRef = this.designerShowImgModalRef(component, designerShowImg);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.designerShowImgModalRef(component, new DesignerShowImg());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    designerShowImgModalRef(component: Component, designerShowImg: DesignerShowImg): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.designerShowImg = designerShowImg;
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
