package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.IndustrySecondClass;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the IndustrySecondClass entity.
 */
public interface IndustrySecondClassSearchRepository extends ElasticsearchRepository<IndustrySecondClass, Long> {
}
