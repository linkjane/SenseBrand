import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

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
        private dataUtils: JhiDataUtils,
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

    registerChangeInSolutionDetailAnalyses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'solutionDetailAnalysisListModification',
            (response) => this.load(this.solutionDetailAnalysis.id)
        );
    }
}
