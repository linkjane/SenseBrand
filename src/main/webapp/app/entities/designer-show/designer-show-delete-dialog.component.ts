import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DesignerShow } from './designer-show.model';
import { DesignerShowPopupService } from './designer-show-popup.service';
import { DesignerShowService } from './designer-show.service';

@Component({
    selector: 'jhi-designer-show-delete-dialog',
    templateUrl: './designer-show-delete-dialog.component.html'
})
export class DesignerShowDeleteDialogComponent {

    designerShow: DesignerShow;

    constructor(
        private designerShowService: DesignerShowService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.designerShowService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'designerShowListModification',
                content: 'Deleted an designerShow'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-designer-show-delete-popup',
    template: ''
})
export class DesignerShowDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private designerShowPopupService: DesignerShowPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.designerShowPopupService
                .open(DesignerShowDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
