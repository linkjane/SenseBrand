import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IndustryType } from './industry-type.model';
import { IndustryTypePopupService } from './industry-type-popup.service';
import { IndustryTypeService } from './industry-type.service';

@Component({
    selector: 'jhi-industry-type-delete-dialog',
    templateUrl: './industry-type-delete-dialog.component.html'
})
export class IndustryTypeDeleteDialogComponent {

    industryType: IndustryType;

    constructor(
        private industryTypeService: IndustryTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.industryTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'industryTypeListModification',
                content: 'Deleted an industryType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-industry-type-delete-popup',
    template: ''
})
export class IndustryTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private industryTypePopupService: IndustryTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.industryTypePopupService
                .open(IndustryTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
