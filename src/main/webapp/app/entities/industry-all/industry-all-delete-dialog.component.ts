import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IndustryAll } from './industry-all.model';
import { IndustryAllPopupService } from './industry-all-popup.service';
import { IndustryAllService } from './industry-all.service';

@Component({
    selector: 'jhi-industry-all-delete-dialog',
    templateUrl: './industry-all-delete-dialog.component.html'
})
export class IndustryAllDeleteDialogComponent {

    industryAll: IndustryAll;

    constructor(
        private industryAllService: IndustryAllService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.industryAllService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'industryAllListModification',
                content: 'Deleted an industryAll'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-industry-all-delete-popup',
    template: ''
})
export class IndustryAllDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private industryAllPopupService: IndustryAllPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.industryAllPopupService
                .open(IndustryAllDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
