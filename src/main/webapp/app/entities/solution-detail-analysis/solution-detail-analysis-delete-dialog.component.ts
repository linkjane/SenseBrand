import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SolutionDetailAnalysis } from './solution-detail-analysis.model';
import { SolutionDetailAnalysisPopupService } from './solution-detail-analysis-popup.service';
import { SolutionDetailAnalysisService } from './solution-detail-analysis.service';

@Component({
    selector: 'jhi-solution-detail-analysis-delete-dialog',
    templateUrl: './solution-detail-analysis-delete-dialog.component.html'
})
export class SolutionDetailAnalysisDeleteDialogComponent {

    solutionDetailAnalysis: SolutionDetailAnalysis;

    constructor(
        private solutionDetailAnalysisService: SolutionDetailAnalysisService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.solutionDetailAnalysisService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'solutionDetailAnalysisListModification',
                content: 'Deleted an solutionDetailAnalysis'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-solution-detail-analysis-delete-popup',
    template: ''
})
export class SolutionDetailAnalysisDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private solutionDetailAnalysisPopupService: SolutionDetailAnalysisPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.solutionDetailAnalysisPopupService
                .open(SolutionDetailAnalysisDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
