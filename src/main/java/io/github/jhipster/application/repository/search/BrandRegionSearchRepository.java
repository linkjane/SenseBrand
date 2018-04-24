package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.BrandRegion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BrandRegion entity.
 */
public interface BrandRegionSearchRepository extends ElasticsearchRepository<BrandRegion, Long> {
}
