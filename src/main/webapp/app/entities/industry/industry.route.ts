import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { IndustryComponent } from './industry.component';
import { IndustryDetailComponent } from './industry-detail.component';
import { IndustryPopupComponent } from './industry-dialog.component';
import { IndustryDeletePopupComponent } from './industry-delete-dialog.component';

@Injectable()
export class IndustryResolvePagingParams implements Resolve<any> {

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

export const industryRoute: Routes = [
    {
        path: 'industry',
        component: IndustryComponent,
        resolve: {
            'pagingParams': IndustryResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'industry/:id',
        component: IndustryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industry.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const industryPopupRoute: Routes = [
    {
        path: 'industry-new',
        component: IndustryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'industry/:id/edit',
        component: IndustryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'industry/:id/delete',
        component: IndustryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industry.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
