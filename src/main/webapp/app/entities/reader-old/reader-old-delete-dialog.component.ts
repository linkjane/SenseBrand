import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ReaderOld } from './reader-old.model';
import { ReaderOldPopupService } from './reader-old-popup.service';
import { ReaderOldService } from './reader-old.service';

@Component({
    selector: 'jhi-reader-old-delete-dialog',
    templateUrl: './reader-old-delete-dialog.component.html'
})
export class ReaderOldDeleteDialogComponent {

    readerOld: ReaderOld;

    constructor(
        private readerOldService: ReaderOldService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.readerOldService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'readerOldListModification',
                content: 'Deleted an readerOld'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-reader-old-delete-popup',
    template: ''
})
export class ReaderOldDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private readerOldPopupService: ReaderOldPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.readerOldPopupService
                .open(ReaderOldDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
