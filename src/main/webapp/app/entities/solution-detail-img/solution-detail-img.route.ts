import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SolutionDetailImgComponent } from './solution-detail-img.component';
import { SolutionDetailImgDetailComponent } from './solution-detail-img-detail.component';
import { SolutionDetailImgPopupComponent } from './solution-detail-img-dialog.component';
import { SolutionDetailImgDeletePopupComponent } from './solution-detail-img-delete-dialog.component';

@Injectable()
export class SolutionDetailImgResolvePagingParams implements Resolve<any> {

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

export const solutionDetailImgRoute: Routes = [
    {
        path: 'solution-detail-img',
        component: SolutionDetailImgComponent,
        resolve: {
            'pagingParams': SolutionDetailImgResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.solutionDetailImg.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'solution-detail-img/:id',
        component: SolutionDetailImgDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.solutionDetailImg.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const solutionDetailImgPopupRoute: Routes = [
    {
        path: 'solution-detail-img-new',
        component: SolutionDetailImgPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.solutionDetailImg.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'solution-detail-img/:id/edit',
        component: SolutionDetailImgPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.solutionDetailImg.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'solution-detail-img/:id/delete',
        component: SolutionDetailImgDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.solutionDetailImg.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
