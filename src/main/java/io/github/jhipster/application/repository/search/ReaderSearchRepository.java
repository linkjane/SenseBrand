package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.Reader;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Reader entity.
 */
public interface ReaderSearchRepository extends ElasticsearchRepository<Reader, Long> {
}
