import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BrandSub } from './brand-sub.model';
import { BrandSubPopupService } from './brand-sub-popup.service';
import { BrandSubService } from './brand-sub.service';

@Component({
    selector: 'jhi-brand-sub-delete-dialog',
    templateUrl: './brand-sub-delete-dialog.component.html'
})
export class BrandSubDeleteDialogComponent {

    brandSub: BrandSub;

    constructor(
        private brandSubService: BrandSubService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.brandSubService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'brandSubListModification',
                content: 'Deleted an brandSub'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-brand-sub-delete-popup',
    template: ''
})
export class BrandSubDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private brandSubPopupService: BrandSubPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.brandSubPopupService
                .open(BrandSubDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
