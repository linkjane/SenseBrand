package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Owner;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Owner entity.
 */
public interface OwnerSearchRepository extends ElasticsearchRepository<Owner, Long> {
}
