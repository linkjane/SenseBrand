import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    IndustrySecondClassService,
    IndustrySecondClassPopupService,
    IndustrySecondClassComponent,
    IndustrySecondClassDetailComponent,
    IndustrySecondClassDialogComponent,
    IndustrySecondClassPopupComponent,
    IndustrySecondClassDeletePopupComponent,
    IndustrySecondClassDeleteDialogComponent,
    industrySecondClassRoute,
    industrySecondClassPopupRoute,
    IndustrySecondClassResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...industrySecondClassRoute,
    ...industrySecondClassPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        IndustrySecondClassComponent,
        IndustrySecondClassDetailComponent,
        IndustrySecondClassDialogComponent,
        IndustrySecondClassDeleteDialogComponent,
        IndustrySecondClassPopupComponent,
        IndustrySecondClassDeletePopupComponent,
    ],
    entryComponents: [
        IndustrySecondClassComponent,
        IndustrySecondClassDialogComponent,
        IndustrySecondClassPopupComponent,
        IndustrySecondClassDeleteDialogComponent,
        IndustrySecondClassDeletePopupComponent,
    ],
    providers: [
        IndustrySecondClassService,
        IndustrySecondClassPopupService,
        IndustrySecondClassResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandIndustrySecondClassModule {}
