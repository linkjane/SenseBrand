import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MyFileComponent } from './my-file.component';
import { MyFileDetailComponent } from './my-file-detail.component';
import { MyFilePopupComponent } from './my-file-dialog.component';
import { MyFileDeletePopupComponent } from './my-file-delete-dialog.component';

@Injectable()
export class MyFileResolvePagingParams implements Resolve<any> {

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

export const myFileRoute: Routes = [
    {
        path: 'my-file',
        component: MyFileComponent,
        resolve: {
            'pagingParams': MyFileResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.myFile.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'my-file/:id',
        component: MyFileDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.myFile.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const myFilePopupRoute: Routes = [
    {
        path: 'my-file-new',
        component: MyFilePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.myFile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'my-file/:id/edit',
        component: MyFilePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.myFile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'my-file/:id/delete',
        component: MyFileDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.myFile.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
