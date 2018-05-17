import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { DesignerIdeaMedia } from './designer-idea-media.model';
import { DesignerIdeaMediaService } from './designer-idea-media.service';

@Component({
    selector: 'jhi-designer-idea-media-detail',
    templateUrl: './designer-idea-media-detail.component.html'
})
export class DesignerIdeaMediaDetailComponent implements OnInit, OnDestroy {

    designerIdeaMedia: DesignerIdeaMedia;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private designerIdeaMediaService: DesignerIdeaMediaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDesignerIdeaMedias();
    }

    load(id) {
        this.designerIdeaMediaService.find(id)
            .subscribe((designerIdeaMediaResponse: HttpResponse<DesignerIdeaMedia>) => {
                this.designerIdeaMedia = designerIdeaMediaResponse.body;
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

    registerChangeInDesignerIdeaMedias() {
        this.eventSubscriber = this.eventManager.subscribe(
            'designerIdeaMediaListModification',
            (response) => this.load(this.designerIdeaMedia.id)
        );
    }
}
