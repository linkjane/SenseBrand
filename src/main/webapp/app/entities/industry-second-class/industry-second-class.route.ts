import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { IndustrySecondClassComponent } from './industry-second-class.component';
import { IndustrySecondClassDetailComponent } from './industry-second-class-detail.component';
import { IndustrySecondClassPopupComponent } from './industry-second-class-dialog.component';
import { IndustrySecondClassDeletePopupComponent } from './industry-second-class-delete-dialog.component';

@Injectable()
export class IndustrySecondClassResolvePagingParams implements Resolve<any> {

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

export const industrySecondClassRoute: Routes = [
    {
        path: 'industry-second-class',
        component: IndustrySecondClassComponent,
        resolve: {
            'pagingParams': IndustrySecondClassResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industrySecondClass.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'industry-second-class/:id',
        component: IndustrySecondClassDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industrySecondClass.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const industrySecondClassPopupRoute: Routes = [
    {
        path: 'industry-second-class-new',
        component: IndustrySecondClassPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industrySecondClass.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'industry-second-class/:id/edit',
        component: IndustrySecondClassPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industrySecondClass.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'industry-second-class/:id/delete',
        component: IndustrySecondClassDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industrySecondClass.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
