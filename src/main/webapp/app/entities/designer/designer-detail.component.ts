import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Designer } from './designer.model';
import { DesignerService } from './designer.service';

@Component({
    selector: 'jhi-designer-detail',
    templateUrl: './designer-detail.component.html'
})
export class DesignerDetailComponent implements OnInit, OnDestroy {

    designer: Designer;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private designerService: DesignerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDesigners();
    }

    load(id) {
        this.designerService.find(id)
            .subscribe((designerResponse: HttpResponse<Designer>) => {
                this.designer = designerResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDesigners() {
        this.eventSubscriber = this.eventManager.subscribe(
            'designerListModification',
            (response) => this.load(this.designer.id)
        );
    }
}
