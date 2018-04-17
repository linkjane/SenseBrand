import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    ReaderService,
    ReaderPopupService,
    ReaderComponent,
    ReaderDetailComponent,
    ReaderDialogComponent,
    ReaderPopupComponent,
    ReaderDeletePopupComponent,
    ReaderDeleteDialogComponent,
    readerRoute,
    readerPopupRoute,
} from './';

const ENTITY_STATES = [
    ...readerRoute,
    ...readerPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ReaderComponent,
        ReaderDetailComponent,
        ReaderDialogComponent,
        ReaderDeleteDialogComponent,
        ReaderPopupComponent,
        ReaderDeletePopupComponent,
    ],
    entryComponents: [
        ReaderComponent,
        ReaderDialogComponent,
        ReaderPopupComponent,
        ReaderDeleteDialogComponent,
        ReaderDeletePopupComponent,
    ],
    providers: [
        ReaderService,
        ReaderPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandReaderModule {}
