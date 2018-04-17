import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ReaderOld } from './reader-old.model';
import { ReaderOldService } from './reader-old.service';

@Injectable()
export class ReaderOldPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private readerOldService: ReaderOldService

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
                this.readerOldService.find(id)
                    .subscribe((readerOldResponse: HttpResponse<ReaderOld>) => {
                        const readerOld: ReaderOld = readerOldResponse.body;
                        if (readerOld.localTime) {
                            readerOld.localTime = {
                                year: readerOld.localTime.getFullYear(),
                                month: readerOld.localTime.getMonth() + 1,
                                day: readerOld.localTime.getDate()
                            };
                        }
                        this.ngbModalRef = this.readerOldModalRef(component, readerOld);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.readerOldModalRef(component, new ReaderOld());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    readerOldModalRef(component: Component, readerOld: ReaderOld): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.readerOld = readerOld;
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
