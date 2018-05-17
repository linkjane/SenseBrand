import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    IndustryFirstClassService,
    IndustryFirstClassPopupService,
    IndustryFirstClassComponent,
    IndustryFirstClassDetailComponent,
    IndustryFirstClassDialogComponent,
    IndustryFirstClassPopupComponent,
    IndustryFirstClassDeletePopupComponent,
    IndustryFirstClassDeleteDialogComponent,
    industryFirstClassRoute,
    industryFirstClassPopupRoute,
    IndustryFirstClassResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...industryFirstClassRoute,
    ...industryFirstClassPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        IndustryFirstClassComponent,
        IndustryFirstClassDetailComponent,
        IndustryFirstClassDialogComponent,
        IndustryFirstClassDeleteDialogComponent,
        IndustryFirstClassPopupComponent,
        IndustryFirstClassDeletePopupComponent,
    ],
    entryComponents: [
        IndustryFirstClassComponent,
        IndustryFirstClassDialogComponent,
        IndustryFirstClassPopupComponent,
        IndustryFirstClassDeleteDialogComponent,
        IndustryFirstClassDeletePopupComponent,
    ],
    providers: [
        IndustryFirstClassService,
        IndustryFirstClassPopupService,
        IndustryFirstClassResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandIndustryFirstClassModule {}
