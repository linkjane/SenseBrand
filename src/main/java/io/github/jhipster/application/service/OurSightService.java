package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.OurSight;
import io.github.jhipster.application.repository.OurSightRepository;
import io.github.jhipster.application.repository.search.OurSightSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OurSight.
 */
@Service
@Transactional
public class OurSightService {

    private final Logger log = LoggerFactory.getLogger(OurSightService.class);

    private final OurSightRepository ourSightRepository;

    private final OurSightSearchRepository ourSightSearchRepository;

    public OurSightService(OurSightRepository ourSightRepository, OurSightSearchRepository ourSightSearchRepository) {
        this.ourSightRepository = ourSightRepository;
        this.ourSightSearchRepository = ourSightSearchRepository;
    }

    /**
     * Save a ourSight.
     *
     * @param ourSight the entity to save
     * @return the persisted entity
     */
    public OurSight save(OurSight ourSight) {
        log.debug("Request to save OurSight : {}", ourSight);
        OurSight result = ourSightRepository.save(ourSight);
        ourSightSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the ourSights.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OurSight> findAll(Pageable pageable) {
        log.debug("Request to get all OurSights");
        return ourSightRepository.findAll(pageable);
    }

    /**
     * Get one ourSight by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public OurSight findOne(Long id) {
        log.debug("Request to get OurSight : {}", id);
        return ourSightRepository.findOne(id);
    }

    /**
     * Delete the ourSight by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OurSight : {}", id);
        ourSightRepository.delete(id);
        ourSightSearchRepository.delete(id);
    }

    /**
     * Search for the ourSight corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OurSight> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OurSights for query {}", query);
        Page<OurSight> result = ourSightSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
