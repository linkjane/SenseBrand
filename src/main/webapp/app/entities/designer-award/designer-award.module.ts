import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    DesignerAwardService,
    DesignerAwardPopupService,
    DesignerAwardComponent,
    DesignerAwardDetailComponent,
    DesignerAwardDialogComponent,
    DesignerAwardPopupComponent,
    DesignerAwardDeletePopupComponent,
    DesignerAwardDeleteDialogComponent,
    designerAwardRoute,
    designerAwardPopupRoute,
    DesignerAwardResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...designerAwardRoute,
    ...designerAwardPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DesignerAwardComponent,
        DesignerAwardDetailComponent,
        DesignerAwardDialogComponent,
        DesignerAwardDeleteDialogComponent,
        DesignerAwardPopupComponent,
        DesignerAwardDeletePopupComponent,
    ],
    entryComponents: [
        DesignerAwardComponent,
        DesignerAwardDialogComponent,
        DesignerAwardPopupComponent,
        DesignerAwardDeleteDialogComponent,
        DesignerAwardDeletePopupComponent,
    ],
    providers: [
        DesignerAwardService,
        DesignerAwardPopupService,
        DesignerAwardResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandDesignerAwardModule {}
