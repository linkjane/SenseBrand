import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    DesignerSentimentService,
    DesignerSentimentPopupService,
    DesignerSentimentComponent,
    DesignerSentimentDetailComponent,
    DesignerSentimentDialogComponent,
    DesignerSentimentPopupComponent,
    DesignerSentimentDeletePopupComponent,
    DesignerSentimentDeleteDialogComponent,
    designerSentimentRoute,
    designerSentimentPopupRoute,
    DesignerSentimentResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...designerSentimentRoute,
    ...designerSentimentPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DesignerSentimentComponent,
        DesignerSentimentDetailComponent,
        DesignerSentimentDialogComponent,
        DesignerSentimentDeleteDialogComponent,
        DesignerSentimentPopupComponent,
        DesignerSentimentDeletePopupComponent,
    ],
    entryComponents: [
        DesignerSentimentComponent,
        DesignerSentimentDialogComponent,
        DesignerSentimentPopupComponent,
        DesignerSentimentDeleteDialogComponent,
        DesignerSentimentDeletePopupComponent,
    ],
    providers: [
        DesignerSentimentService,
        DesignerSentimentPopupService,
        DesignerSentimentResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandDesignerSentimentModule {}
