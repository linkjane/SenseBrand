import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { DesignerSentiment } from './designer-sentiment.model';
import { DesignerSentimentService } from './designer-sentiment.service';

@Component({
    selector: 'jhi-designer-sentiment-detail',
    templateUrl: './designer-sentiment-detail.component.html'
})
export class DesignerSentimentDetailComponent implements OnInit, OnDestroy {

    designerSentiment: DesignerSentiment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private designerSentimentService: DesignerSentimentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDesignerSentiments();
    }

    load(id) {
        this.designerSentimentService.find(id)
            .subscribe((designerSentimentResponse: HttpResponse<DesignerSentiment>) => {
                this.designerSentiment = designerSentimentResponse.body;
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

    registerChangeInDesignerSentiments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'designerSentimentListModification',
            (response) => this.load(this.designerSentiment.id)
        );
    }
}
