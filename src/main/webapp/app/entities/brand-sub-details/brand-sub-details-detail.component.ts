import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { BrandSubDetails } from './brand-sub-details.model';
import { BrandSubDetailsService } from './brand-sub-details.service';

@Component({
    selector: 'jhi-brand-sub-details-detail',
    templateUrl: './brand-sub-details-detail.component.html'
})
export class BrandSubDetailsDetailComponent implements OnInit, OnDestroy {

    brandSubDetails: BrandSubDetails;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private brandSubDetailsService: BrandSubDetailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBrandSubDetails();
    }

    load(id) {
        this.brandSubDetailsService.find(id)
            .subscribe((brandSubDetailsResponse: HttpResponse<BrandSubDetails>) => {
                this.brandSubDetails = brandSubDetailsResponse.body;
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

    registerChangeInBrandSubDetails() {
        this.eventSubscriber = this.eventManager.subscribe(
            'brandSubDetailsListModification',
            (response) => this.load(this.brandSubDetails.id)
        );
    }
}
