import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IndustryTypeName } from './industry-type-name.model';
import { IndustryTypeNamePopupService } from './industry-type-name-popup.service';
import { IndustryTypeNameService } from './industry-type-name.service';

@Component({
    selector: 'jhi-industry-type-name-delete-dialog',
    templateUrl: './industry-type-name-delete-dialog.component.html'
})
export class IndustryTypeNameDeleteDialogComponent {

    industryTypeName: IndustryTypeName;

    constructor(
        private industryTypeNameService: IndustryTypeNameService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.industryTypeNameService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'industryTypeNameListModification',
                content: 'Deleted an industryTypeName'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-industry-type-name-delete-popup',
    template: ''
})
export class IndustryTypeNameDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private industryTypeNamePopupService: IndustryTypeNamePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.industryTypeNamePopupService
                .open(IndustryTypeNameDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
