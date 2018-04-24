import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { SolutionCorrelation } from './solution-correlation.model';
import { SolutionCorrelationService } from './solution-correlation.service';

@Component({
    selector: 'jhi-solution-correlation-detail',
    templateUrl: './solution-correlation-detail.component.html'
})
export class SolutionCorrelationDetailComponent implements OnInit, OnDestroy {

    solutionCorrelation: SolutionCorrelation;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private solutionCorrelationService: SolutionCorrelationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSolutionCorrelations();
    }

    load(id) {
        this.solutionCorrelationService.find(id)
            .subscribe((solutionCorrelationResponse: HttpResponse<SolutionCorrelation>) => {
                this.solutionCorrelation = solutionCorrelationResponse.body;
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

    registerChangeInSolutionCorrelations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'solutionCorrelationListModification',
            (response) => this.load(this.solutionCorrelation.id)
        );
    }
}
