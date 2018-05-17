import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    BrandRankService,
    BrandRankPopupService,
    BrandRankComponent,
    BrandRankDetailComponent,
    BrandRankDialogComponent,
    BrandRankPopupComponent,
    BrandRankDeletePopupComponent,
    BrandRankDeleteDialogComponent,
    brandRankRoute,
    brandRankPopupRoute,
    BrandRankResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...brandRankRoute,
    ...brandRankPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BrandRankComponent,
        BrandRankDetailComponent,
        BrandRankDialogComponent,
        BrandRankDeleteDialogComponent,
        BrandRankPopupComponent,
        BrandRankDeletePopupComponent,
    ],
    entryComponents: [
        BrandRankComponent,
        BrandRankDialogComponent,
        BrandRankPopupComponent,
        BrandRankDeleteDialogComponent,
        BrandRankDeletePopupComponent,
    ],
    providers: [
        BrandRankService,
        BrandRankPopupService,
        BrandRankResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandBrandRankModule {}
