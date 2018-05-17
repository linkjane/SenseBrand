import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BrandRankComponent } from './brand-rank.component';
import { BrandRankDetailComponent } from './brand-rank-detail.component';
import { BrandRankPopupComponent } from './brand-rank-dialog.component';
import { BrandRankDeletePopupComponent } from './brand-rank-delete-dialog.component';

@Injectable()
export class BrandRankResolvePagingParams implements Resolve<any> {

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

export const brandRankRoute: Routes = [
    {
        path: 'brand-rank',
        component: BrandRankComponent,
        resolve: {
            'pagingParams': BrandRankResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.brandRank.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'brand-rank/:id',
        component: BrandRankDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.brandRank.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const brandRankPopupRoute: Routes = [
    {
        path: 'brand-rank-new',
        component: BrandRankPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.brandRank.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'brand-rank/:id/edit',
        component: BrandRankPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.brandRank.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'brand-rank/:id/delete',
        component: BrandRankDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.brandRank.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
