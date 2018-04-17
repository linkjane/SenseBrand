package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.ReaderOld;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ReaderOld entity.
 */
public interface ReaderOldSearchRepository extends ElasticsearchRepository<ReaderOld, Long> {
}
