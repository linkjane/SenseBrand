import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DesignerSentimentComponent } from './designer-sentiment.component';
import { DesignerSentimentDetailComponent } from './designer-sentiment-detail.component';
import { DesignerSentimentPopupComponent } from './designer-sentiment-dialog.component';
import { DesignerSentimentDeletePopupComponent } from './designer-sentiment-delete-dialog.component';

@Injectable()
export class DesignerSentimentResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const designerSentimentRoute: Routes = [
    {
        path: 'designer-sentiment',
        component: DesignerSentimentComponent,
        resolve: {
            'pagingParams': DesignerSentimentResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerSentiment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'designer-sentiment/:id',
        component: DesignerSentimentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerSentiment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const designerSentimentPopupRoute: Routes = [
    {
        path: 'designer-sentiment-new',
        component: DesignerSentimentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerSentiment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'designer-sentiment/:id/edit',
        component: DesignerSentimentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerSentiment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'designer-sentiment/:id/delete',
        component: DesignerSentimentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerSentiment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
