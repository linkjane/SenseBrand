import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    DesignerService,
    DesignerPopupService,
    DesignerComponent,
    DesignerDetailComponent,
    DesignerDialogComponent,
    DesignerPopupComponent,
    DesignerDeletePopupComponent,
    DesignerDeleteDialogComponent,
    designerRoute,
    designerPopupRoute,
    DesignerResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...designerRoute,
    ...designerPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DesignerComponent,
        DesignerDetailComponent,
        DesignerDialogComponent,
        DesignerDeleteDialogComponent,
        DesignerPopupComponent,
        DesignerDeletePopupComponent,
    ],
    entryComponents: [
        DesignerComponent,
        DesignerDialogComponent,
        DesignerPopupComponent,
        DesignerDeleteDialogComponent,
        DesignerDeletePopupComponent,
    ],
    providers: [
        DesignerService,
        DesignerPopupService,
        DesignerResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandDesignerModule {}
