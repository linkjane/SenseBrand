import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DesignerIdeaMedia } from './designer-idea-media.model';
import { DesignerIdeaMediaPopupService } from './designer-idea-media-popup.service';
import { DesignerIdeaMediaService } from './designer-idea-media.service';

@Component({
    selector: 'jhi-designer-idea-media-delete-dialog',
    templateUrl: './designer-idea-media-delete-dialog.component.html'
})
export class DesignerIdeaMediaDeleteDialogComponent {

    designerIdeaMedia: DesignerIdeaMedia;

    constructor(
        private designerIdeaMediaService: DesignerIdeaMediaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.designerIdeaMediaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'designerIdeaMediaListModification',
                content: 'Deleted an designerIdeaMedia'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-designer-idea-media-delete-popup',
    template: ''
})
export class DesignerIdeaMediaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private designerIdeaMediaPopupService: DesignerIdeaMediaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.designerIdeaMediaPopupService
                .open(DesignerIdeaMediaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
