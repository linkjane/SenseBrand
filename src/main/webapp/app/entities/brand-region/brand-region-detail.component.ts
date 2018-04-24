import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { BrandRegion } from './brand-region.model';
import { BrandRegionService } from './brand-region.service';

@Component({
    selector: 'jhi-brand-region-detail',
    templateUrl: './brand-region-detail.component.html'
})
export class BrandRegionDetailComponent implements OnInit, OnDestroy {

    brandRegion: BrandRegion;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private brandRegionService: BrandRegionService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBrandRegions();
    }

    load(id) {
        this.brandRegionService.find(id)
            .subscribe((brandRegionResponse: HttpResponse<BrandRegion>) => {
                this.brandRegion = brandRegionResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBrandRegions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'brandRegionListModification',
            (response) => this.load(this.brandRegion.id)
        );
    }
}
