package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.DesignerShow;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DesignerShow entity.
 */
public interface DesignerShowSearchRepository extends ElasticsearchRepository<DesignerShow, Long> {
}
