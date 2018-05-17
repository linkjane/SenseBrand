package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.BrandSubDetails;
import io.github.jhipster.application.repository.BrandSubDetailsRepository;
import io.github.jhipster.application.repository.search.BrandSubDetailsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BrandSubDetails.
 */
@Service
@Transactional
public class BrandSubDetailsService {

    private final Logger log = LoggerFactory.getLogger(BrandSubDetailsService.class);

    private final BrandSubDetailsRepository brandSubDetailsRepository;

    private final BrandSubDetailsSearchRepository brandSubDetailsSearchRepository;

    public BrandSubDetailsService(BrandSubDetailsRepository brandSubDetailsRepository, BrandSubDetailsSearchRepository brandSubDetailsSearchRepository) {
        this.brandSubDetailsRepository = brandSubDetailsRepository;
        this.brandSubDetailsSearchRepository = brandSubDetailsSearchRepository;
    }

    /**
     * Save a brandSubDetails.
     *
     * @param brandSubDetails the entity to save
     * @return the persisted entity
     */
    public BrandSubDetails save(BrandSubDetails brandSubDetails) {
        log.debug("Request to save BrandSubDetails : {}", brandSubDetails);
        BrandSubDetails result = brandSubDetailsRepository.save(brandSubDetails);
        brandSubDetailsSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the brandSubDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BrandSubDetails> findAll(Pageable pageable) {
        log.debug("Request to get all BrandSubDetails");
        return brandSubDetailsRepository.findAll(pageable);
    }

    /**
     * Get one brandSubDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public BrandSubDetails findOne(Long id) {
        log.debug("Request to get BrandSubDetails : {}", id);
        return brandSubDetailsRepository.findOne(id);
    }

    /**
     * Delete the brandSubDetails by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BrandSubDetails : {}", id);
        brandSubDetailsRepository.delete(id);
        brandSubDetailsSearchRepository.delete(id);
    }

    /**
     * Search for the brandSubDetails corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BrandSubDetails> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BrandSubDetails for query {}", query);
        Page<BrandSubDetails> result = brandSubDetailsSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
