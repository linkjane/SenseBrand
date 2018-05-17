package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.SolutionCorrelation;
import io.github.jhipster.application.repository.SolutionCorrelationRepository;
import io.github.jhipster.application.repository.search.SolutionCorrelationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SolutionCorrelation.
 */
@Service
@Transactional
public class SolutionCorrelationService {

    private final Logger log = LoggerFactory.getLogger(SolutionCorrelationService.class);

    private final SolutionCorrelationRepository solutionCorrelationRepository;

    private final SolutionCorrelationSearchRepository solutionCorrelationSearchRepository;

    public SolutionCorrelationService(SolutionCorrelationRepository solutionCorrelationRepository, SolutionCorrelationSearchRepository solutionCorrelationSearchRepository) {
        this.solutionCorrelationRepository = solutionCorrelationRepository;
        this.solutionCorrelationSearchRepository = solutionCorrelationSearchRepository;
    }

    /**
     * Save a solutionCorrelation.
     *
     * @param solutionCorrelation the entity to save
     * @return the persisted entity
     */
    public SolutionCorrelation save(SolutionCorrelation solutionCorrelation) {
        log.debug("Request to save SolutionCorrelation : {}", solutionCorrelation);
        SolutionCorrelation result = solutionCorrelationRepository.save(solutionCorrelation);
        solutionCorrelationSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the solutionCorrelations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SolutionCorrelation> findAll(Pageable pageable) {
        log.debug("Request to get all SolutionCorrelations");
        return solutionCorrelationRepository.findAll(pageable);
    }

    /**
     * Get one solutionCorrelation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SolutionCorrelation findOne(Long id) {
        log.debug("Request to get SolutionCorrelation : {}", id);
        return solutionCorrelationRepository.findOne(id);
    }

    /**
     * Delete the solutionCorrelation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SolutionCorrelation : {}", id);
        solutionCorrelationRepository.delete(id);
        solutionCorrelationSearchRepository.delete(id);
    }

    /**
     * Search for the solutionCorrelation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SolutionCorrelation> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SolutionCorrelations for query {}", query);
        Page<SolutionCorrelation> result = solutionCorrelationSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
