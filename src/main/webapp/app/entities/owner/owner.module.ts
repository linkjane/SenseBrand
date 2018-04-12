import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    OwnerService,
    OwnerPopupService,
    OwnerComponent,
    OwnerDetailComponent,
    OwnerDialogComponent,
    OwnerPopupComponent,
    OwnerDeletePopupComponent,
    OwnerDeleteDialogComponent,
    ownerRoute,
    ownerPopupRoute,
    OwnerResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...ownerRoute,
    ...ownerPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OwnerComponent,
        OwnerDetailComponent,
        OwnerDialogComponent,
        OwnerDeleteDialogComponent,
        OwnerPopupComponent,
        OwnerDeletePopupComponent,
    ],
    entryComponents: [
        OwnerComponent,
        OwnerDialogComponent,
        OwnerPopupComponent,
        OwnerDeleteDialogComponent,
        OwnerDeletePopupComponent,
    ],
    providers: [
        OwnerService,
        OwnerPopupService,
        OwnerResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandOwnerModule {}
