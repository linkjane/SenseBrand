import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { IndustryAllComponent } from './industry-all.component';
import { IndustryAllDetailComponent } from './industry-all-detail.component';
import { IndustryAllPopupComponent } from './industry-all-dialog.component';
import { IndustryAllDeletePopupComponent } from './industry-all-delete-dialog.component';

@Injectable()
export class IndustryAllResolvePagingParams implements Resolve<any> {

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

export const industryAllRoute: Routes = [
    {
        path: 'industry-all',
        component: IndustryAllComponent,
        resolve: {
            'pagingParams': IndustryAllResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryAll.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'industry-all/:id',
        component: IndustryAllDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryAll.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const industryAllPopupRoute: Routes = [
    {
        path: 'industry-all-new',
        component: IndustryAllPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryAll.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'industry-all/:id/edit',
        component: IndustryAllPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryAll.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'industry-all/:id/delete',
        component: IndustryAllDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryAll.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
