import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    IndustryTypeNameService,
    IndustryTypeNamePopupService,
    IndustryTypeNameComponent,
    IndustryTypeNameDetailComponent,
    IndustryTypeNameDialogComponent,
    IndustryTypeNamePopupComponent,
    IndustryTypeNameDeletePopupComponent,
    IndustryTypeNameDeleteDialogComponent,
    industryTypeNameRoute,
    industryTypeNamePopupRoute,
    IndustryTypeNameResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...industryTypeNameRoute,
    ...industryTypeNamePopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        IndustryTypeNameComponent,
        IndustryTypeNameDetailComponent,
        IndustryTypeNameDialogComponent,
        IndustryTypeNameDeleteDialogComponent,
        IndustryTypeNamePopupComponent,
        IndustryTypeNameDeletePopupComponent,
    ],
    entryComponents: [
        IndustryTypeNameComponent,
        IndustryTypeNameDialogComponent,
        IndustryTypeNamePopupComponent,
        IndustryTypeNameDeleteDialogComponent,
        IndustryTypeNameDeletePopupComponent,
    ],
    providers: [
        IndustryTypeNameService,
        IndustryTypeNamePopupService,
        IndustryTypeNameResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandIndustryTypeNameModule {}
