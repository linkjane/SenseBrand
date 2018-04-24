package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.IndustrySecondClass;
import io.github.jhipster.application.repository.IndustrySecondClassRepository;
import io.github.jhipster.application.repository.search.IndustrySecondClassSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing IndustrySecondClass.
 */
@Service
@Transactional
public class IndustrySecondClassService {

    private final Logger log = LoggerFactory.getLogger(IndustrySecondClassService.class);

    private final IndustrySecondClassRepository industrySecondClassRepository;

    private final IndustrySecondClassSearchRepository industrySecondClassSearchRepository;

    public IndustrySecondClassService(IndustrySecondClassRepository industrySecondClassRepository, IndustrySecondClassSearchRepository industrySecondClassSearchRepository) {
        this.industrySecondClassRepository = industrySecondClassRepository;
        this.industrySecondClassSearchRepository = industrySecondClassSearchRepository;
    }

    /**
     * Save a industrySecondClass.
     *
     * @param industrySecondClass the entity to save
     * @return the persisted entity
     */
    public IndustrySecondClass save(IndustrySecondClass industrySecondClass) {
        log.debug("Request to save IndustrySecondClass : {}", industrySecondClass);
        IndustrySecondClass result = industrySecondClassRepository.save(industrySecondClass);
        industrySecondClassSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the industrySecondClasses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IndustrySecondClass> findAll(Pageable pageable) {
        log.debug("Request to get all IndustrySecondClasses");
        return industrySecondClassRepository.findAll(pageable);
    }

    /**
     * Get one industrySecondClass by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public IndustrySecondClass findOne(Long id) {
        log.debug("Request to get IndustrySecondClass : {}", id);
        return industrySecondClassRepository.findOne(id);
    }

    /**
     * Delete the industrySecondClass by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete IndustrySecondClass : {}", id);
        industrySecondClassRepository.delete(id);
        industrySecondClassSearchRepository.delete(id);
    }

    /**
     * Search for the industrySecondClass corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IndustrySecondClass> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of IndustrySecondClasses for query {}", query);
        Page<IndustrySecondClass> result = industrySecondClassSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
