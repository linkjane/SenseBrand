package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.IndustryAll;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the IndustryAll entity.
 */
public interface IndustryAllSearchRepository extends ElasticsearchRepository<IndustryAll, Long> {
}
