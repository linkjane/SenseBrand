import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { IndustryTopComponent } from './industry-top.component';
import { IndustryTopDetailComponent } from './industry-top-detail.component';
import { IndustryTopPopupComponent } from './industry-top-dialog.component';
import { IndustryTopDeletePopupComponent } from './industry-top-delete-dialog.component';

@Injectable()
export class IndustryTopResolvePagingParams implements Resolve<any> {

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

export const industryTopRoute: Routes = [
    {
        path: 'industry-top',
        component: IndustryTopComponent,
        resolve: {
            'pagingParams': IndustryTopResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryTop.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'industry-top/:id',
        component: IndustryTopDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryTop.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const industryTopPopupRoute: Routes = [
    {
        path: 'industry-top-new',
        component: IndustryTopPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryTop.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'industry-top/:id/edit',
        component: IndustryTopPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryTop.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'industry-top/:id/delete',
        component: IndustryTopDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryTop.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
