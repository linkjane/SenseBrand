import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { SolutionDetailAnalysisImg } from './solution-detail-analysis-img.model';
import { SolutionDetailAnalysisImgService } from './solution-detail-analysis-img.service';

@Injectable()
export class SolutionDetailAnalysisImgPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private solutionDetailAnalysisImgService: SolutionDetailAnalysisImgService

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
                this.solutionDetailAnalysisImgService.find(id)
                    .subscribe((solutionDetailAnalysisImgResponse: HttpResponse<SolutionDetailAnalysisImg>) => {
                        const solutionDetailAnalysisImg: SolutionDetailAnalysisImg = solutionDetailAnalysisImgResponse.body;
                        this.ngbModalRef = this.solutionDetailAnalysisImgModalRef(component, solutionDetailAnalysisImg);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.solutionDetailAnalysisImgModalRef(component, new SolutionDetailAnalysisImg());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    solutionDetailAnalysisImgModalRef(component: Component, solutionDetailAnalysisImg: SolutionDetailAnalysisImg): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.solutionDetailAnalysisImg = solutionDetailAnalysisImg;
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
