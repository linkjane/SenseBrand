package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.IndustryTop;
import io.github.jhipster.application.repository.IndustryTopRepository;
import io.github.jhipster.application.repository.search.IndustryTopSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing IndustryTop.
 */
@Service
@Transactional
public class IndustryTopService {

    private final Logger log = LoggerFactory.getLogger(IndustryTopService.class);

    private final IndustryTopRepository industryTopRepository;

    private final IndustryTopSearchRepository industryTopSearchRepository;

    public IndustryTopService(IndustryTopRepository industryTopRepository, IndustryTopSearchRepository industryTopSearchRepository) {
        this.industryTopRepository = industryTopRepository;
        this.industryTopSearchRepository = industryTopSearchRepository;
    }

    /**
     * Save a industryTop.
     *
     * @param industryTop the entity to save
     * @return the persisted entity
     */
    public IndustryTop save(IndustryTop industryTop) {
        log.debug("Request to save IndustryTop : {}", industryTop);
        IndustryTop result = industryTopRepository.save(industryTop);
        industryTopSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the industryTops.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IndustryTop> findAll(Pageable pageable) {
        log.debug("Request to get all IndustryTops");
        return industryTopRepository.findAll(pageable);
    }

    /**
     * Get one industryTop by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public IndustryTop findOne(Long id) {
        log.debug("Request to get IndustryTop : {}", id);
        return industryTopRepository.findOne(id);
    }

    /**
     * Delete the industryTop by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete IndustryTop : {}", id);
        industryTopRepository.delete(id);
        industryTopSearchRepository.delete(id);
    }

    /**
     * Search for the industryTop corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<IndustryTop> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of IndustryTops for query {}", query);
        Page<IndustryTop> result = industryTopSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
