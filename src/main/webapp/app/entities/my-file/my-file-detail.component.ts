import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { MyFile } from './my-file.model';
import { MyFileService } from './my-file.service';

@Component({
    selector: 'jhi-my-file-detail',
    templateUrl: './my-file-detail.component.html'
})
export class MyFileDetailComponent implements OnInit, OnDestroy {

    myFile: MyFile;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private myFileService: MyFileService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMyFiles();
    }

    load(id) {
        this.myFileService.find(id)
            .subscribe((myFileResponse: HttpResponse<MyFile>) => {
                this.myFile = myFileResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMyFiles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'myFileListModification',
            (response) => this.load(this.myFile.id)
        );
    }
}
