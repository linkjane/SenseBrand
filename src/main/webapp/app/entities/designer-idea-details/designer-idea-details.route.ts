import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DesignerIdeaDetailsComponent } from './designer-idea-details.component';
import { DesignerIdeaDetailsDetailComponent } from './designer-idea-details-detail.component';
import { DesignerIdeaDetailsPopupComponent } from './designer-idea-details-dialog.component';
import { DesignerIdeaDetailsDeletePopupComponent } from './designer-idea-details-delete-dialog.component';

@Injectable()
export class DesignerIdeaDetailsResolvePagingParams implements Resolve<any> {

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

export const designerIdeaDetailsRoute: Routes = [
    {
        path: 'designer-idea-details',
        component: DesignerIdeaDetailsComponent,
        resolve: {
            'pagingParams': DesignerIdeaDetailsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerIdeaDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'designer-idea-details/:id',
        component: DesignerIdeaDetailsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerIdeaDetails.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const designerIdeaDetailsPopupRoute: Routes = [
    {
        path: 'designer-idea-details-new',
        component: DesignerIdeaDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerIdeaDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'designer-idea-details/:id/edit',
        component: DesignerIdeaDetailsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerIdeaDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'designer-idea-details/:id/delete',
        component: DesignerIdeaDetailsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerIdeaDetails.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
