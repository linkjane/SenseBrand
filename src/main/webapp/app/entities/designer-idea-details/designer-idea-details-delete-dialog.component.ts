import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DesignerIdeaDetails } from './designer-idea-details.model';
import { DesignerIdeaDetailsPopupService } from './designer-idea-details-popup.service';
import { DesignerIdeaDetailsService } from './designer-idea-details.service';

@Component({
    selector: 'jhi-designer-idea-details-delete-dialog',
    templateUrl: './designer-idea-details-delete-dialog.component.html'
})
export class DesignerIdeaDetailsDeleteDialogComponent {

    designerIdeaDetails: DesignerIdeaDetails;

    constructor(
        private designerIdeaDetailsService: DesignerIdeaDetailsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.designerIdeaDetailsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'designerIdeaDetailsListModification',
                content: 'Deleted an designerIdeaDetails'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-designer-idea-details-delete-popup',
    template: ''
})
export class DesignerIdeaDetailsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private designerIdeaDetailsPopupService: DesignerIdeaDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.designerIdeaDetailsPopupService
                .open(DesignerIdeaDetailsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
