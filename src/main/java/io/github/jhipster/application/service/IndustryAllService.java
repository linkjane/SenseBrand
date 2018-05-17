package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.IndustryAll;
import io.github.jhipster.application.repository.IndustryAllRepository;
import io.github.jhipster.application.repository.search.IndustryAllSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing IndustryAll.
 */
@Service
@Transactional
public class IndustryAllService {

    private final Logger log = LoggerFactory.getLogger(IndustryAllService.class);

    private final IndustryAllRepository industryAllRepository;

    private final IndustryAllSearchRepository industryAllSearchRepository;

    public IndustryAllService(IndustryAllRepository industryAllRepository, IndustryAllSearchRepository industryAllSearchRepository) {
        this.industryAllRepository = industryAllRepository;
        this.industryAllSearchRepository = industryAllSearchRepository;
    }

    /**
     * Save a industryAll.
     *
     * @param industryAll the entity to save
     * @return the persisted entity
     */
    public IndustryAll save(IndustryAll industryAll) {
        log.debug("Request to save IndustryAll : {}", industryAll);
        IndustryAll result = industryAllRepository.save(industryAll);
        industryAllSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the industryAlls.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IndustryAll> findAll(Pageable pageable) {
        log.debug("Request to get all IndustryAlls");
        return industryAllRepository.findAll(pageable);
    }

    /**
     * Get one industryAll by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public IndustryAll findOne(Long id) {
        log.debug("Request to get IndustryAll : {}", id);
        return industryAllRepository.findOne(id);
    }

    /**
     * Delete the industryAll by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete IndustryAll : {}", id);
        industryAllRepository.delete(id);
        industryAllSearchRepository.delete(id);
    }

    /**
     * Search for the industryAll corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IndustryAll> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of IndustryAlls for query {}", query);
        Page<IndustryAll> result = industryAllSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
