package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.BrandRegion;
import io.github.jhipster.application.repository.BrandRegionRepository;
import io.github.jhipster.application.repository.search.BrandRegionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BrandRegion.
 */
@Service
@Transactional
public class BrandRegionService {

    private final Logger log = LoggerFactory.getLogger(BrandRegionService.class);

    private final BrandRegionRepository brandRegionRepository;

    private final BrandRegionSearchRepository brandRegionSearchRepository;

    public BrandRegionService(BrandRegionRepository brandRegionRepository, BrandRegionSearchRepository brandRegionSearchRepository) {
        this.brandRegionRepository = brandRegionRepository;
        this.brandRegionSearchRepository = brandRegionSearchRepository;
    }

    /**
     * Save a brandRegion.
     *
     * @param brandRegion the entity to save
     * @return the persisted entity
     */
    public BrandRegion save(BrandRegion brandRegion) {
        log.debug("Request to save BrandRegion : {}", brandRegion);
        BrandRegion result = brandRegionRepository.save(brandRegion);
        brandRegionSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the brandRegions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BrandRegion> findAll(Pageable pageable) {
        log.debug("Request to get all BrandRegions");
        return brandRegionRepository.findAll(pageable);
    }

    /**
     * Get one brandRegion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public BrandRegion findOne(Long id) {
        log.debug("Request to get BrandRegion : {}", id);
        return brandRegionRepository.findOne(id);
    }

    /**
     * Delete the brandRegion by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BrandRegion : {}", id);
        brandRegionRepository.delete(id);
        brandRegionSearchRepository.delete(id);
    }

    /**
     * Search for the brandRegion corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BrandRegion> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BrandRegions for query {}", query);
        Page<BrandRegion> result = brandRegionSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
