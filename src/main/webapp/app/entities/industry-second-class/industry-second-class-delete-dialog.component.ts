import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IndustrySecondClass } from './industry-second-class.model';
import { IndustrySecondClassPopupService } from './industry-second-class-popup.service';
import { IndustrySecondClassService } from './industry-second-class.service';

@Component({
    selector: 'jhi-industry-second-class-delete-dialog',
    templateUrl: './industry-second-class-delete-dialog.component.html'
})
export class IndustrySecondClassDeleteDialogComponent {

    industrySecondClass: IndustrySecondClass;

    constructor(
        private industrySecondClassService: IndustrySecondClassService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.industrySecondClassService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'industrySecondClassListModification',
                content: 'Deleted an industrySecondClass'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-industry-second-class-delete-popup',
    template: ''
})
export class IndustrySecondClassDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private industrySecondClassPopupService: IndustrySecondClassPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.industrySecondClassPopupService
                .open(IndustrySecondClassDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
