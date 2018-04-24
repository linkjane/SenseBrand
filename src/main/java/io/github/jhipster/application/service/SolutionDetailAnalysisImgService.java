package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.SolutionDetailAnalysisImg;
import io.github.jhipster.application.repository.SolutionDetailAnalysisImgRepository;
import io.github.jhipster.application.repository.search.SolutionDetailAnalysisImgSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SolutionDetailAnalysisImg.
 */
@Service
@Transactional
public class SolutionDetailAnalysisImgService {

    private final Logger log = LoggerFactory.getLogger(SolutionDetailAnalysisImgService.class);

    private final SolutionDetailAnalysisImgRepository solutionDetailAnalysisImgRepository;

    private final SolutionDetailAnalysisImgSearchRepository solutionDetailAnalysisImgSearchRepository;

    public SolutionDetailAnalysisImgService(SolutionDetailAnalysisImgRepository solutionDetailAnalysisImgRepository, SolutionDetailAnalysisImgSearchRepository solutionDetailAnalysisImgSearchRepository) {
        this.solutionDetailAnalysisImgRepository = solutionDetailAnalysisImgRepository;
        this.solutionDetailAnalysisImgSearchRepository = solutionDetailAnalysisImgSearchRepository;
    }

    /**
     * Save a solutionDetailAnalysisImg.
     *
     * @param solutionDetailAnalysisImg the entity to save
     * @return the persisted entity
     */
    public SolutionDetailAnalysisImg save(SolutionDetailAnalysisImg solutionDetailAnalysisImg) {
        log.debug("Request to save SolutionDetailAnalysisImg : {}", solutionDetailAnalysisImg);
        SolutionDetailAnalysisImg result = solutionDetailAnalysisImgRepository.save(solutionDetailAnalysisImg);
        solutionDetailAnalysisImgSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the solutionDetailAnalysisImgs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SolutionDetailAnalysisImg> findAll(Pageable pageable) {
        log.debug("Request to get all SolutionDetailAnalysisImgs");
        return solutionDetailAnalysisImgRepository.findAll(pageable);
    }

    /**
     * Get one solutionDetailAnalysisImg by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SolutionDetailAnalysisImg findOne(Long id) {
        log.debug("Request to get SolutionDetailAnalysisImg : {}", id);
        return solutionDetailAnalysisImgRepository.findOne(id);
    }

    /**
     * Delete the solutionDetailAnalysisImg by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SolutionDetailAnalysisImg : {}", id);
        solutionDetailAnalysisImgRepository.delete(id);
        solutionDetailAnalysisImgSearchRepository.delete(id);
    }

    /**
     * Search for the solutionDetailAnalysisImg corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SolutionDetailAnalysisImg> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SolutionDetailAnalysisImgs for query {}", query);
        Page<SolutionDetailAnalysisImg> result = solutionDetailAnalysisImgSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
