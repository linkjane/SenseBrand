import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SolutionDetailAnalysisImg } from './solution-detail-analysis-img.model';
import { SolutionDetailAnalysisImgPopupService } from './solution-detail-analysis-img-popup.service';
import { SolutionDetailAnalysisImgService } from './solution-detail-analysis-img.service';

@Component({
    selector: 'jhi-solution-detail-analysis-img-delete-dialog',
    templateUrl: './solution-detail-analysis-img-delete-dialog.component.html'
})
export class SolutionDetailAnalysisImgDeleteDialogComponent {

    solutionDetailAnalysisImg: SolutionDetailAnalysisImg;

    constructor(
        private solutionDetailAnalysisImgService: SolutionDetailAnalysisImgService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.solutionDetailAnalysisImgService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'solutionDetailAnalysisImgListModification',
                content: 'Deleted an solutionDetailAnalysisImg'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-solution-detail-analysis-img-delete-popup',
    template: ''
})
export class SolutionDetailAnalysisImgDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private solutionDetailAnalysisImgPopupService: SolutionDetailAnalysisImgPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.solutionDetailAnalysisImgPopupService
                .open(SolutionDetailAnalysisImgDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
