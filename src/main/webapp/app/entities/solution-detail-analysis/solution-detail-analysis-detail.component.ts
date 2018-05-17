import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SolutionDetailAnalysis } from './solution-detail-analysis.model';
import { SolutionDetailAnalysisService } from './solution-detail-analysis.service';

@Component({
    selector: 'jhi-solution-detail-analysis-detail',
    templateUrl: './solution-detail-analysis-detail.component.html'
})
export class SolutionDetailAnalysisDetailComponent implements OnInit, OnDestroy {

    solutionDetailAnalysis: SolutionDetailAnalysis;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private solutionDetailAnalysisService: SolutionDetailAnalysisService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSolutionDetailAnalyses();
    }

    load(id) {
        this.solutionDetailAnalysisService.find(id)
            .subscribe((solutionDetailAnalysisResponse: HttpResponse<SolutionDetailAnalysis>) => {
                this.solutionDetailAnalysis = solutionDetailAnalysisResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSolutionDetailAnalyses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'solutionDetailAnalysisListModification',
            (response) => this.load(this.solutionDetailAnalysis.id)
        );
    }
}
