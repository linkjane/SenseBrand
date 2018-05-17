import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    OurSightService,
    OurSightPopupService,
    OurSightComponent,
    OurSightDetailComponent,
    OurSightDialogComponent,
    OurSightPopupComponent,
    OurSightDeletePopupComponent,
    OurSightDeleteDialogComponent,
    ourSightRoute,
    ourSightPopupRoute,
    OurSightResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...ourSightRoute,
    ...ourSightPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        OurSightComponent,
        OurSightDetailComponent,
        OurSightDialogComponent,
        OurSightDeleteDialogComponent,
        OurSightPopupComponent,
        OurSightDeletePopupComponent,
    ],
    entryComponents: [
        OurSightComponent,
        OurSightDialogComponent,
        OurSightPopupComponent,
        OurSightDeleteDialogComponent,
        OurSightDeletePopupComponent,
    ],
    providers: [
        OurSightService,
        OurSightPopupService,
        OurSightResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandOurSightModule {}
