import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    Test1Service,
    Test1PopupService,
    Test1Component,
    Test1DetailComponent,
    Test1DialogComponent,
    Test1PopupComponent,
    Test1DeletePopupComponent,
    Test1DeleteDialogComponent,
    test1Route,
    test1PopupRoute,
} from './';

const ENTITY_STATES = [
    ...test1Route,
    ...test1PopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        Test1Component,
        Test1DetailComponent,
        Test1DialogComponent,
        Test1DeleteDialogComponent,
        Test1PopupComponent,
        Test1DeletePopupComponent,
    ],
    entryComponents: [
        Test1Component,
        Test1DialogComponent,
        Test1PopupComponent,
        Test1DeleteDialogComponent,
        Test1DeletePopupComponent,
    ],
    providers: [
        Test1Service,
        Test1PopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandTest1Module {}
