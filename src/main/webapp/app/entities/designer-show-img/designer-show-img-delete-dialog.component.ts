import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DesignerShowImg } from './designer-show-img.model';
import { DesignerShowImgPopupService } from './designer-show-img-popup.service';
import { DesignerShowImgService } from './designer-show-img.service';

@Component({
    selector: 'jhi-designer-show-img-delete-dialog',
    templateUrl: './designer-show-img-delete-dialog.component.html'
})
export class DesignerShowImgDeleteDialogComponent {

    designerShowImg: DesignerShowImg;

    constructor(
        private designerShowImgService: DesignerShowImgService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.designerShowImgService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'designerShowImgListModification',
                content: 'Deleted an designerShowImg'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-designer-show-img-delete-popup',
    template: ''
})
export class DesignerShowImgDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private designerShowImgPopupService: DesignerShowImgPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.designerShowImgPopupService
                .open(DesignerShowImgDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
