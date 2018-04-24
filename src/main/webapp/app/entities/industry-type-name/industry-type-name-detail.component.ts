import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { IndustryTypeName } from './industry-type-name.model';
import { IndustryTypeNameService } from './industry-type-name.service';

@Component({
    selector: 'jhi-industry-type-name-detail',
    templateUrl: './industry-type-name-detail.component.html'
})
export class IndustryTypeNameDetailComponent implements OnInit, OnDestroy {

    industryTypeName: IndustryTypeName;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private industryTypeNameService: IndustryTypeNameService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIndustryTypeNames();
    }

    load(id) {
        this.industryTypeNameService.find(id)
            .subscribe((industryTypeNameResponse: HttpResponse<IndustryTypeName>) => {
                this.industryTypeName = industryTypeNameResponse.body;
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

    registerChangeInIndustryTypeNames() {
        this.eventSubscriber = this.eventManager.subscribe(
            'industryTypeNameListModification',
            (response) => this.load(this.industryTypeName.id)
        );
    }
}
