import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { OurSightComponent } from './our-sight.component';
import { OurSightDetailComponent } from './our-sight-detail.component';
import { OurSightPopupComponent } from './our-sight-dialog.component';
import { OurSightDeletePopupComponent } from './our-sight-delete-dialog.component';

@Injectable()
export class OurSightResolvePagingParams implements Resolve<any> {

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

export const ourSightRoute: Routes = [
    {
        path: 'our-sight',
        component: OurSightComponent,
        resolve: {
            'pagingParams': OurSightResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.ourSight.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'our-sight/:id',
        component: OurSightDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.ourSight.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ourSightPopupRoute: Routes = [
    {
        path: 'our-sight-new',
        component: OurSightPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.ourSight.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'our-sight/:id/edit',
        component: OurSightPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.ourSight.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'our-sight/:id/delete',
        component: OurSightDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.ourSight.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
