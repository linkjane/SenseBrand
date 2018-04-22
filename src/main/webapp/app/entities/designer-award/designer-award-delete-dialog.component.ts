import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DesignerAward } from './designer-award.model';
import { DesignerAwardPopupService } from './designer-award-popup.service';
import { DesignerAwardService } from './designer-award.service';

@Component({
    selector: 'jhi-designer-award-delete-dialog',
    templateUrl: './designer-award-delete-dialog.component.html'
})
export class DesignerAwardDeleteDialogComponent {

    designerAward: DesignerAward;

    constructor(
        private designerAwardService: DesignerAwardService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.designerAwardService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'designerAwardListModification',
                content: 'Deleted an designerAward'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-designer-award-delete-popup',
    template: ''
})
export class DesignerAwardDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private designerAwardPopupService: DesignerAwardPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.designerAwardPopupService
                .open(DesignerAwardDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
