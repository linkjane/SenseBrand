import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OurSight } from './our-sight.model';
import { OurSightPopupService } from './our-sight-popup.service';
import { OurSightService } from './our-sight.service';

@Component({
    selector: 'jhi-our-sight-delete-dialog',
    templateUrl: './our-sight-delete-dialog.component.html'
})
export class OurSightDeleteDialogComponent {

    ourSight: OurSight;

    constructor(
        private ourSightService: OurSightService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ourSightService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ourSightListModification',
                content: 'Deleted an ourSight'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-our-sight-delete-popup',
    template: ''
})
export class OurSightDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ourSightPopupService: OurSightPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.ourSightPopupService
                .open(OurSightDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
