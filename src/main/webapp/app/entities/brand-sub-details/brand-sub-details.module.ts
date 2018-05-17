import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    BrandSubDetailsService,
    BrandSubDetailsPopupService,
    BrandSubDetailsComponent,
    BrandSubDetailsDetailComponent,
    BrandSubDetailsDialogComponent,
    BrandSubDetailsPopupComponent,
    BrandSubDetailsDeletePopupComponent,
    BrandSubDetailsDeleteDialogComponent,
    brandSubDetailsRoute,
    brandSubDetailsPopupRoute,
    BrandSubDetailsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...brandSubDetailsRoute,
    ...brandSubDetailsPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BrandSubDetailsComponent,
        BrandSubDetailsDetailComponent,
        BrandSubDetailsDialogComponent,
        BrandSubDetailsDeleteDialogComponent,
        BrandSubDetailsPopupComponent,
        BrandSubDetailsDeletePopupComponent,
    ],
    entryComponents: [
        BrandSubDetailsComponent,
        BrandSubDetailsDialogComponent,
        BrandSubDetailsPopupComponent,
        BrandSubDetailsDeleteDialogComponent,
        BrandSubDetailsDeletePopupComponent,
    ],
    providers: [
        BrandSubDetailsService,
        BrandSubDetailsPopupService,
        BrandSubDetailsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandBrandSubDetailsModule {}
