import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { SolutionCorrelation } from './solution-correlation.model';
import { SolutionCorrelationService } from './solution-correlation.service';

@Injectable()
export class SolutionCorrelationPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private solutionCorrelationService: SolutionCorrelationService

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
                this.solutionCorrelationService.find(id)
                    .subscribe((solutionCorrelationResponse: HttpResponse<SolutionCorrelation>) => {
                        const solutionCorrelation: SolutionCorrelation = solutionCorrelationResponse.body;
                        this.ngbModalRef = this.solutionCorrelationModalRef(component, solutionCorrelation);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.solutionCorrelationModalRef(component, new SolutionCorrelation());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    solutionCorrelationModalRef(component: Component, solutionCorrelation: SolutionCorrelation): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.solutionCorrelation = solutionCorrelation;
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
