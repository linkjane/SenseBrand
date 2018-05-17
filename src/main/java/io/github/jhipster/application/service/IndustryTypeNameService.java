package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.IndustryTypeName;
import io.github.jhipster.application.repository.IndustryTypeNameRepository;
import io.github.jhipster.application.repository.search.IndustryTypeNameSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing IndustryTypeName.
 */
@Service
@Transactional
public class IndustryTypeNameService {

    private final Logger log = LoggerFactory.getLogger(IndustryTypeNameService.class);

    private final IndustryTypeNameRepository industryTypeNameRepository;

    private final IndustryTypeNameSearchRepository industryTypeNameSearchRepository;

    public IndustryTypeNameService(IndustryTypeNameRepository industryTypeNameRepository, IndustryTypeNameSearchRepository industryTypeNameSearchRepository) {
        this.industryTypeNameRepository = industryTypeNameRepository;
        this.industryTypeNameSearchRepository = industryTypeNameSearchRepository;
    }

    /**
     * Save a industryTypeName.
     *
     * @param industryTypeName the entity to save
     * @return the persisted entity
     */
    public IndustryTypeName save(IndustryTypeName industryTypeName) {
        log.debug("Request to save IndustryTypeName : {}", industryTypeName);
        IndustryTypeName result = industryTypeNameRepository.save(industryTypeName);
        industryTypeNameSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the industryTypeNames.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IndustryTypeName> findAll(Pageable pageable) {
        log.debug("Request to get all IndustryTypeNames");
        return industryTypeNameRepository.findAll(pageable);
    }

    /**
     * Get one industryTypeName by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public IndustryTypeName findOne(Long id) {
        log.debug("Request to get IndustryTypeName : {}", id);
        return industryTypeNameRepository.findOne(id);
    }

    /**
     * Delete the industryTypeName by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete IndustryTypeName : {}", id);
        industryTypeNameRepository.delete(id);
        industryTypeNameSearchRepository.delete(id);
    }

    /**
     * Search for the industryTypeName corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IndustryTypeName> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of IndustryTypeNames for query {}", query);
        Page<IndustryTypeName> result = industryTypeNameSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
