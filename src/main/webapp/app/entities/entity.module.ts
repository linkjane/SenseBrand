import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SenseBrandOwnerModule } from './owner/owner.module';
import { SenseBrandCarModule } from './car/car.module';
import { SenseBrandDriverModule } from './driver/driver.module';
import { SenseBrandMyFileModule } from './my-file/my-file.module';
import { SenseBrandPersonModule } from './person/person.module';
import { SenseBrandReaderModule } from './reader/reader.module';
import { SenseBrandReaderOldModule } from './reader-old/reader-old.module';
import { SenseBrandTest1Module } from './test-1/test-1.module';
import { SenseBrandDesignerModule } from './designer/designer.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SenseBrandOwnerModule,
        SenseBrandCarModule,
        SenseBrandDriverModule,
        SenseBrandMyFileModule,
        SenseBrandPersonModule,
        SenseBrandReaderModule,
        SenseBrandReaderOldModule,
        SenseBrandTest1Module,
        SenseBrandDesignerModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandEntityModule {}
