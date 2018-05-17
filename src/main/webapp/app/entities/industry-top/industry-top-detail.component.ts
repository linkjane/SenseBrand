import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { IndustryTop } from './industry-top.model';
import { IndustryTopService } from './industry-top.service';

@Component({
    selector: 'jhi-industry-top-detail',
    templateUrl: './industry-top-detail.component.html'
})
export class IndustryTopDetailComponent implements OnInit, OnDestroy {

    industryTop: IndustryTop;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private industryTopService: IndustryTopService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIndustryTops();
    }

    load(id) {
        this.industryTopService.find(id)
            .subscribe((industryTopResponse: HttpResponse<IndustryTop>) => {
                this.industryTop = industryTopResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIndustryTops() {
        this.eventSubscriber = this.eventManager.subscribe(
            'industryTopListModification',
            (response) => this.load(this.industryTop.id)
        );
    }
}
