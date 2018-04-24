import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BrandRank } from './brand-rank.model';
import { BrandRankPopupService } from './brand-rank-popup.service';
import { BrandRankService } from './brand-rank.service';

@Component({
    selector: 'jhi-brand-rank-delete-dialog',
    templateUrl: './brand-rank-delete-dialog.component.html'
})
export class BrandRankDeleteDialogComponent {

    brandRank: BrandRank;

    constructor(
        private brandRankService: BrandRankService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.brandRankService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'brandRankListModification',
                content: 'Deleted an brandRank'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-brand-rank-delete-popup',
    template: ''
})
export class BrandRankDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private brandRankPopupService: BrandRankPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.brandRankPopupService
                .open(BrandRankDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
