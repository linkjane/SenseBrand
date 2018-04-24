package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Industry;
import io.github.jhipster.application.repository.IndustryRepository;
import io.github.jhipster.application.repository.search.IndustrySearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Industry.
 */
@Service
@Transactional
public class IndustryService {

    private final Logger log = LoggerFactory.getLogger(IndustryService.class);

    private final IndustryRepository industryRepository;

    private final IndustrySearchRepository industrySearchRepository;

    public IndustryService(IndustryRepository industryRepository, IndustrySearchRepository industrySearchRepository) {
        this.industryRepository = industryRepository;
        this.industrySearchRepository = industrySearchRepository;
    }

    /**
     * Save a industry.
     *
     * @param industry the entity to save
     * @return the persisted entity
     */
    public Industry save(Industry industry) {
        log.debug("Request to save Industry : {}", industry);
        Industry result = industryRepository.save(industry);
        industrySearchRepository.save(result);
        return result;
    }

    /**
     * Get all the industries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Industry> findAll(Pageable pageable) {
        log.debug("Request to get all Industries");
        return industryRepository.findAll(pageable);
    }

    /**
     * Get one industry by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Industry findOne(Long id) {
        log.debug("Request to get Industry : {}", id);
        return industryRepository.findOne(id);
    }

    /**
     * Delete the industry by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Industry : {}", id);
        industryRepository.delete(id);
        industrySearchRepository.delete(id);
    }

    /**
     * Search for the industry corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Industry> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Industries for query {}", query);
        Page<Industry> result = industrySearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
