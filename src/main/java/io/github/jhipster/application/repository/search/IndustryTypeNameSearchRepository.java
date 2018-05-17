package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.IndustryTypeName;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the IndustryTypeName entity.
 */
public interface IndustryTypeNameSearchRepository extends ElasticsearchRepository<IndustryTypeName, Long> {
}
