package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.SolutionDetailAnalysis;
import io.github.jhipster.application.repository.SolutionDetailAnalysisRepository;
import io.github.jhipster.application.repository.search.SolutionDetailAnalysisSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SolutionDetailAnalysis.
 */
@Service
@Transactional
public class SolutionDetailAnalysisService {

    private final Logger log = LoggerFactory.getLogger(SolutionDetailAnalysisService.class);

    private final SolutionDetailAnalysisRepository solutionDetailAnalysisRepository;

    private final SolutionDetailAnalysisSearchRepository solutionDetailAnalysisSearchRepository;

    public SolutionDetailAnalysisService(SolutionDetailAnalysisRepository solutionDetailAnalysisRepository, SolutionDetailAnalysisSearchRepository solutionDetailAnalysisSearchRepository) {
        this.solutionDetailAnalysisRepository = solutionDetailAnalysisRepository;
        this.solutionDetailAnalysisSearchRepository = solutionDetailAnalysisSearchRepository;
    }

    /**
     * Save a solutionDetailAnalysis.
     *
     * @param solutionDetailAnalysis the entity to save
     * @return the persisted entity
     */
    public SolutionDetailAnalysis save(SolutionDetailAnalysis solutionDetailAnalysis) {
        log.debug("Request to save SolutionDetailAnalysis : {}", solutionDetailAnalysis);
        SolutionDetailAnalysis result = solutionDetailAnalysisRepository.save(solutionDetailAnalysis);
        solutionDetailAnalysisSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the solutionDetailAnalyses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SolutionDetailAnalysis> findAll(Pageable pageable) {
        log.debug("Request to get all SolutionDetailAnalyses");
        return solutionDetailAnalysisRepository.findAll(pageable);
    }

    /**
     * Get one solutionDetailAnalysis by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SolutionDetailAnalysis findOne(Long id) {
        log.debug("Request to get SolutionDetailAnalysis : {}", id);
        return solutionDetailAnalysisRepository.findOne(id);
    }

    /**
     * Delete the solutionDetailAnalysis by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SolutionDetailAnalysis : {}", id);
        solutionDetailAnalysisRepository.delete(id);
        solutionDetailAnalysisSearchRepository.delete(id);
    }

    /**
     * Search for the solutionDetailAnalysis corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SolutionDetailAnalysis> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SolutionDetailAnalyses for query {}", query);
        Page<SolutionDetailAnalysis> result = solutionDetailAnalysisSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
