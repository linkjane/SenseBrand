package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.DesignerIdeaMedia;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DesignerIdeaMedia entity.
 */
public interface DesignerIdeaMediaSearchRepository extends ElasticsearchRepository<DesignerIdeaMedia, Long> {
}
