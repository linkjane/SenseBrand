package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.BrandSubDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BrandSubDetails entity.
 */
public interface BrandSubDetailsSearchRepository extends ElasticsearchRepository<BrandSubDetails, Long> {
}
