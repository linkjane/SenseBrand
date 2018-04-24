import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { IndustrySecondClass } from './industry-second-class.model';
import { IndustrySecondClassService } from './industry-second-class.service';

@Component({
    selector: 'jhi-industry-second-class-detail',
    templateUrl: './industry-second-class-detail.component.html'
})
export class IndustrySecondClassDetailComponent implements OnInit, OnDestroy {

    industrySecondClass: IndustrySecondClass;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private industrySecondClassService: IndustrySecondClassService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIndustrySecondClasses();
    }

    load(id) {
        this.industrySecondClassService.find(id)
            .subscribe((industrySecondClassResponse: HttpResponse<IndustrySecondClass>) => {
                this.industrySecondClass = industrySecondClassResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIndustrySecondClasses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'industrySecondClassListModification',
            (response) => this.load(this.industrySecondClass.id)
        );
    }
}
