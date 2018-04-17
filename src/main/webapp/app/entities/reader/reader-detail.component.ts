import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Reader } from './reader.model';
import { ReaderService } from './reader.service';

@Component({
    selector: 'jhi-reader-detail',
    templateUrl: './reader-detail.component.html'
})
export class ReaderDetailComponent implements OnInit, OnDestroy {

    reader: Reader;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private readerService: ReaderService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInReaders();
    }

    load(id) {
        this.readerService.find(id)
            .subscribe((readerResponse: HttpResponse<Reader>) => {
                this.reader = readerResponse.body;
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

    registerChangeInReaders() {
        this.eventSubscriber = this.eventManager.subscribe(
            'readerListModification',
            (response) => this.load(this.reader.id)
        );
    }
}
