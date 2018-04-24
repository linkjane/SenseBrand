import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BrandSubComponent } from './brand-sub.component';
import { BrandSubDetailComponent } from './brand-sub-detail.component';
import { BrandSubPopupComponent } from './brand-sub-dialog.component';
import { BrandSubDeletePopupComponent } from './brand-sub-delete-dialog.component';

@Injectable()
export class BrandSubResolvePagingParams implements Resolve<any> {

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

export const brandSubRoute: Routes = [
    {
        path: 'brand-sub',
        component: BrandSubComponent,
        resolve: {
            'pagingParams': BrandSubResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.brandSub.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'brand-sub/:id',
        component: BrandSubDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.brandSub.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const brandSubPopupRoute: Routes = [
    {
        path: 'brand-sub-new',
        component: BrandSubPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.brandSub.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'brand-sub/:id/edit',
        component: BrandSubPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.brandSub.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'brand-sub/:id/delete',
        component: BrandSubDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.brandSub.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
