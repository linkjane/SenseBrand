import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { SolutionDetailAnalysisImgComponent } from './solution-detail-analysis-img.component';
import { SolutionDetailAnalysisImgDetailComponent } from './solution-detail-analysis-img-detail.component';
import { SolutionDetailAnalysisImgPopupComponent } from './solution-detail-analysis-img-dialog.component';
import { SolutionDetailAnalysisImgDeletePopupComponent } from './solution-detail-analysis-img-delete-dialog.component';

@Injectable()
export class SolutionDetailAnalysisImgResolvePagingParams implements Resolve<any> {

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

export const solutionDetailAnalysisImgRoute: Routes = [
    {
        path: 'solution-detail-analysis-img',
        component: SolutionDetailAnalysisImgComponent,
        resolve: {
            'pagingParams': SolutionDetailAnalysisImgResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.solutionDetailAnalysisImg.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'solution-detail-analysis-img/:id',
        component: SolutionDetailAnalysisImgDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.solutionDetailAnalysisImg.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const solutionDetailAnalysisImgPopupRoute: Routes = [
    {
        path: 'solution-detail-analysis-img-new',
        component: SolutionDetailAnalysisImgPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.solutionDetailAnalysisImg.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'solution-detail-analysis-img/:id/edit',
        component: SolutionDetailAnalysisImgPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.solutionDetailAnalysisImg.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'solution-detail-analysis-img/:id/delete',
        component: SolutionDetailAnalysisImgDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.solutionDetailAnalysisImg.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
