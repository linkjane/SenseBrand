import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MyFile } from './my-file.model';
import { MyFilePopupService } from './my-file-popup.service';
import { MyFileService } from './my-file.service';

@Component({
    selector: 'jhi-my-file-delete-dialog',
    templateUrl: './my-file-delete-dialog.component.html'
})
export class MyFileDeleteDialogComponent {

    myFile: MyFile;

    constructor(
        private myFileService: MyFileService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.myFileService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'myFileListModification',
                content: 'Deleted an myFile'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-my-file-delete-popup',
    template: ''
})
export class MyFileDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private myFilePopupService: MyFilePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.myFilePopupService
                .open(MyFileDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
