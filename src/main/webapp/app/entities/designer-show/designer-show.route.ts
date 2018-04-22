import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DesignerShowComponent } from './designer-show.component';
import { DesignerShowDetailComponent } from './designer-show-detail.component';
import { DesignerShowPopupComponent } from './designer-show-dialog.component';
import { DesignerShowDeletePopupComponent } from './designer-show-delete-dialog.component';

@Injectable()
export class DesignerShowResolvePagingParams implements Resolve<any> {

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

export const designerShowRoute: Routes = [
    {
        path: 'designer-show',
        component: DesignerShowComponent,
        resolve: {
            'pagingParams': DesignerShowResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerShow.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'designer-show/:id',
        component: DesignerShowDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerShow.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const designerShowPopupRoute: Routes = [
    {
        path: 'designer-show-new',
        component: DesignerShowPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerShow.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'designer-show/:id/edit',
        component: DesignerShowPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerShow.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'designer-show/:id/delete',
        component: DesignerShowDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerShow.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
