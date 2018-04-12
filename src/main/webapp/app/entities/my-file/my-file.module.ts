import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    MyFileService,
    MyFilePopupService,
    MyFileComponent,
    MyFileDetailComponent,
    MyFileDialogComponent,
    MyFilePopupComponent,
    MyFileDeletePopupComponent,
    MyFileDeleteDialogComponent,
    myFileRoute,
    myFilePopupRoute,
    MyFileResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...myFileRoute,
    ...myFilePopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MyFileComponent,
        MyFileDetailComponent,
        MyFileDialogComponent,
        MyFileDeleteDialogComponent,
        MyFilePopupComponent,
        MyFileDeletePopupComponent,
    ],
    entryComponents: [
        MyFileComponent,
        MyFileDialogComponent,
        MyFilePopupComponent,
        MyFileDeleteDialogComponent,
        MyFileDeletePopupComponent,
    ],
    providers: [
        MyFileService,
        MyFilePopupService,
        MyFileResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandMyFileModule {}
