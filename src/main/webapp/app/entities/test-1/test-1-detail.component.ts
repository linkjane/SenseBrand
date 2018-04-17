import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Test1 } from './test-1.model';
import { Test1Service } from './test-1.service';

@Component({
    selector: 'jhi-test-1-detail',
    templateUrl: './test-1-detail.component.html'
})
export class Test1DetailComponent implements OnInit, OnDestroy {

    test1: Test1;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private test1Service: Test1Service,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTest1S();
    }

    load(id) {
        this.test1Service.find(id)
            .subscribe((test1Response: HttpResponse<Test1>) => {
                this.test1 = test1Response.body;
            });
    }
    byteSize(field) {
        return ;
        // return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        const win = window.open();
        win.document.write(`<iframe src="${field}" frameborder="0" style="border:0; top:0px; left:0px; bottom:0px; right:0px; width:100%; height:100%;" allowfullscreen></iframe>`);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTest1S() {
        this.eventSubscriber = this.eventManager.subscribe(
            'test1ListModification',
            (response) => this.load(this.test1.id)
        );
    }
}
