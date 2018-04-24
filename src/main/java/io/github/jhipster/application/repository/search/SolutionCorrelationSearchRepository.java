package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.SolutionCorrelation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SolutionCorrelation entity.
 */
public interface SolutionCorrelationSearchRepository extends ElasticsearchRepository<SolutionCorrelation, Long> {
}
