import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    SolutionDetailAnalysisService,
    SolutionDetailAnalysisPopupService,
    SolutionDetailAnalysisComponent,
    SolutionDetailAnalysisDetailComponent,
    SolutionDetailAnalysisDialogComponent,
    SolutionDetailAnalysisPopupComponent,
    SolutionDetailAnalysisDeletePopupComponent,
    SolutionDetailAnalysisDeleteDialogComponent,
    solutionDetailAnalysisRoute,
    solutionDetailAnalysisPopupRoute,
    SolutionDetailAnalysisResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...solutionDetailAnalysisRoute,
    ...solutionDetailAnalysisPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SolutionDetailAnalysisComponent,
        SolutionDetailAnalysisDetailComponent,
        SolutionDetailAnalysisDialogComponent,
        SolutionDetailAnalysisDeleteDialogComponent,
        SolutionDetailAnalysisPopupComponent,
        SolutionDetailAnalysisDeletePopupComponent,
    ],
    entryComponents: [
        SolutionDetailAnalysisComponent,
        SolutionDetailAnalysisDialogComponent,
        SolutionDetailAnalysisPopupComponent,
        SolutionDetailAnalysisDeleteDialogComponent,
        SolutionDetailAnalysisDeletePopupComponent,
    ],
    providers: [
        SolutionDetailAnalysisService,
        SolutionDetailAnalysisPopupService,
        SolutionDetailAnalysisResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandSolutionDetailAnalysisModule {}
