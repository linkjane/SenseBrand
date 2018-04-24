import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    BrandSubService,
    BrandSubPopupService,
    BrandSubComponent,
    BrandSubDetailComponent,
    BrandSubDialogComponent,
    BrandSubPopupComponent,
    BrandSubDeletePopupComponent,
    BrandSubDeleteDialogComponent,
    brandSubRoute,
    brandSubPopupRoute,
    BrandSubResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...brandSubRoute,
    ...brandSubPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BrandSubComponent,
        BrandSubDetailComponent,
        BrandSubDialogComponent,
        BrandSubDeleteDialogComponent,
        BrandSubPopupComponent,
        BrandSubDeletePopupComponent,
    ],
    entryComponents: [
        BrandSubComponent,
        BrandSubDialogComponent,
        BrandSubPopupComponent,
        BrandSubDeleteDialogComponent,
        BrandSubDeletePopupComponent,
    ],
    providers: [
        BrandSubService,
        BrandSubPopupService,
        BrandSubResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandBrandSubModule {}
