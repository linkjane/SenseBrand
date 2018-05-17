import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { BrandSubDetails } from './brand-sub-details.model';
import { BrandSubDetailsService } from './brand-sub-details.service';

@Injectable()
export class BrandSubDetailsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private brandSubDetailsService: BrandSubDetailsService

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
                this.brandSubDetailsService.find(id)
                    .subscribe((brandSubDetailsResponse: HttpResponse<BrandSubDetails>) => {
                        const brandSubDetails: BrandSubDetails = brandSubDetailsResponse.body;
                        if (brandSubDetails.createdTime) {
                            brandSubDetails.createdTime = {
                                year: brandSubDetails.createdTime.getFullYear(),
                                month: brandSubDetails.createdTime.getMonth() + 1,
                                day: brandSubDetails.createdTime.getDate()
                            };
                        }
                        this.ngbModalRef = this.brandSubDetailsModalRef(component, brandSubDetails);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.brandSubDetailsModalRef(component, new BrandSubDetails());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    brandSubDetailsModalRef(component: Component, brandSubDetails: BrandSubDetails): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.brandSubDetails = brandSubDetails;
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
