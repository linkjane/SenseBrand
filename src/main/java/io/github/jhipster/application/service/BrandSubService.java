package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.BrandSub;
import io.github.jhipster.application.repository.BrandSubRepository;
import io.github.jhipster.application.repository.search.BrandSubSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BrandSub.
 */
@Service
@Transactional
public class BrandSubService {

    private final Logger log = LoggerFactory.getLogger(BrandSubService.class);

    private final BrandSubRepository brandSubRepository;

    private final BrandSubSearchRepository brandSubSearchRepository;

    public BrandSubService(BrandSubRepository brandSubRepository, BrandSubSearchRepository brandSubSearchRepository) {
        this.brandSubRepository = brandSubRepository;
        this.brandSubSearchRepository = brandSubSearchRepository;
    }

    /**
     * Save a brandSub.
     *
     * @param brandSub the entity to save
     * @return the persisted entity
     */
    public BrandSub save(BrandSub brandSub) {
        log.debug("Request to save BrandSub : {}", brandSub);
        BrandSub result = brandSubRepository.save(brandSub);
        brandSubSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the brandSubs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BrandSub> findAll(Pageable pageable) {
        log.debug("Request to get all BrandSubs");
        return brandSubRepository.findAll(pageable);
    }

    /**
     * Get one brandSub by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public BrandSub findOne(Long id) {
        log.debug("Request to get BrandSub : {}", id);
        return brandSubRepository.findOne(id);
    }

    /**
     * Delete the brandSub by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BrandSub : {}", id);
        brandSubRepository.delete(id);
        brandSubSearchRepository.delete(id);
    }

    /**
     * Search for the brandSub corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BrandSub> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BrandSubs for query {}", query);
        Page<BrandSub> result = brandSubSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
