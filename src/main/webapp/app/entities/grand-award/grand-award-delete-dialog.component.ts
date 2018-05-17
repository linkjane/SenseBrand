import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { GrandAward } from './grand-award.model';
import { GrandAwardPopupService } from './grand-award-popup.service';
import { GrandAwardService } from './grand-award.service';

@Component({
    selector: 'jhi-grand-award-delete-dialog',
    templateUrl: './grand-award-delete-dialog.component.html'
})
export class GrandAwardDeleteDialogComponent {

    grandAward: GrandAward;

    constructor(
        private grandAwardService: GrandAwardService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.grandAwardService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'grandAwardListModification',
                content: 'Deleted an grandAward'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-grand-award-delete-popup',
    template: ''
})
export class GrandAwardDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private grandAwardPopupService: GrandAwardPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.grandAwardPopupService
                .open(GrandAwardDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
