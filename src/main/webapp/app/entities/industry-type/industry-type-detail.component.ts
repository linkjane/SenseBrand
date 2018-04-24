import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { IndustryType } from './industry-type.model';
import { IndustryTypeService } from './industry-type.service';

@Component({
    selector: 'jhi-industry-type-detail',
    templateUrl: './industry-type-detail.component.html'
})
export class IndustryTypeDetailComponent implements OnInit, OnDestroy {

    industryType: IndustryType;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private industryTypeService: IndustryTypeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIndustryTypes();
    }

    load(id) {
        this.industryTypeService.find(id)
            .subscribe((industryTypeResponse: HttpResponse<IndustryType>) => {
                this.industryType = industryTypeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIndustryTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'industryTypeListModification',
            (response) => this.load(this.industryType.id)
        );
    }
}
