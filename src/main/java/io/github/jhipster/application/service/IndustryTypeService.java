package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.IndustryType;
import io.github.jhipster.application.repository.IndustryTypeRepository;
import io.github.jhipster.application.repository.search.IndustryTypeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing IndustryType.
 */
@Service
@Transactional
public class IndustryTypeService {

    private final Logger log = LoggerFactory.getLogger(IndustryTypeService.class);

    private final IndustryTypeRepository industryTypeRepository;

    private final IndustryTypeSearchRepository industryTypeSearchRepository;

    public IndustryTypeService(IndustryTypeRepository industryTypeRepository, IndustryTypeSearchRepository industryTypeSearchRepository) {
        this.industryTypeRepository = industryTypeRepository;
        this.industryTypeSearchRepository = industryTypeSearchRepository;
    }

    /**
     * Save a industryType.
     *
     * @param industryType the entity to save
     * @return the persisted entity
     */
    public IndustryType save(IndustryType industryType) {
        log.debug("Request to save IndustryType : {}", industryType);
        IndustryType result = industryTypeRepository.save(industryType);
        industryTypeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the industryTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IndustryType> findAll(Pageable pageable) {
        log.debug("Request to get all IndustryTypes");
        return industryTypeRepository.findAll(pageable);
    }

    /**
     * Get one industryType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public IndustryType findOne(Long id) {
        log.debug("Request to get IndustryType : {}", id);
        return industryTypeRepository.findOne(id);
    }

    /**
     * Delete the industryType by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete IndustryType : {}", id);
        industryTypeRepository.delete(id);
        industryTypeSearchRepository.delete(id);
    }

    /**
     * Search for the industryType corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IndustryType> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of IndustryTypes for query {}", query);
        Page<IndustryType> result = industryTypeSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
