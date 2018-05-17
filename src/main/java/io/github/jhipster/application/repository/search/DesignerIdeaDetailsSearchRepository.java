package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.DesignerIdeaDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DesignerIdeaDetails entity.
 */
public interface DesignerIdeaDetailsSearchRepository extends ElasticsearchRepository<DesignerIdeaDetails, Long> {
}
