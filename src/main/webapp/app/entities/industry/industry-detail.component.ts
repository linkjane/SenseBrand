import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Industry } from './industry.model';
import { IndustryService } from './industry.service';

@Component({
    selector: 'jhi-industry-detail',
    templateUrl: './industry-detail.component.html'
})
export class IndustryDetailComponent implements OnInit, OnDestroy {

    industry: Industry;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private industryService: IndustryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIndustries();
    }

    load(id) {
        this.industryService.find(id)
            .subscribe((industryResponse: HttpResponse<Industry>) => {
                this.industry = industryResponse.body;
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

    registerChangeInIndustries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'industryListModification',
            (response) => this.load(this.industry.id)
        );
    }
}
