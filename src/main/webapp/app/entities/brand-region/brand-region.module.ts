import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    BrandRegionService,
    BrandRegionPopupService,
    BrandRegionComponent,
    BrandRegionDetailComponent,
    BrandRegionDialogComponent,
    BrandRegionPopupComponent,
    BrandRegionDeletePopupComponent,
    BrandRegionDeleteDialogComponent,
    brandRegionRoute,
    brandRegionPopupRoute,
    BrandRegionResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...brandRegionRoute,
    ...brandRegionPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BrandRegionComponent,
        BrandRegionDetailComponent,
        BrandRegionDialogComponent,
        BrandRegionDeleteDialogComponent,
        BrandRegionPopupComponent,
        BrandRegionDeletePopupComponent,
    ],
    entryComponents: [
        BrandRegionComponent,
        BrandRegionDialogComponent,
        BrandRegionPopupComponent,
        BrandRegionDeleteDialogComponent,
        BrandRegionDeletePopupComponent,
    ],
    providers: [
        BrandRegionService,
        BrandRegionPopupService,
        BrandRegionResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandBrandRegionModule {}
