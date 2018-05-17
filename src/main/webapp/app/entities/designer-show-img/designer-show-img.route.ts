import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DesignerShowImgComponent } from './designer-show-img.component';
import { DesignerShowImgDetailComponent } from './designer-show-img-detail.component';
import { DesignerShowImgPopupComponent } from './designer-show-img-dialog.component';
import { DesignerShowImgDeletePopupComponent } from './designer-show-img-delete-dialog.component';

@Injectable()
export class DesignerShowImgResolvePagingParams implements Resolve<any> {

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

export const designerShowImgRoute: Routes = [
    {
        path: 'designer-show-img',
        component: DesignerShowImgComponent,
        resolve: {
            'pagingParams': DesignerShowImgResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerShowImg.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'designer-show-img/:id',
        component: DesignerShowImgDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerShowImg.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const designerShowImgPopupRoute: Routes = [
    {
        path: 'designer-show-img-new',
        component: DesignerShowImgPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerShowImg.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'designer-show-img/:id/edit',
        component: DesignerShowImgPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerShowImg.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'designer-show-img/:id/delete',
        component: DesignerShowImgDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerShowImg.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
