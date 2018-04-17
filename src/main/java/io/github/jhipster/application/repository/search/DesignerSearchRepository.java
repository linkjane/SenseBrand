package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Designer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Designer entity.
 */
public interface DesignerSearchRepository extends ElasticsearchRepository<Designer, Long> {
}
