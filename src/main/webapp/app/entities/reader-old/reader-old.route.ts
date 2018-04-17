import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ReaderOldComponent } from './reader-old.component';
import { ReaderOldDetailComponent } from './reader-old-detail.component';
import { ReaderOldPopupComponent } from './reader-old-dialog.component';
import { ReaderOldDeletePopupComponent } from './reader-old-delete-dialog.component';

export const readerOldRoute: Routes = [
    {
        path: 'reader-old',
        component: ReaderOldComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.readerOld.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'reader-old/:id',
        component: ReaderOldDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.readerOld.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const readerOldPopupRoute: Routes = [
    {
        path: 'reader-old-new',
        component: ReaderOldPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.readerOld.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'reader-old/:id/edit',
        component: ReaderOldPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.readerOld.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'reader-old/:id/delete',
        component: ReaderOldDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.readerOld.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
