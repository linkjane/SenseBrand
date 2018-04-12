import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { OwnerComponent } from './owner.component';
import { OwnerDetailComponent } from './owner-detail.component';
import { OwnerPopupComponent } from './owner-dialog.component';
import { OwnerDeletePopupComponent } from './owner-delete-dialog.component';

@Injectable()
export class OwnerResolvePagingParams implements Resolve<any> {

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

export const ownerRoute: Routes = [
    {
        path: 'owner',
        component: OwnerComponent,
        resolve: {
            'pagingParams': OwnerResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.owner.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'owner/:id',
        component: OwnerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.owner.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ownerPopupRoute: Routes = [
    {
        path: 'owner-new',
        component: OwnerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.owner.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'owner/:id/edit',
        component: OwnerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.owner.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'owner/:id/delete',
        component: OwnerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.owner.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
