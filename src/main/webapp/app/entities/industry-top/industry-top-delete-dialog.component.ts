import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IndustryTop } from './industry-top.model';
import { IndustryTopPopupService } from './industry-top-popup.service';
import { IndustryTopService } from './industry-top.service';

@Component({
    selector: 'jhi-industry-top-delete-dialog',
    templateUrl: './industry-top-delete-dialog.component.html'
})
export class IndustryTopDeleteDialogComponent {

    industryTop: IndustryTop;

    constructor(
        private industryTopService: IndustryTopService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.industryTopService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'industryTopListModification',
                content: 'Deleted an industryTop'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-industry-top-delete-popup',
    template: ''
})
export class IndustryTopDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private industryTopPopupService: IndustryTopPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.industryTopPopupService
                .open(IndustryTopDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
