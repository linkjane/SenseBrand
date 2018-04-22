import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    DesignerIdeaMediaService,
    DesignerIdeaMediaPopupService,
    DesignerIdeaMediaComponent,
    DesignerIdeaMediaDetailComponent,
    DesignerIdeaMediaDialogComponent,
    DesignerIdeaMediaPopupComponent,
    DesignerIdeaMediaDeletePopupComponent,
    DesignerIdeaMediaDeleteDialogComponent,
    designerIdeaMediaRoute,
    designerIdeaMediaPopupRoute,
    DesignerIdeaMediaResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...designerIdeaMediaRoute,
    ...designerIdeaMediaPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DesignerIdeaMediaComponent,
        DesignerIdeaMediaDetailComponent,
        DesignerIdeaMediaDialogComponent,
        DesignerIdeaMediaDeleteDialogComponent,
        DesignerIdeaMediaPopupComponent,
        DesignerIdeaMediaDeletePopupComponent,
    ],
    entryComponents: [
        DesignerIdeaMediaComponent,
        DesignerIdeaMediaDialogComponent,
        DesignerIdeaMediaPopupComponent,
        DesignerIdeaMediaDeleteDialogComponent,
        DesignerIdeaMediaDeletePopupComponent,
    ],
    providers: [
        DesignerIdeaMediaService,
        DesignerIdeaMediaPopupService,
        DesignerIdeaMediaResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandDesignerIdeaMediaModule {}
