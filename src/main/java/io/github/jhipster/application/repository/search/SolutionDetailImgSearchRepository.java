package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.SolutionDetailImg;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SolutionDetailImg entity.
 */
public interface SolutionDetailImgSearchRepository extends ElasticsearchRepository<SolutionDetailImg, Long> {
}
