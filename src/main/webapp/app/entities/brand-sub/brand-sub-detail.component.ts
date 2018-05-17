import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { BrandSub } from './brand-sub.model';
import { BrandSubService } from './brand-sub.service';

@Component({
    selector: 'jhi-brand-sub-detail',
    templateUrl: './brand-sub-detail.component.html'
})
export class BrandSubDetailComponent implements OnInit, OnDestroy {

    brandSub: BrandSub;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private brandSubService: BrandSubService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBrandSubs();
    }

    load(id) {
        this.brandSubService.find(id)
            .subscribe((brandSubResponse: HttpResponse<BrandSub>) => {
                this.brandSub = brandSubResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBrandSubs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'brandSubListModification',
            (response) => this.load(this.brandSub.id)
        );
    }
}
