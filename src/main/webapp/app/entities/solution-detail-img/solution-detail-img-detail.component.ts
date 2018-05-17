import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { SolutionDetailImg } from './solution-detail-img.model';
import { SolutionDetailImgService } from './solution-detail-img.service';

@Component({
    selector: 'jhi-solution-detail-img-detail',
    templateUrl: './solution-detail-img-detail.component.html'
})
export class SolutionDetailImgDetailComponent implements OnInit, OnDestroy {

    solutionDetailImg: SolutionDetailImg;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private solutionDetailImgService: SolutionDetailImgService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSolutionDetailImgs();
    }

    load(id) {
        this.solutionDetailImgService.find(id)
            .subscribe((solutionDetailImgResponse: HttpResponse<SolutionDetailImg>) => {
                this.solutionDetailImg = solutionDetailImgResponse.body;
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

    registerChangeInSolutionDetailImgs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'solutionDetailImgListModification',
            (response) => this.load(this.solutionDetailImg.id)
        );
    }
}
