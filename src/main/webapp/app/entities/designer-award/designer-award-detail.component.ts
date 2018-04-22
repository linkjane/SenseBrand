import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { DesignerAward } from './designer-award.model';
import { DesignerAwardService } from './designer-award.service';

@Component({
    selector: 'jhi-designer-award-detail',
    templateUrl: './designer-award-detail.component.html'
})
export class DesignerAwardDetailComponent implements OnInit, OnDestroy {

    designerAward: DesignerAward;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private designerAwardService: DesignerAwardService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDesignerAwards();
    }

    load(id) {
        this.designerAwardService.find(id)
            .subscribe((designerAwardResponse: HttpResponse<DesignerAward>) => {
                this.designerAward = designerAwardResponse.body;
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

    registerChangeInDesignerAwards() {
        this.eventSubscriber = this.eventManager.subscribe(
            'designerAwardListModification',
            (response) => this.load(this.designerAward.id)
        );
    }
}
