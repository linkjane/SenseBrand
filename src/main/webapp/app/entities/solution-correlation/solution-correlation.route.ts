import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SolutionCorrelationComponent } from './solution-correlation.component';
import { SolutionCorrelationDetailComponent } from './solution-correlation-detail.component';
import { SolutionCorrelationPopupComponent } from './solution-correlation-dialog.component';
import { SolutionCorrelationDeletePopupComponent } from './solution-correlation-delete-dialog.component';

@Injectable()
export class SolutionCorrelationResolvePagingParams implements Resolve<any> {

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

export const solutionCorrelationRoute: Routes = [
    {
        path: 'solution-correlation',
        component: SolutionCorrelationComponent,
        resolve: {
            'pagingParams': SolutionCorrelationResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.solutionCorrelation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'solution-correlation/:id',
        component: SolutionCorrelationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.solutionCorrelation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const solutionCorrelationPopupRoute: Routes = [
    {
        path: 'solution-correlation-new',
        component: SolutionCorrelationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.solutionCorrelation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'solution-correlation/:id/edit',
        component: SolutionCorrelationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.solutionCorrelation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'solution-correlation/:id/delete',
        component: SolutionCorrelationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.solutionCorrelation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
