import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    DesignerShowService,
    DesignerShowPopupService,
    DesignerShowComponent,
    DesignerShowDetailComponent,
    DesignerShowDialogComponent,
    DesignerShowPopupComponent,
    DesignerShowDeletePopupComponent,
    DesignerShowDeleteDialogComponent,
    designerShowRoute,
    designerShowPopupRoute,
    DesignerShowResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...designerShowRoute,
    ...designerShowPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DesignerShowComponent,
        DesignerShowDetailComponent,
        DesignerShowDialogComponent,
        DesignerShowDeleteDialogComponent,
        DesignerShowPopupComponent,
        DesignerShowDeletePopupComponent,
    ],
    entryComponents: [
        DesignerShowComponent,
        DesignerShowDialogComponent,
        DesignerShowPopupComponent,
        DesignerShowDeleteDialogComponent,
        DesignerShowDeletePopupComponent,
    ],
    providers: [
        DesignerShowService,
        DesignerShowPopupService,
        DesignerShowResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandDesignerShowModule {}
