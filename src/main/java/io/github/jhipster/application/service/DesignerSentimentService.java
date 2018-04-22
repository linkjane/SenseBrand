package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.DesignerSentiment;
import io.github.jhipster.application.repository.DesignerSentimentRepository;
import io.github.jhipster.application.repository.search.DesignerSentimentSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DesignerSentiment.
 */
@Service
@Transactional
public class DesignerSentimentService {

    private final Logger log = LoggerFactory.getLogger(DesignerSentimentService.class);

    private final DesignerSentimentRepository designerSentimentRepository;

    private final DesignerSentimentSearchRepository designerSentimentSearchRepository;

    public DesignerSentimentService(DesignerSentimentRepository designerSentimentRepository, DesignerSentimentSearchRepository designerSentimentSearchRepository) {
        this.designerSentimentRepository = designerSentimentRepository;
        this.designerSentimentSearchRepository = designerSentimentSearchRepository;
    }

    /**
     * Save a designerSentiment.
     *
     * @param designerSentiment the entity to save
     * @return the persisted entity
     */
    public DesignerSentiment save(DesignerSentiment designerSentiment) {
        log.debug("Request to save DesignerSentiment : {}", designerSentiment);
        DesignerSentiment result = designerSentimentRepository.save(designerSentiment);
        designerSentimentSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the designerSentiments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DesignerSentiment> findAll(Pageable pageable) {
        log.debug("Request to get all DesignerSentiments");
        return designerSentimentRepository.findAll(pageable);
    }

    /**
     * Get one designerSentiment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DesignerSentiment findOne(Long id) {
        log.debug("Request to get DesignerSentiment : {}", id);
        return designerSentimentRepository.findOne(id);
    }

    /**
     * Delete the designerSentiment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DesignerSentiment : {}", id);
        designerSentimentRepository.delete(id);
        designerSentimentSearchRepository.delete(id);
    }

    /**
     * Search for the designerSentiment corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DesignerSentiment> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DesignerSentiments for query {}", query);
        Page<DesignerSentiment> result = designerSentimentSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
