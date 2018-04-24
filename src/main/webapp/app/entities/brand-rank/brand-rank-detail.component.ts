import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { BrandRank } from './brand-rank.model';
import { BrandRankService } from './brand-rank.service';

@Component({
    selector: 'jhi-brand-rank-detail',
    templateUrl: './brand-rank-detail.component.html'
})
export class BrandRankDetailComponent implements OnInit, OnDestroy {

    brandRank: BrandRank;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private brandRankService: BrandRankService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBrandRanks();
    }

    load(id) {
        this.brandRankService.find(id)
            .subscribe((brandRankResponse: HttpResponse<BrandRank>) => {
                this.brandRank = brandRankResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBrandRanks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'brandRankListModification',
            (response) => this.load(this.brandRank.id)
        );
    }
}
