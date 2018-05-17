import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    SolutionDetailAnalysisImgService,
    SolutionDetailAnalysisImgPopupService,
    SolutionDetailAnalysisImgComponent,
    SolutionDetailAnalysisImgDetailComponent,
    SolutionDetailAnalysisImgDialogComponent,
    SolutionDetailAnalysisImgPopupComponent,
    SolutionDetailAnalysisImgDeletePopupComponent,
    SolutionDetailAnalysisImgDeleteDialogComponent,
    solutionDetailAnalysisImgRoute,
    solutionDetailAnalysisImgPopupRoute,
    SolutionDetailAnalysisImgResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...solutionDetailAnalysisImgRoute,
    ...solutionDetailAnalysisImgPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SolutionDetailAnalysisImgComponent,
        SolutionDetailAnalysisImgDetailComponent,
        SolutionDetailAnalysisImgDialogComponent,
        SolutionDetailAnalysisImgDeleteDialogComponent,
        SolutionDetailAnalysisImgPopupComponent,
        SolutionDetailAnalysisImgDeletePopupComponent,
    ],
    entryComponents: [
        SolutionDetailAnalysisImgComponent,
        SolutionDetailAnalysisImgDialogComponent,
        SolutionDetailAnalysisImgPopupComponent,
        SolutionDetailAnalysisImgDeleteDialogComponent,
        SolutionDetailAnalysisImgDeletePopupComponent,
    ],
    providers: [
        SolutionDetailAnalysisImgService,
        SolutionDetailAnalysisImgPopupService,
        SolutionDetailAnalysisImgResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandSolutionDetailAnalysisImgModule {}
