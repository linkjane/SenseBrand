import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IndustryFirstClass } from './industry-first-class.model';
import { IndustryFirstClassPopupService } from './industry-first-class-popup.service';
import { IndustryFirstClassService } from './industry-first-class.service';

@Component({
    selector: 'jhi-industry-first-class-delete-dialog',
    templateUrl: './industry-first-class-delete-dialog.component.html'
})
export class IndustryFirstClassDeleteDialogComponent {

    industryFirstClass: IndustryFirstClass;

    constructor(
        private industryFirstClassService: IndustryFirstClassService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.industryFirstClassService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'industryFirstClassListModification',
                content: 'Deleted an industryFirstClass'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-industry-first-class-delete-popup',
    template: ''
})
export class IndustryFirstClassDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private industryFirstClassPopupService: IndustryFirstClassPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.industryFirstClassPopupService
                .open(IndustryFirstClassDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
