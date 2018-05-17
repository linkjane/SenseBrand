import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { GrandAward } from './grand-award.model';
import { GrandAwardService } from './grand-award.service';

@Component({
    selector: 'jhi-grand-award-detail',
    templateUrl: './grand-award-detail.component.html'
})
export class GrandAwardDetailComponent implements OnInit, OnDestroy {

    grandAward: GrandAward;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private grandAwardService: GrandAwardService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInGrandAwards();
    }

    load(id) {
        this.grandAwardService.find(id)
            .subscribe((grandAwardResponse: HttpResponse<GrandAward>) => {
                this.grandAward = grandAwardResponse.body;
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

    registerChangeInGrandAwards() {
        this.eventSubscriber = this.eventManager.subscribe(
            'grandAwardListModification',
            (response) => this.load(this.grandAward.id)
        );
    }
}
