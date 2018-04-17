import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DesignerComponent } from './designer.component';
import { DesignerDetailComponent } from './designer-detail.component';
import { DesignerPopupComponent } from './designer-dialog.component';
import { DesignerDeletePopupComponent } from './designer-delete-dialog.component';

@Injectable()
export class DesignerResolvePagingParams implements Resolve<any> {

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

export const designerRoute: Routes = [
    {
        path: 'designer',
        component: DesignerComponent,
        resolve: {
            'pagingParams': DesignerResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'designer/:id',
        component: DesignerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const designerPopupRoute: Routes = [
    {
        path: 'designer-new',
        component: DesignerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'designer/:id/edit',
        component: DesignerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'designer/:id/delete',
        component: DesignerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
