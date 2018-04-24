import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { IndustryTypeName } from './industry-type-name.model';
import { IndustryTypeNameService } from './industry-type-name.service';

@Injectable()
export class IndustryTypeNamePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private industryTypeNameService: IndustryTypeNameService

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
                this.industryTypeNameService.find(id)
                    .subscribe((industryTypeNameResponse: HttpResponse<IndustryTypeName>) => {
                        const industryTypeName: IndustryTypeName = industryTypeNameResponse.body;
                        this.ngbModalRef = this.industryTypeNameModalRef(component, industryTypeName);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.industryTypeNameModalRef(component, new IndustryTypeName());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    industryTypeNameModalRef(component: Component, industryTypeName: IndustryTypeName): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.industryTypeName = industryTypeName;
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
