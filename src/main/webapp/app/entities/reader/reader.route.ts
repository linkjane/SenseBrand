import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ReaderComponent } from './reader.component';
import { ReaderDetailComponent } from './reader-detail.component';
import { ReaderPopupComponent } from './reader-dialog.component';
import { ReaderDeletePopupComponent } from './reader-delete-dialog.component';

export const readerRoute: Routes = [
    {
        path: 'reader',
        component: ReaderComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.reader.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'reader/:id',
        component: ReaderDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.reader.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const readerPopupRoute: Routes = [
    {
        path: 'reader-new',
        component: ReaderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.reader.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'reader/:id/edit',
        component: ReaderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.reader.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'reader/:id/delete',
        component: ReaderDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senseBrandApp.reader.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
