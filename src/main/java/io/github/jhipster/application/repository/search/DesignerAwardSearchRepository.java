package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.DesignerAward;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DesignerAward entity.
 */
public interface DesignerAwardSearchRepository extends ElasticsearchRepository<DesignerAward, Long> {
}
