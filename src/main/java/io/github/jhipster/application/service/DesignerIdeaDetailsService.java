package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.DesignerIdeaDetails;
import io.github.jhipster.application.repository.DesignerIdeaDetailsRepository;
import io.github.jhipster.application.repository.search.DesignerIdeaDetailsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DesignerIdeaDetails.
 */
@Service
@Transactional
public class DesignerIdeaDetailsService {

    private final Logger log = LoggerFactory.getLogger(DesignerIdeaDetailsService.class);

    private final DesignerIdeaDetailsRepository designerIdeaDetailsRepository;

    private final DesignerIdeaDetailsSearchRepository designerIdeaDetailsSearchRepository;

    public DesignerIdeaDetailsService(DesignerIdeaDetailsRepository designerIdeaDetailsRepository, DesignerIdeaDetailsSearchRepository designerIdeaDetailsSearchRepository) {
        this.designerIdeaDetailsRepository = designerIdeaDetailsRepository;
        this.designerIdeaDetailsSearchRepository = designerIdeaDetailsSearchRepository;
    }

    /**
     * Save a designerIdeaDetails.
     *
     * @param designerIdeaDetails the entity to save
     * @return the persisted entity
     */
    public DesignerIdeaDetails save(DesignerIdeaDetails designerIdeaDetails) {
        log.debug("Request to save DesignerIdeaDetails : {}", designerIdeaDetails);
        DesignerIdeaDetails result = designerIdeaDetailsRepository.save(designerIdeaDetails);
        designerIdeaDetailsSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the designerIdeaDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DesignerIdeaDetails> findAll(Pageable pageable) {
        log.debug("Request to get all DesignerIdeaDetails");
        return designerIdeaDetailsRepository.findAll(pageable);
    }

    /**
     * Get one designerIdeaDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DesignerIdeaDetails findOne(Long id) {
        log.debug("Request to get DesignerIdeaDetails : {}", id);
        return designerIdeaDetailsRepository.findOne(id);
    }

    /**
     * Delete the designerIdeaDetails by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DesignerIdeaDetails : {}", id);
        designerIdeaDetailsRepository.delete(id);
        designerIdeaDetailsSearchRepository.delete(id);
    }

    /**
     * Search for the designerIdeaDetails corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DesignerIdeaDetails> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DesignerIdeaDetails for query {}", query);
        Page<DesignerIdeaDetails> result = designerIdeaDetailsSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
