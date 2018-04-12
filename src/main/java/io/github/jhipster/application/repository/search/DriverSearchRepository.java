package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Driver;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Driver entity.
 */
public interface DriverSearchRepository extends ElasticsearchRepository<Driver, Long> {
}
