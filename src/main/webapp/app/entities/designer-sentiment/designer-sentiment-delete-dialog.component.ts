import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DesignerSentiment } from './designer-sentiment.model';
import { DesignerSentimentPopupService } from './designer-sentiment-popup.service';
import { DesignerSentimentService } from './designer-sentiment.service';

@Component({
    selector: 'jhi-designer-sentiment-delete-dialog',
    templateUrl: './designer-sentiment-delete-dialog.component.html'
})
export class DesignerSentimentDeleteDialogComponent {

    designerSentiment: DesignerSentiment;

    constructor(
        private designerSentimentService: DesignerSentimentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.designerSentimentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'designerSentimentListModification',
                content: 'Deleted an designerSentiment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-designer-sentiment-delete-popup',
    template: ''
})
export class DesignerSentimentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private designerSentimentPopupService: DesignerSentimentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.designerSentimentPopupService
                .open(DesignerSentimentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
