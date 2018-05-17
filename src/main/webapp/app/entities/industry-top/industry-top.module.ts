import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    IndustryTopService,
    IndustryTopPopupService,
    IndustryTopComponent,
    IndustryTopDetailComponent,
    IndustryTopDialogComponent,
    IndustryTopPopupComponent,
    IndustryTopDeletePopupComponent,
    IndustryTopDeleteDialogComponent,
    industryTopRoute,
    industryTopPopupRoute,
    IndustryTopResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...industryTopRoute,
    ...industryTopPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        IndustryTopComponent,
        IndustryTopDetailComponent,
        IndustryTopDialogComponent,
        IndustryTopDeleteDialogComponent,
        IndustryTopPopupComponent,
        IndustryTopDeletePopupComponent,
    ],
    entryComponents: [
        IndustryTopComponent,
        IndustryTopDialogComponent,
        IndustryTopPopupComponent,
        IndustryTopDeleteDialogComponent,
        IndustryTopDeletePopupComponent,
    ],
    providers: [
        IndustryTopService,
        IndustryTopPopupService,
        IndustryTopResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandIndustryTopModule {}
