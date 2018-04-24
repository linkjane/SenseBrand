package io.github.jhipster.application.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(io.github.jhipster.application.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Designer.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Designer.class.getName() + ".designerShowImgs", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Designer.class.getName() + ".designerSentiments", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Designer.class.getName() + ".designerAwards", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Designer.class.getName() + ".designerIdeaMedias", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.DesignerShow.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.DesignerShowImg.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.DesignerSentiment.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.DesignerAward.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.DesignerIdeaDetails.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.DesignerIdeaMedia.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.OurSight.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Solution.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Solution.class.getName() + ".solutionDetailImgs", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Solution.class.getName() + ".solutionDetailAnalyses", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Solution.class.getName() + ".solutionCorrelations", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.SolutionDetailAnalysis.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.SolutionDetailAnalysis.class.getName() + ".solutionDetailAnalysisImgs", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.SolutionDetailAnalysisImg.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.SolutionDetailImg.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.SolutionCorrelation.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.IndustryAll.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.IndustryAll.class.getName() + ".industryFirstClasses", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.IndustryFirstClass.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.IndustryFirstClass.class.getName() + ".industrySecondClasses", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.IndustrySecondClass.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.IndustrySecondClass.class.getName() + ".brands", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Brand.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Brand.class.getName() + ".brandSubs", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Brand.class.getName() + ".brandRanks", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Brand.class.getName() + ".brandRegions", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.BrandSub.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.BrandSub.class.getName() + ".brandSubDetails", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.BrandSubDetails.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.BrandRank.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.BrandRank.class.getName() + ".brands", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.BrandRegion.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.BrandRegion.class.getName() + ".brands", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Industry.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Industry.class.getName() + ".industryTops", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Industry.class.getName() + ".industryTypes", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.IndustryTop.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.IndustryType.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.IndustryType.class.getName() + ".industryTypeNames", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.IndustryTypeName.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Question.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
