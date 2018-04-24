import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { IndustryFirstClass } from './industry-first-class.model';
import { IndustryFirstClassService } from './industry-first-class.service';

@Injectable()
export class IndustryFirstClassPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private industryFirstClassService: IndustryFirstClassService

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
                this.industryFirstClassService.find(id)
                    .subscribe((industryFirstClassResponse: HttpResponse<IndustryFirstClass>) => {
                        const industryFirstClass: IndustryFirstClass = industryFirstClassResponse.body;
                        this.ngbModalRef = this.industryFirstClassModalRef(component, industryFirstClass);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.industryFirstClassModalRef(component, new IndustryFirstClass());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    industryFirstClassModalRef(component: Component, industryFirstClass: IndustryFirstClass): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.industryFirstClass = industryFirstClass;
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
