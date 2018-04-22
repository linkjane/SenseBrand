import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DesignerShow } from './designer-show.model';
import { DesignerShowService } from './designer-show.service';

@Component({
    selector: 'jhi-designer-show-detail',
    templateUrl: './designer-show-detail.component.html'
})
export class DesignerShowDetailComponent implements OnInit, OnDestroy {

    designerShow: DesignerShow;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private designerShowService: DesignerShowService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDesignerShows();
    }

    load(id) {
        this.designerShowService.find(id)
            .subscribe((designerShowResponse: HttpResponse<DesignerShow>) => {
                this.designerShow = designerShowResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDesignerShows() {
        this.eventSubscriber = this.eventManager.subscribe(
            'designerShowListModification',
            (response) => this.load(this.designerShow.id)
        );
    }
}
