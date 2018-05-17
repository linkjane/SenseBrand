import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    DesignerShowImgService,
    DesignerShowImgPopupService,
    DesignerShowImgComponent,
    DesignerShowImgDetailComponent,
    DesignerShowImgDialogComponent,
    DesignerShowImgPopupComponent,
    DesignerShowImgDeletePopupComponent,
    DesignerShowImgDeleteDialogComponent,
    designerShowImgRoute,
    designerShowImgPopupRoute,
    DesignerShowImgResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...designerShowImgRoute,
    ...designerShowImgPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DesignerShowImgComponent,
        DesignerShowImgDetailComponent,
        DesignerShowImgDialogComponent,
        DesignerShowImgDeleteDialogComponent,
        DesignerShowImgPopupComponent,
        DesignerShowImgDeletePopupComponent,
    ],
    entryComponents: [
        DesignerShowImgComponent,
        DesignerShowImgDialogComponent,
        DesignerShowImgPopupComponent,
        DesignerShowImgDeleteDialogComponent,
        DesignerShowImgDeletePopupComponent,
    ],
    providers: [
        DesignerShowImgService,
        DesignerShowImgPopupService,
        DesignerShowImgResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandDesignerShowImgModule {}
