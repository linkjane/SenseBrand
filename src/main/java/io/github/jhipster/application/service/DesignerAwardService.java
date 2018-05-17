package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.DesignerAward;
import io.github.jhipster.application.repository.DesignerAwardRepository;
import io.github.jhipster.application.repository.search.DesignerAwardSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DesignerAward.
 */
@Service
@Transactional
public class DesignerAwardService {

    private final Logger log = LoggerFactory.getLogger(DesignerAwardService.class);

    private final DesignerAwardRepository designerAwardRepository;

    private final DesignerAwardSearchRepository designerAwardSearchRepository;

    public DesignerAwardService(DesignerAwardRepository designerAwardRepository, DesignerAwardSearchRepository designerAwardSearchRepository) {
        this.designerAwardRepository = designerAwardRepository;
        this.designerAwardSearchRepository = designerAwardSearchRepository;
    }

    /**
     * Save a designerAward.
     *
     * @param designerAward the entity to save
     * @return the persisted entity
     */
    public DesignerAward save(DesignerAward designerAward) {
        log.debug("Request to save DesignerAward : {}", designerAward);
        DesignerAward result = designerAwardRepository.save(designerAward);
        designerAwardSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the designerAwards.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DesignerAward> findAll(Pageable pageable) {
        log.debug("Request to get all DesignerAwards");
        return designerAwardRepository.findAll(pageable);
    }

    /**
     * Get one designerAward by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DesignerAward findOne(Long id) {
        log.debug("Request to get DesignerAward : {}", id);
        return designerAwardRepository.findOne(id);
    }

    /**
     * Delete the designerAward by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DesignerAward : {}", id);
        designerAwardRepository.delete(id);
        designerAwardSearchRepository.delete(id);
    }

    /**
     * Search for the designerAward corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DesignerAward> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DesignerAwards for query {}", query);
        Page<DesignerAward> result = designerAwardSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
