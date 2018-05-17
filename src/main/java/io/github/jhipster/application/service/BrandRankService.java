package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.BrandRank;
import io.github.jhipster.application.repository.BrandRankRepository;
import io.github.jhipster.application.repository.search.BrandRankSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing BrandRank.
 */
@Service
@Transactional
public class BrandRankService {

    private final Logger log = LoggerFactory.getLogger(BrandRankService.class);

    private final BrandRankRepository brandRankRepository;

    private final BrandRankSearchRepository brandRankSearchRepository;

    public BrandRankService(BrandRankRepository brandRankRepository, BrandRankSearchRepository brandRankSearchRepository) {
        this.brandRankRepository = brandRankRepository;
        this.brandRankSearchRepository = brandRankSearchRepository;
    }

    /**
     * Save a brandRank.
     *
     * @param brandRank the entity to save
     * @return the persisted entity
     */
    public BrandRank save(BrandRank brandRank) {
        log.debug("Request to save BrandRank : {}", brandRank);
        BrandRank result = brandRankRepository.save(brandRank);
        brandRankSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the brandRanks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BrandRank> findAll(Pageable pageable) {
        log.debug("Request to get all BrandRanks");
        return brandRankRepository.findAll(pageable);
    }

    /**
     * Get one brandRank by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public BrandRank findOne(Long id) {
        log.debug("Request to get BrandRank : {}", id);
        return brandRankRepository.findOne(id);
    }

    /**
     * Delete the brandRank by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BrandRank : {}", id);
        brandRankRepository.delete(id);
        brandRankSearchRepository.delete(id);
    }

    /**
     * Search for the brandRank corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BrandRank> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of BrandRanks for query {}", query);
        Page<BrandRank> result = brandRankSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
