package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.SolutionDetailImg;
import io.github.jhipster.application.repository.SolutionDetailImgRepository;
import io.github.jhipster.application.repository.search.SolutionDetailImgSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SolutionDetailImg.
 */
@Service
@Transactional
public class SolutionDetailImgService {

    private final Logger log = LoggerFactory.getLogger(SolutionDetailImgService.class);

    private final SolutionDetailImgRepository solutionDetailImgRepository;

    private final SolutionDetailImgSearchRepository solutionDetailImgSearchRepository;

    public SolutionDetailImgService(SolutionDetailImgRepository solutionDetailImgRepository, SolutionDetailImgSearchRepository solutionDetailImgSearchRepository) {
        this.solutionDetailImgRepository = solutionDetailImgRepository;
        this.solutionDetailImgSearchRepository = solutionDetailImgSearchRepository;
    }

    /**
     * Save a solutionDetailImg.
     *
     * @param solutionDetailImg the entity to save
     * @return the persisted entity
     */
    public SolutionDetailImg save(SolutionDetailImg solutionDetailImg) {
        log.debug("Request to save SolutionDetailImg : {}", solutionDetailImg);
        SolutionDetailImg result = solutionDetailImgRepository.save(solutionDetailImg);
        solutionDetailImgSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the solutionDetailImgs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SolutionDetailImg> findAll(Pageable pageable) {
        log.debug("Request to get all SolutionDetailImgs");
        return solutionDetailImgRepository.findAll(pageable);
    }

    /**
     * Get one solutionDetailImg by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public SolutionDetailImg findOne(Long id) {
        log.debug("Request to get SolutionDetailImg : {}", id);
        return solutionDetailImgRepository.findOne(id);
    }

    /**
     * Delete the solutionDetailImg by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SolutionDetailImg : {}", id);
        solutionDetailImgRepository.delete(id);
        solutionDetailImgSearchRepository.delete(id);
    }

    /**
     * Search for the solutionDetailImg corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SolutionDetailImg> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SolutionDetailImgs for query {}", query);
        Page<SolutionDetailImg> result = solutionDetailImgSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
