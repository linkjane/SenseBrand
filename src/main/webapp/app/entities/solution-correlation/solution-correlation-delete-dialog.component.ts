import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SolutionCorrelation } from './solution-correlation.model';
import { SolutionCorrelationPopupService } from './solution-correlation-popup.service';
import { SolutionCorrelationService } from './solution-correlation.service';

@Component({
    selector: 'jhi-solution-correlation-delete-dialog',
    templateUrl: './solution-correlation-delete-dialog.component.html'
})
export class SolutionCorrelationDeleteDialogComponent {

    solutionCorrelation: SolutionCorrelation;

    constructor(
        private solutionCorrelationService: SolutionCorrelationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.solutionCorrelationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'solutionCorrelationListModification',
                content: 'Deleted an solutionCorrelation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-solution-correlation-delete-popup',
    template: ''
})
export class SolutionCorrelationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private solutionCorrelationPopupService: SolutionCorrelationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.solutionCorrelationPopupService
                .open(SolutionCorrelationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
