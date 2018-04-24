import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    IndustryService,
    IndustryPopupService,
    IndustryComponent,
    IndustryDetailComponent,
    IndustryDialogComponent,
    IndustryPopupComponent,
    IndustryDeletePopupComponent,
    IndustryDeleteDialogComponent,
    industryRoute,
    industryPopupRoute,
    IndustryResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...industryRoute,
    ...industryPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        IndustryComponent,
        IndustryDetailComponent,
        IndustryDialogComponent,
        IndustryDeleteDialogComponent,
        IndustryPopupComponent,
        IndustryDeletePopupComponent,
    ],
    entryComponents: [
        IndustryComponent,
        IndustryDialogComponent,
        IndustryPopupComponent,
        IndustryDeleteDialogComponent,
        IndustryDeletePopupComponent,
    ],
    providers: [
        IndustryService,
        IndustryPopupService,
        IndustryResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandIndustryModule {}
