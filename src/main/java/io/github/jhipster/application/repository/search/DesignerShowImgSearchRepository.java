package io.github.jhipster.application.repository.search;

import io.github.jhipster.application.domain.DesignerShowImg;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DesignerShowImg entity.
 */
public interface DesignerShowImgSearchRepository extends ElasticsearchRepository<DesignerShowImg, Long> {
}
