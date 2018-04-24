import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    SolutionCorrelationService,
    SolutionCorrelationPopupService,
    SolutionCorrelationComponent,
    SolutionCorrelationDetailComponent,
    SolutionCorrelationDialogComponent,
    SolutionCorrelationPopupComponent,
    SolutionCorrelationDeletePopupComponent,
    SolutionCorrelationDeleteDialogComponent,
    solutionCorrelationRoute,
    solutionCorrelationPopupRoute,
    SolutionCorrelationResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...solutionCorrelationRoute,
    ...solutionCorrelationPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SolutionCorrelationComponent,
        SolutionCorrelationDetailComponent,
        SolutionCorrelationDialogComponent,
        SolutionCorrelationDeleteDialogComponent,
        SolutionCorrelationPopupComponent,
        SolutionCorrelationDeletePopupComponent,
    ],
    entryComponents: [
        SolutionCorrelationComponent,
        SolutionCorrelationDialogComponent,
        SolutionCorrelationPopupComponent,
        SolutionCorrelationDeleteDialogComponent,
        SolutionCorrelationDeletePopupComponent,
    ],
    providers: [
        SolutionCorrelationService,
        SolutionCorrelationPopupService,
        SolutionCorrelationResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandSolutionCorrelationModule {}
