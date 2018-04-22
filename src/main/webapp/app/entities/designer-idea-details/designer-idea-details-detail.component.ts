import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DesignerIdeaDetails } from './designer-idea-details.model';
import { DesignerIdeaDetailsService } from './designer-idea-details.service';

@Component({
    selector: 'jhi-designer-idea-details-detail',
    templateUrl: './designer-idea-details-detail.component.html'
})
export class DesignerIdeaDetailsDetailComponent implements OnInit, OnDestroy {

    designerIdeaDetails: DesignerIdeaDetails;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private designerIdeaDetailsService: DesignerIdeaDetailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDesignerIdeaDetails();
    }

    load(id) {
        this.designerIdeaDetailsService.find(id)
            .subscribe((designerIdeaDetailsResponse: HttpResponse<DesignerIdeaDetails>) => {
                this.designerIdeaDetails = designerIdeaDetailsResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDesignerIdeaDetails() {
        this.eventSubscriber = this.eventManager.subscribe(
            'designerIdeaDetailsListModification',
            (response) => this.load(this.designerIdeaDetails.id)
        );
    }
}
