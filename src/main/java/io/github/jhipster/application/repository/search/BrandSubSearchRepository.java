package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.BrandSub;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BrandSub entity.
 */
public interface BrandSubSearchRepository extends ElasticsearchRepository<BrandSub, Long> {
}
