package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Solution;
import io.github.jhipster.application.repository.SolutionRepository;
import io.github.jhipster.application.repository.search.SolutionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Solution.
 */
@Service
@Transactional
public class SolutionService {

    private final Logger log = LoggerFactory.getLogger(SolutionService.class);

    private final SolutionRepository solutionRepository;

    private final SolutionSearchRepository solutionSearchRepository;

    public SolutionService(SolutionRepository solutionRepository, SolutionSearchRepository solutionSearchRepository) {
        this.solutionRepository = solutionRepository;
        this.solutionSearchRepository = solutionSearchRepository;
    }

    /**
     * Save a solution.
     *
     * @param solution the entity to save
     * @return the persisted entity
     */
    public Solution save(Solution solution) {
        log.debug("Request to save Solution : {}", solution);
        Solution result = solutionRepository.save(solution);
        solutionSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the solutions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Solution> findAll(Pageable pageable) {
        log.debug("Request to get all Solutions");
        return solutionRepository.findAll(pageable);
    }

    /**
     * Get one solution by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Solution findOne(Long id) {
        log.debug("Request to get Solution : {}", id);
        return solutionRepository.findOne(id);
    }

    /**
     * Delete the solution by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Solution : {}", id);
        solutionRepository.delete(id);
        solutionSearchRepository.delete(id);
    }

    /**
     * Search for the solution corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Solution> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Solutions for query {}", query);
        Page<Solution> result = solutionSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
