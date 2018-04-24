import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    IndustryTypeService,
    IndustryTypePopupService,
    IndustryTypeComponent,
    IndustryTypeDetailComponent,
    IndustryTypeDialogComponent,
    IndustryTypePopupComponent,
    IndustryTypeDeletePopupComponent,
    IndustryTypeDeleteDialogComponent,
    industryTypeRoute,
    industryTypePopupRoute,
    IndustryTypeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...industryTypeRoute,
    ...industryTypePopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        IndustryTypeComponent,
        IndustryTypeDetailComponent,
        IndustryTypeDialogComponent,
        IndustryTypeDeleteDialogComponent,
        IndustryTypePopupComponent,
        IndustryTypeDeletePopupComponent,
    ],
    entryComponents: [
        IndustryTypeComponent,
        IndustryTypeDialogComponent,
        IndustryTypePopupComponent,
        IndustryTypeDeleteDialogComponent,
        IndustryTypeDeletePopupComponent,
    ],
    providers: [
        IndustryTypeService,
        IndustryTypePopupService,
        IndustryTypeResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandIndustryTypeModule {}
