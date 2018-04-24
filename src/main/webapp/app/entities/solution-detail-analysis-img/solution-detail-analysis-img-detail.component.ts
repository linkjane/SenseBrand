import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { SolutionDetailAnalysisImg } from './solution-detail-analysis-img.model';
import { SolutionDetailAnalysisImgService } from './solution-detail-analysis-img.service';

@Component({
    selector: 'jhi-solution-detail-analysis-img-detail',
    templateUrl: './solution-detail-analysis-img-detail.component.html'
})
export class SolutionDetailAnalysisImgDetailComponent implements OnInit, OnDestroy {

    solutionDetailAnalysisImg: SolutionDetailAnalysisImg;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private solutionDetailAnalysisImgService: SolutionDetailAnalysisImgService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSolutionDetailAnalysisImgs();
    }

    load(id) {
        this.solutionDetailAnalysisImgService.find(id)
            .subscribe((solutionDetailAnalysisImgResponse: HttpResponse<SolutionDetailAnalysisImg>) => {
                this.solutionDetailAnalysisImg = solutionDetailAnalysisImgResponse.body;
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

    registerChangeInSolutionDetailAnalysisImgs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'solutionDetailAnalysisImgListModification',
            (response) => this.load(this.solutionDetailAnalysisImg.id)
        );
    }
}
