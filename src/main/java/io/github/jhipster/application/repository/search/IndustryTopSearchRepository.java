package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.IndustryTop;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the IndustryTop entity.
 */
public interface IndustryTopSearchRepository extends ElasticsearchRepository<IndustryTop, Long> {
}
