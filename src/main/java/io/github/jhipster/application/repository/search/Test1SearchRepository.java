package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Test1;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Test1 entity.
 */
public interface Test1SearchRepository extends ElasticsearchRepository<Test1, Long> {
}
