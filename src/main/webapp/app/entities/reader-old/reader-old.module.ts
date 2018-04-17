import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SenseBrandSharedModule } from '../../shared';
import {
    ReaderOldService,
    ReaderOldPopupService,
    ReaderOldComponent,
    ReaderOldDetailComponent,
    ReaderOldDialogComponent,
    ReaderOldPopupComponent,
    ReaderOldDeletePopupComponent,
    ReaderOldDeleteDialogComponent,
    readerOldRoute,
    readerOldPopupRoute,
} from './';

const ENTITY_STATES = [
    ...readerOldRoute,
    ...readerOldPopupRoute,
];

@NgModule({
    imports: [
        SenseBrandSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ReaderOldComponent,
        ReaderOldDetailComponent,
        ReaderOldDialogComponent,
        ReaderOldDeleteDialogComponent,
        ReaderOldPopupComponent,
        ReaderOldDeletePopupComponent,
    ],
    entryComponents: [
        ReaderOldComponent,
        ReaderOldDialogComponent,
        ReaderOldPopupComponent,
        ReaderOldDeleteDialogComponent,
        ReaderOldDeletePopupComponent,
    ],
    providers: [
        ReaderOldService,
        ReaderOldPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandReaderOldModule {}
