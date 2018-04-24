import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BrandSubDetailsComponent } from './brand-sub-details.component';
import { BrandSubDetailsDetailComponent } from './brand-sub-details-detail.component';
import { BrandSubDetailsPopupComponent } from './brand-sub-details-dialog.component';
import { BrandSubDetailsDeletePopupComponent } from './brand-sub-details-delete-dialog.component';

@Injectable()
export class BrandSubDetailsResolvePagingParams implements Resolve<any> {

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

export const brandSubDetailsRoute: Routes = [
    {
        path: 'brand-sub-details',
        component: BrandSubDetailsComponent,
        resolve: {
            'pagingParams': BrandSubDetailsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.brandSubDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'brand-sub-details/:id',
        component: BrandSubDetailsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.brandSubDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const brandSubDetailsPopupRoute: Routes = [
    {
        path: 'brand-sub-details-new',
        component: BrandSubDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.brandSubDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'brand-sub-details/:id/edit',
        component: BrandSubDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.brandSubDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'brand-sub-details/:id/delete',
        component: BrandSubDetailsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.brandSubDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
