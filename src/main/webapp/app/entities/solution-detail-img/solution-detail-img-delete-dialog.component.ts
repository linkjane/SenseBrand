import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SolutionDetailImg } from './solution-detail-img.model';
import { SolutionDetailImgPopupService } from './solution-detail-img-popup.service';
import { SolutionDetailImgService } from './solution-detail-img.service';

@Component({
    selector: 'jhi-solution-detail-img-delete-dialog',
    templateUrl: './solution-detail-img-delete-dialog.component.html'
})
export class SolutionDetailImgDeleteDialogComponent {

    solutionDetailImg: SolutionDetailImg;

    constructor(
        private solutionDetailImgService: SolutionDetailImgService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.solutionDetailImgService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'solutionDetailImgListModification',
                content: 'Deleted an solutionDetailImg'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-solution-detail-img-delete-popup',
    template: ''
})
export class SolutionDetailImgDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private solutionDetailImgPopupService: SolutionDetailImgPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.solutionDetailImgPopupService
                .open(SolutionDetailImgDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
