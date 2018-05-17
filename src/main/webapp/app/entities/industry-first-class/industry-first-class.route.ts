import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { IndustryFirstClassComponent } from './industry-first-class.component';
import { IndustryFirstClassDetailComponent } from './industry-first-class-detail.component';
import { IndustryFirstClassPopupComponent } from './industry-first-class-dialog.component';
import { IndustryFirstClassDeletePopupComponent } from './industry-first-class-delete-dialog.component';

@Injectable()
export class IndustryFirstClassResolvePagingParams implements Resolve<any> {

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

export const industryFirstClassRoute: Routes = [
    {
        path: 'industry-first-class',
        component: IndustryFirstClassComponent,
        resolve: {
            'pagingParams': IndustryFirstClassResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryFirstClass.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'industry-first-class/:id',
        component: IndustryFirstClassDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryFirstClass.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const industryFirstClassPopupRoute: Routes = [
    {
        path: 'industry-first-class-new',
        component: IndustryFirstClassPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryFirstClass.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'industry-first-class/:id/edit',
        component: IndustryFirstClassPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryFirstClass.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'industry-first-class/:id/delete',
        component: IndustryFirstClassDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryFirstClass.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
