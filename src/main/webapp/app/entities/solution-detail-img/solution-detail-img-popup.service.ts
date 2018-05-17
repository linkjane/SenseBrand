import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { SolutionDetailImg } from './solution-detail-img.model';
import { SolutionDetailImgService } from './solution-detail-img.service';

@Injectable()
export class SolutionDetailImgPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private solutionDetailImgService: SolutionDetailImgService

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
                this.solutionDetailImgService.find(id)
                    .subscribe((solutionDetailImgResponse: HttpResponse<SolutionDetailImg>) => {
                        const solutionDetailImg: SolutionDetailImg = solutionDetailImgResponse.body;
                        this.ngbModalRef = this.solutionDetailImgModalRef(component, solutionDetailImg);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.solutionDetailImgModalRef(component, new SolutionDetailImg());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    solutionDetailImgModalRef(component: Component, solutionDetailImg: SolutionDetailImg): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.solutionDetailImg = solutionDetailImg;
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
