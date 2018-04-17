import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Designer } from './designer.model';
import { DesignerPopupService } from './designer-popup.service';
import { DesignerService } from './designer.service';

@Component({
    selector: 'jhi-designer-delete-dialog',
    templateUrl: './designer-delete-dialog.component.html'
})
export class DesignerDeleteDialogComponent {

    designer: Designer;

    constructor(
        private designerService: DesignerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.designerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'designerListModification',
                content: 'Deleted an designer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-designer-delete-popup',
    template: ''
})
export class DesignerDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private designerPopupService: DesignerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.designerPopupService
                .open(DesignerDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
