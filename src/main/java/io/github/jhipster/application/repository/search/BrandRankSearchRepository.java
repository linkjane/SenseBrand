package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.BrandRank;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BrandRank entity.
 */
public interface BrandRankSearchRepository extends ElasticsearchRepository<BrandRank, Long> {
}
