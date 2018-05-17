import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { SolutionDetailAnalysis } from './solution-detail-analysis.model';
import { SolutionDetailAnalysisService } from './solution-detail-analysis.service';

@Injectable()
export class SolutionDetailAnalysisPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private solutionDetailAnalysisService: SolutionDetailAnalysisService

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
                this.solutionDetailAnalysisService.find(id)
                    .subscribe((solutionDetailAnalysisResponse: HttpResponse<SolutionDetailAnalysis>) => {
                        const solutionDetailAnalysis: SolutionDetailAnalysis = solutionDetailAnalysisResponse.body;
                        this.ngbModalRef = this.solutionDetailAnalysisModalRef(component, solutionDetailAnalysis);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.solutionDetailAnalysisModalRef(component, new SolutionDetailAnalysis());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    solutionDetailAnalysisModalRef(component: Component, solutionDetailAnalysis: SolutionDetailAnalysis): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.solutionDetailAnalysis = solutionDetailAnalysis;
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
