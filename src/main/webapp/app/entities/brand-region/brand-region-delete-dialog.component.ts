import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BrandRegion } from './brand-region.model';
import { BrandRegionPopupService } from './brand-region-popup.service';
import { BrandRegionService } from './brand-region.service';

@Component({
    selector: 'jhi-brand-region-delete-dialog',
    templateUrl: './brand-region-delete-dialog.component.html'
})
export class BrandRegionDeleteDialogComponent {

    brandRegion: BrandRegion;

    constructor(
        private brandRegionService: BrandRegionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.brandRegionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'brandRegionListModification',
                content: 'Deleted an brandRegion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-brand-region-delete-popup',
    template: ''
})
export class BrandRegionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private brandRegionPopupService: BrandRegionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.brandRegionPopupService
                .open(BrandRegionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
