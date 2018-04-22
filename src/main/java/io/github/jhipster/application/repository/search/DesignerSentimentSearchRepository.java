package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.DesignerSentiment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DesignerSentiment entity.
 */
public interface DesignerSentimentSearchRepository extends ElasticsearchRepository<DesignerSentiment, Long> {
}
