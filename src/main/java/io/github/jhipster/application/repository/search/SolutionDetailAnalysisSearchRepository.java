package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.SolutionDetailAnalysis;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SolutionDetailAnalysis entity.
 */
public interface SolutionDetailAnalysisSearchRepository extends ElasticsearchRepository<SolutionDetailAnalysis, Long> {
}
