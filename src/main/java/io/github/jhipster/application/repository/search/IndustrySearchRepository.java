package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Industry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Industry entity.
 */
public interface IndustrySearchRepository extends ElasticsearchRepository<Industry, Long> {
}
