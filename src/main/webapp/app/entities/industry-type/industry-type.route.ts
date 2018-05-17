import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { IndustryTypeComponent } from './industry-type.component';
import { IndustryTypeDetailComponent } from './industry-type-detail.component';
import { IndustryTypePopupComponent } from './industry-type-dialog.component';
import { IndustryTypeDeletePopupComponent } from './industry-type-delete-dialog.component';

@Injectable()
export class IndustryTypeResolvePagingParams implements Resolve<any> {

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

export const industryTypeRoute: Routes = [
    {
        path: 'industry-type',
        component: IndustryTypeComponent,
        resolve: {
            'pagingParams': IndustryTypeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'industry-type/:id',
        component: IndustryTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const industryTypePopupRoute: Routes = [
    {
        path: 'industry-type-new',
        component: IndustryTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'industry-type/:id/edit',
        component: IndustryTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'industry-type/:id/delete',
        component: IndustryTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
