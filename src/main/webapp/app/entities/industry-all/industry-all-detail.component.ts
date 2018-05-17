import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { IndustryAll } from './industry-all.model';
import { IndustryAllService } from './industry-all.service';

@Component({
    selector: 'jhi-industry-all-detail',
    templateUrl: './industry-all-detail.component.html'
})
export class IndustryAllDetailComponent implements OnInit, OnDestroy {

    industryAll: IndustryAll;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private industryAllService: IndustryAllService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIndustryAlls();
    }

    load(id) {
        this.industryAllService.find(id)
            .subscribe((industryAllResponse: HttpResponse<IndustryAll>) => {
                this.industryAll = industryAllResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIndustryAlls() {
        this.eventSubscriber = this.eventManager.subscribe(
            'industryAllListModification',
            (response) => this.load(this.industryAll.id)
        );
    }
}
