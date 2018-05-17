import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BrandRegionComponent } from './brand-region.component';
import { BrandRegionDetailComponent } from './brand-region-detail.component';
import { BrandRegionPopupComponent } from './brand-region-dialog.component';
import { BrandRegionDeletePopupComponent } from './brand-region-delete-dialog.component';

@Injectable()
export class BrandRegionResolvePagingParams implements Resolve<any> {

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

export const brandRegionRoute: Routes = [
    {
        path: 'brand-region',
        component: BrandRegionComponent,
        resolve: {
            'pagingParams': BrandRegionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.brandRegion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'brand-region/:id',
        component: BrandRegionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.brandRegion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const brandRegionPopupRoute: Routes = [
    {
        path: 'brand-region-new',
        component: BrandRegionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.brandRegion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'brand-region/:id/edit',
        component: BrandRegionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.brandRegion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'brand-region/:id/delete',
        component: BrandRegionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.brandRegion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
