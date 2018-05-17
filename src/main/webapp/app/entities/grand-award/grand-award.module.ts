import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    GrandAwardService,
    GrandAwardPopupService,
    GrandAwardComponent,
    GrandAwardDetailComponent,
    GrandAwardDialogComponent,
    GrandAwardPopupComponent,
    GrandAwardDeletePopupComponent,
    GrandAwardDeleteDialogComponent,
    grandAwardRoute,
    grandAwardPopupRoute,
    GrandAwardResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...grandAwardRoute,
    ...grandAwardPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        GrandAwardComponent,
        GrandAwardDetailComponent,
        GrandAwardDialogComponent,
        GrandAwardDeleteDialogComponent,
        GrandAwardPopupComponent,
        GrandAwardDeletePopupComponent,
    ],
    entryComponents: [
        GrandAwardComponent,
        GrandAwardDialogComponent,
        GrandAwardPopupComponent,
        GrandAwardDeleteDialogComponent,
        GrandAwardDeletePopupComponent,
    ],
    providers: [
        GrandAwardService,
        GrandAwardPopupService,
        GrandAwardResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandGrandAwardModule {}
