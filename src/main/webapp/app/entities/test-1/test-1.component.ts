import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Test1 } from './test-1.model';
import { Test1Service } from './test-1.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-test-1',
    templateUrl: './test-1.component.html'
})
export class Test1Component implements OnInit, OnDestroy {
test1S: Test1[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private test1Service: Test1Service,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
            this.activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.test1Service.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<Test1[]>) => this.test1S = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.test1Service.query().subscribe(
            (res: HttpResponse<Test1[]>) => {
                this.test1S = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTest1S();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Test1) {
        return item.id;
    }

    byteSize(field) {
        return ;
        // return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        const win = window.open();
        win.document.write(`<iframe src="${field}" frameborder="0" style="border:0; top:0px; left:0px; bottom:0px; right:0px; width:100%; height:100%;" allowfullscreen></iframe>`);
    }
    registerChangeInTest1S() {
        this.eventSubscriber = this.eventManager.subscribe('test1ListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
