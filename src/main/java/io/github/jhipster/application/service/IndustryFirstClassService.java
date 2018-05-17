package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.IndustryFirstClass;
import io.github.jhipster.application.repository.IndustryFirstClassRepository;
import io.github.jhipster.application.repository.search.IndustryFirstClassSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing IndustryFirstClass.
 */
@Service
@Transactional
public class IndustryFirstClassService {

    private final Logger log = LoggerFactory.getLogger(IndustryFirstClassService.class);

    private final IndustryFirstClassRepository industryFirstClassRepository;

    private final IndustryFirstClassSearchRepository industryFirstClassSearchRepository;

    public IndustryFirstClassService(IndustryFirstClassRepository industryFirstClassRepository, IndustryFirstClassSearchRepository industryFirstClassSearchRepository) {
        this.industryFirstClassRepository = industryFirstClassRepository;
        this.industryFirstClassSearchRepository = industryFirstClassSearchRepository;
    }

    /**
     * Save a industryFirstClass.
     *
     * @param industryFirstClass the entity to save
     * @return the persisted entity
     */
    public IndustryFirstClass save(IndustryFirstClass industryFirstClass) {
        log.debug("Request to save IndustryFirstClass : {}", industryFirstClass);
        IndustryFirstClass result = industryFirstClassRepository.save(industryFirstClass);
        industryFirstClassSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the industryFirstClasses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IndustryFirstClass> findAll(Pageable pageable) {
        log.debug("Request to get all IndustryFirstClasses");
        return industryFirstClassRepository.findAll(pageable);
    }

    /**
     * Get one industryFirstClass by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public IndustryFirstClass findOne(Long id) {
        log.debug("Request to get IndustryFirstClass : {}", id);
        return industryFirstClassRepository.findOne(id);
    }

    /**
     * Delete the industryFirstClass by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete IndustryFirstClass : {}", id);
        industryFirstClassRepository.delete(id);
        industryFirstClassSearchRepository.delete(id);
    }

    /**
     * Search for the industryFirstClass corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IndustryFirstClass> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of IndustryFirstClasses for query {}", query);
        Page<IndustryFirstClass> result = industryFirstClassSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
