package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.OurSight;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OurSight entity.
 */
public interface OurSightSearchRepository extends ElasticsearchRepository<OurSight, Long> {
}
