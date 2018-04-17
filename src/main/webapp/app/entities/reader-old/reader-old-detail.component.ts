import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { ReaderOld } from './reader-old.model';
import { ReaderOldService } from './reader-old.service';

@Component({
    selector: 'jhi-reader-old-detail',
    templateUrl: './reader-old-detail.component.html'
})
export class ReaderOldDetailComponent implements OnInit, OnDestroy {

    readerOld: ReaderOld;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private readerOldService: ReaderOldService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInReaderOlds();
    }

    load(id) {
        this.readerOldService.find(id)
            .subscribe((readerOldResponse: HttpResponse<ReaderOld>) => {
                this.readerOld = readerOldResponse.body;
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

    registerChangeInReaderOlds() {
        this.eventSubscriber = this.eventManager.subscribe(
            'readerOldListModification',
            (response) => this.load(this.readerOld.id)
        );
    }
}
