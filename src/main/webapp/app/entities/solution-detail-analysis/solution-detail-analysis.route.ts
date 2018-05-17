import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SolutionDetailAnalysisComponent } from './solution-detail-analysis.component';
import { SolutionDetailAnalysisDetailComponent } from './solution-detail-analysis-detail.component';
import { SolutionDetailAnalysisPopupComponent } from './solution-detail-analysis-dialog.component';
import { SolutionDetailAnalysisDeletePopupComponent } from './solution-detail-analysis-delete-dialog.component';

@Injectable()
export class SolutionDetailAnalysisResolvePagingParams implements Resolve<any> {

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

export const solutionDetailAnalysisRoute: Routes = [
    {
        path: 'solution-detail-analysis',
        component: SolutionDetailAnalysisComponent,
        resolve: {
            'pagingParams': SolutionDetailAnalysisResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.solutionDetailAnalysis.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'solution-detail-analysis/:id',
        component: SolutionDetailAnalysisDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.solutionDetailAnalysis.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const solutionDetailAnalysisPopupRoute: Routes = [
    {
        path: 'solution-detail-analysis-new',
        component: SolutionDetailAnalysisPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.solutionDetailAnalysis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'solution-detail-analysis/:id/edit',
        component: SolutionDetailAnalysisPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.solutionDetailAnalysis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'solution-detail-analysis/:id/delete',
        component: SolutionDetailAnalysisDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.solutionDetailAnalysis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
