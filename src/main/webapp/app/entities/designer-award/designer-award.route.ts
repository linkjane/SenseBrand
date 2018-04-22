import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DesignerAwardComponent } from './designer-award.component';
import { DesignerAwardDetailComponent } from './designer-award-detail.component';
import { DesignerAwardPopupComponent } from './designer-award-dialog.component';
import { DesignerAwardDeletePopupComponent } from './designer-award-delete-dialog.component';

@Injectable()
export class DesignerAwardResolvePagingParams implements Resolve<any> {

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

export const designerAwardRoute: Routes = [
    {
        path: 'designer-award',
        component: DesignerAwardComponent,
        resolve: {
            'pagingParams': DesignerAwardResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerAward.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'designer-award/:id',
        component: DesignerAwardDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerAward.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const designerAwardPopupRoute: Routes = [
    {
        path: 'designer-award-new',
        component: DesignerAwardPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerAward.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'designer-award/:id/edit',
        component: DesignerAwardPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerAward.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'designer-award/:id/delete',
        component: DesignerAwardDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerAward.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
