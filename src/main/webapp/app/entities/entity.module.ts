import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SenseBrandDesignerModule } from './designer/designer.module';
import { SenseBrandDesignerShowModule } from './designer-show/designer-show.module';
import { SenseBrandDesignerShowImgModule } from './designer-show-img/designer-show-img.module';
import { SenseBrandDesignerSentimentModule } from './designer-sentiment/designer-sentiment.module';
import { SenseBrandDesignerAwardModule } from './designer-award/designer-award.module';
import { SenseBrandDesignerIdeaDetailsModule } from './designer-idea-details/designer-idea-details.module';
import { SenseBrandDesignerIdeaMediaModule } from './designer-idea-media/designer-idea-media.module';
import { SenseBrandOurSightModule } from './our-sight/our-sight.module';
import { SenseBrandSolutionModule } from './solution/solution.module';
import { SenseBrandSolutionDetailAnalysisModule } from './solution-detail-analysis/solution-detail-analysis.module';
import { SenseBrandSolutionDetailAnalysisImgModule } from './solution-detail-analysis-img/solution-detail-analysis-img.module';
import { SenseBrandSolutionDetailImgModule } from './solution-detail-img/solution-detail-img.module';
import { SenseBrandSolutionCorrelationModule } from './solution-correlation/solution-correlation.module';
import { SenseBrandIndustryAllModule } from './industry-all/industry-all.module';
import { SenseBrandIndustryFirstClassModule } from './industry-first-class/industry-first-class.module';
import { SenseBrandIndustrySecondClassModule } from './industry-second-class/industry-second-class.module';
import { SenseBrandBrandModule } from './brand/brand.module';
import { SenseBrandBrandSubModule } from './brand-sub/brand-sub.module';
import { SenseBrandBrandSubDetailsModule } from './brand-sub-details/brand-sub-details.module';
import { SenseBrandBrandRankModule } from './brand-rank/brand-rank.module';
import { SenseBrandBrandRegionModule } from './brand-region/brand-region.module';
import { SenseBrandIndustryModule } from './industry/industry.module';
import { SenseBrandIndustryTopModule } from './industry-top/industry-top.module';
import { SenseBrandIndustryTypeModule } from './industry-type/industry-type.module';
import { SenseBrandIndustryTypeNameModule } from './industry-type-name/industry-type-name.module';
import { SenseBrandQuestionModule } from './question/question.module';
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
        SenseBrandOurSightModule,
        SenseBrandSolutionModule,
        SenseBrandSolutionDetailAnalysisModule,
        SenseBrandSolutionDetailAnalysisImgModule,
        SenseBrandSolutionDetailImgModule,
        SenseBrandSolutionCorrelationModule,
        SenseBrandIndustryAllModule,
        SenseBrandIndustryFirstClassModule,
        SenseBrandIndustrySecondClassModule,
        SenseBrandBrandModule,
        SenseBrandBrandSubModule,
        SenseBrandBrandSubDetailsModule,
        SenseBrandBrandRankModule,
        SenseBrandBrandRegionModule,
        SenseBrandIndustryModule,
        SenseBrandIndustryTopModule,
        SenseBrandIndustryTypeModule,
        SenseBrandIndustryTypeNameModule,
        SenseBrandQuestionModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SenseBrandEntityModule {}
