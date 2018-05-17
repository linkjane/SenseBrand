import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DesignerIdeaMediaComponent } from './designer-idea-media.component';
import { DesignerIdeaMediaDetailComponent } from './designer-idea-media-detail.component';
import { DesignerIdeaMediaPopupComponent } from './designer-idea-media-dialog.component';
import { DesignerIdeaMediaDeletePopupComponent } from './designer-idea-media-delete-dialog.component';

@Injectable()
export class DesignerIdeaMediaResolvePagingParams implements Resolve<any> {

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

export const designerIdeaMediaRoute: Routes = [
    {
        path: 'designer-idea-media',
        component: DesignerIdeaMediaComponent,
        resolve: {
            'pagingParams': DesignerIdeaMediaResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerIdeaMedia.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'designer-idea-media/:id',
        component: DesignerIdeaMediaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerIdeaMedia.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const designerIdeaMediaPopupRoute: Routes = [
    {
        path: 'designer-idea-media-new',
        component: DesignerIdeaMediaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerIdeaMedia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'designer-idea-media/:id/edit',
        component: DesignerIdeaMediaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerIdeaMedia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'designer-idea-media/:id/delete',
        component: DesignerIdeaMediaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.designerIdeaMedia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
