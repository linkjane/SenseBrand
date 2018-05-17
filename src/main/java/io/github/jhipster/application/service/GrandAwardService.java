package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.GrandAward;
import io.github.jhipster.application.repository.GrandAwardRepository;
import io.github.jhipster.application.repository.search.GrandAwardSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing GrandAward.
 */
@Service
@Transactional
public class GrandAwardService {

    private final Logger log = LoggerFactory.getLogger(GrandAwardService.class);

    private final GrandAwardRepository grandAwardRepository;

    private final GrandAwardSearchRepository grandAwardSearchRepository;

    public GrandAwardService(GrandAwardRepository grandAwardRepository, GrandAwardSearchRepository grandAwardSearchRepository) {
        this.grandAwardRepository = grandAwardRepository;
        this.grandAwardSearchRepository = grandAwardSearchRepository;
    }

    /**
     * Save a grandAward.
     *
     * @param grandAward the entity to save
     * @return the persisted entity
     */
    public GrandAward save(GrandAward grandAward) {
        log.debug("Request to save GrandAward : {}", grandAward);
        GrandAward result = grandAwardRepository.save(grandAward);
        grandAwardSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the grandAwards.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GrandAward> findAll(Pageable pageable) {
        log.debug("Request to get all GrandAwards");
        return grandAwardRepository.findAll(pageable);
    }

    /**
     * Get one grandAward by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public GrandAward findOne(Long id) {
        log.debug("Request to get GrandAward : {}", id);
        return grandAwardRepository.findOne(id);
    }

    /**
     * Delete the grandAward by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GrandAward : {}", id);
        grandAwardRepository.delete(id);
        grandAwardSearchRepository.delete(id);
    }

    /**
     * Search for the grandAward corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GrandAward> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of GrandAwards for query {}", query);
        Page<GrandAward> result = grandAwardSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
