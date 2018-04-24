package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Solution;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Solution entity.
 */
public interface SolutionSearchRepository extends ElasticsearchRepository<Solution, Long> {
}
