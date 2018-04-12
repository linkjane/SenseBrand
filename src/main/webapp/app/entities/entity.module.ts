import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SenseBrandOwnerModule } from './owner/owner.module';
import { SenseBrandCarModule } from './car/car.module';
import { SenseBrandDriverModule } from './driver/driver.module';
import { SenseBrandMyFileModule } from './my-file/my-file.module';
import { SenseBrandPersonModule } from './person/person.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SenseBrandOwnerModule,
        SenseBrandCarModule,
        SenseBrandDriverModule,
        SenseBrandMyFileModule,
        SenseBrandPersonModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandEntityModule {}
