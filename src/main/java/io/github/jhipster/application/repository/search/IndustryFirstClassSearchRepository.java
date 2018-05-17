package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.IndustryFirstClass;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the IndustryFirstClass entity.
 */
public interface IndustryFirstClassSearchRepository extends ElasticsearchRepository<IndustryFirstClass, Long> {
}
