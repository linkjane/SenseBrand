import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    SolutionDetailImgService,
    SolutionDetailImgPopupService,
    SolutionDetailImgComponent,
    SolutionDetailImgDetailComponent,
    SolutionDetailImgDialogComponent,
    SolutionDetailImgPopupComponent,
    SolutionDetailImgDeletePopupComponent,
    SolutionDetailImgDeleteDialogComponent,
    solutionDetailImgRoute,
    solutionDetailImgPopupRoute,
    SolutionDetailImgResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...solutionDetailImgRoute,
    ...solutionDetailImgPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SolutionDetailImgComponent,
        SolutionDetailImgDetailComponent,
        SolutionDetailImgDialogComponent,
        SolutionDetailImgDeleteDialogComponent,
        SolutionDetailImgPopupComponent,
        SolutionDetailImgDeletePopupComponent,
    ],
    entryComponents: [
        SolutionDetailImgComponent,
        SolutionDetailImgDialogComponent,
        SolutionDetailImgPopupComponent,
        SolutionDetailImgDeleteDialogComponent,
        SolutionDetailImgDeletePopupComponent,
    ],
    providers: [
        SolutionDetailImgService,
        SolutionDetailImgPopupService,
        SolutionDetailImgResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandSolutionDetailImgModule {}
