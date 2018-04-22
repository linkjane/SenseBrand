import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SenseBrandDesignerModule } from './designer/designer.module';
import { SenseBrandDesignerShowModule } from './designer-show/designer-show.module';
import { SenseBrandDesignerShowImgModule } from './designer-show-img/designer-show-img.module';
import { SenseBrandDesignerSentimentModule } from './designer-sentiment/designer-sentiment.module';
import { SenseBrandDesignerAwardModule } from './designer-award/designer-award.module';
import { SenseBrandDesignerIdeaDetailsModule } from './designer-idea-details/designer-idea-details.module';
import { SenseBrandDesignerIdeaMediaModule } from './designer-idea-media/designer-idea-media.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SenseBrandDesignerModule,
        SenseBrandDesignerShowModule,
        SenseBrandDesignerShowImgModule,
        SenseBrandDesignerSentimentModule,
        SenseBrandDesignerAwardModule,
        SenseBrandDesignerIdeaDetailsModule,
        SenseBrandDesignerIdeaMediaModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandEntityModule {}
