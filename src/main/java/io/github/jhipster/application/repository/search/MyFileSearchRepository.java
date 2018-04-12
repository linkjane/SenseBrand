package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.MyFile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MyFile entity.
 */
public interface MyFileSearchRepository extends ElasticsearchRepository<MyFile, Long> {
}
