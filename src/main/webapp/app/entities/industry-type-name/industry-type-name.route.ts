import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { IndustryTypeNameComponent } from './industry-type-name.component';
import { IndustryTypeNameDetailComponent } from './industry-type-name-detail.component';
import { IndustryTypeNamePopupComponent } from './industry-type-name-dialog.component';
import { IndustryTypeNameDeletePopupComponent } from './industry-type-name-delete-dialog.component';

@Injectable()
export class IndustryTypeNameResolvePagingParams implements Resolve<any> {

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

export const industryTypeNameRoute: Routes = [
    {
        path: 'industry-type-name',
        component: IndustryTypeNameComponent,
        resolve: {
            'pagingParams': IndustryTypeNameResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryTypeName.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'industry-type-name/:id',
        component: IndustryTypeNameDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryTypeName.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const industryTypeNamePopupRoute: Routes = [
    {
        path: 'industry-type-name-new',
        component: IndustryTypeNamePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryTypeName.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'industry-type-name/:id/edit',
        component: IndustryTypeNamePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryTypeName.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'industry-type-name/:id/delete',
        component: IndustryTypeNameDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.industryTypeName.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
