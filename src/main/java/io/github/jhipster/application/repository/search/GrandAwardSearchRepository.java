package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.GrandAward;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the GrandAward entity.
 */
public interface GrandAwardSearchRepository extends ElasticsearchRepository<GrandAward, Long> {
}
