package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Brand;
import io.github.jhipster.application.repository.BrandRepository;
import io.github.jhipster.application.repository.search.BrandSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Brand.
 */
@Service
@Transactional
public class BrandService {

    private final Logger log = LoggerFactory.getLogger(BrandService.class);

    private final BrandRepository brandRepository;

    private final BrandSearchRepository brandSearchRepository;

    public BrandService(BrandRepository brandRepository, BrandSearchRepository brandSearchRepository) {
        this.brandRepository = brandRepository;
        this.brandSearchRepository = brandSearchRepository;
    }

    /**
     * Save a brand.
     *
     * @param brand the entity to save
     * @return the persisted entity
     */
    public Brand save(Brand brand) {
        log.debug("Request to save Brand : {}", brand);
        Brand result = brandRepository.save(brand);
        brandSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the brands.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Brand> findAll(Pageable pageable) {
        log.debug("Request to get all Brands");
        return brandRepository.findAll(pageable);
    }

    /**
     * Get one brand by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Brand findOne(Long id) {
        log.debug("Request to get Brand : {}", id);
        return brandRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the brand by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Brand : {}", id);
        brandRepository.delete(id);
        brandSearchRepository.delete(id);
    }

    /**
     * Search for the brand corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Brand> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Brands for query {}", query);
        Page<Brand> result = brandSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
