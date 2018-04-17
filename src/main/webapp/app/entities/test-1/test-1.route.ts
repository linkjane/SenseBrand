import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { Test1Component } from './test-1.component';
import { Test1DetailComponent } from './test-1-detail.component';
import { Test1PopupComponent } from './test-1-dialog.component';
import { Test1DeletePopupComponent } from './test-1-delete-dialog.component';

export const test1Route: Routes = [
    {
        path: 'test-1',
        component: Test1Component,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.test1.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'test-1/:id',
        component: Test1DetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.test1.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const test1PopupRoute: Routes = [
    {
        path: 'test-1-new',
        component: Test1PopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.test1.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'test-1/:id/edit',
        component: Test1PopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.test1.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'test-1/:id/delete',
        component: Test1DeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.test1.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
