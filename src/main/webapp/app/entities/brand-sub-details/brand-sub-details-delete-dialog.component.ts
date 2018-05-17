import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BrandSubDetails } from './brand-sub-details.model';
import { BrandSubDetailsPopupService } from './brand-sub-details-popup.service';
import { BrandSubDetailsService } from './brand-sub-details.service';

@Component({
    selector: 'jhi-brand-sub-details-delete-dialog',
    templateUrl: './brand-sub-details-delete-dialog.component.html'
})
export class BrandSubDetailsDeleteDialogComponent {

    brandSubDetails: BrandSubDetails;

    constructor(
        private brandSubDetailsService: BrandSubDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.brandSubDetailsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'brandSubDetailsListModification',
                content: 'Deleted an brandSubDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-brand-sub-details-delete-popup',
    template: ''
})
export class BrandSubDetailsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private brandSubDetailsPopupService: BrandSubDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.brandSubDetailsPopupService
                .open(BrandSubDetailsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
