import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { IndustryFirstClass } from './industry-first-class.model';
import { IndustryFirstClassService } from './industry-first-class.service';

@Component({
    selector: 'jhi-industry-first-class-detail',
    templateUrl: './industry-first-class-detail.component.html'
})
export class IndustryFirstClassDetailComponent implements OnInit, OnDestroy {

    industryFirstClass: IndustryFirstClass;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private industryFirstClassService: IndustryFirstClassService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIndustryFirstClasses();
    }

    load(id) {
        this.industryFirstClassService.find(id)
            .subscribe((industryFirstClassResponse: HttpResponse<IndustryFirstClass>) => {
                this.industryFirstClass = industryFirstClassResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIndustryFirstClasses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'industryFirstClassListModification',
            (response) => this.load(this.industryFirstClass.id)
        );
    }
}
