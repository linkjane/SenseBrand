import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    IndustryAllService,
    IndustryAllPopupService,
    IndustryAllComponent,
    IndustryAllDetailComponent,
    IndustryAllDialogComponent,
    IndustryAllPopupComponent,
    IndustryAllDeletePopupComponent,
    IndustryAllDeleteDialogComponent,
    industryAllRoute,
    industryAllPopupRoute,
    IndustryAllResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...industryAllRoute,
    ...industryAllPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        IndustryAllComponent,
        IndustryAllDetailComponent,
        IndustryAllDialogComponent,
        IndustryAllDeleteDialogComponent,
        IndustryAllPopupComponent,
        IndustryAllDeletePopupComponent,
    ],
    entryComponents: [
        IndustryAllComponent,
        IndustryAllDialogComponent,
        IndustryAllPopupComponent,
        IndustryAllDeleteDialogComponent,
        IndustryAllDeletePopupComponent,
    ],
    providers: [
        IndustryAllService,
        IndustryAllPopupService,
        IndustryAllResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandIndustryAllModule {}
