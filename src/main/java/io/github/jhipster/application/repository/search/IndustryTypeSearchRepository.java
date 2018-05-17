package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.IndustryType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the IndustryType entity.
 */
public interface IndustryTypeSearchRepository extends ElasticsearchRepository<IndustryType, Long> {
}
