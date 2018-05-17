import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { GrandAwardComponent } from './grand-award.component';
import { GrandAwardDetailComponent } from './grand-award-detail.component';
import { GrandAwardPopupComponent } from './grand-award-dialog.component';
import { GrandAwardDeletePopupComponent } from './grand-award-delete-dialog.component';

@Injectable()
export class GrandAwardResolvePagingParams implements Resolve<any> {

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

export const grandAwardRoute: Routes = [
    {
        path: 'grand-award',
        component: GrandAwardComponent,
        resolve: {
            'pagingParams': GrandAwardResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.grandAward.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'grand-award/:id',
        component: GrandAwardDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.grandAward.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const grandAwardPopupRoute: Routes = [
    {
        path: 'grand-award-new',
        component: GrandAwardPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.grandAward.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'grand-award/:id/edit',
        component: GrandAwardPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.grandAward.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'grand-award/:id/delete',
        component: GrandAwardDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.grandAward.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
