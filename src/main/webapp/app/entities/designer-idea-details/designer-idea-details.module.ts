import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    DesignerIdeaDetailsService,
    DesignerIdeaDetailsPopupService,
    DesignerIdeaDetailsComponent,
    DesignerIdeaDetailsDetailComponent,
    DesignerIdeaDetailsDialogComponent,
    DesignerIdeaDetailsPopupComponent,
    DesignerIdeaDetailsDeletePopupComponent,
    DesignerIdeaDetailsDeleteDialogComponent,
    designerIdeaDetailsRoute,
    designerIdeaDetailsPopupRoute,
    DesignerIdeaDetailsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...designerIdeaDetailsRoute,
    ...designerIdeaDetailsPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DesignerIdeaDetailsComponent,
        DesignerIdeaDetailsDetailComponent,
        DesignerIdeaDetailsDialogComponent,
        DesignerIdeaDetailsDeleteDialogComponent,
        DesignerIdeaDetailsPopupComponent,
        DesignerIdeaDetailsDeletePopupComponent,
    ],
    entryComponents: [
        DesignerIdeaDetailsComponent,
        DesignerIdeaDetailsDialogComponent,
        DesignerIdeaDetailsPopupComponent,
        DesignerIdeaDetailsDeleteDialogComponent,
        DesignerIdeaDetailsDeletePopupComponent,
    ],
    providers: [
        DesignerIdeaDetailsService,
        DesignerIdeaDetailsPopupService,
        DesignerIdeaDetailsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandDesignerIdeaDetailsModule {}
