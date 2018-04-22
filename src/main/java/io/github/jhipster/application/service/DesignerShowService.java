package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.DesignerShow;
import io.github.jhipster.application.repository.DesignerShowRepository;
import io.github.jhipster.application.repository.search.DesignerShowSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DesignerShow.
 */
@Service
@Transactional
public class DesignerShowService {

    private final Logger log = LoggerFactory.getLogger(DesignerShowService.class);

    private final DesignerShowRepository designerShowRepository;

    private final DesignerShowSearchRepository designerShowSearchRepository;

    public DesignerShowService(DesignerShowRepository designerShowRepository, DesignerShowSearchRepository designerShowSearchRepository) {
        this.designerShowRepository = designerShowRepository;
        this.designerShowSearchRepository = designerShowSearchRepository;
    }

    /**
     * Save a designerShow.
     *
     * @param designerShow the entity to save
     * @return the persisted entity
     */
    public DesignerShow save(DesignerShow designerShow) {
        log.debug("Request to save DesignerShow : {}", designerShow);
        DesignerShow result = designerShowRepository.save(designerShow);
        designerShowSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the designerShows.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DesignerShow> findAll(Pageable pageable) {
        log.debug("Request to get all DesignerShows");
        return designerShowRepository.findAll(pageable);
    }

    /**
     * Get one designerShow by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DesignerShow findOne(Long id) {
        log.debug("Request to get DesignerShow : {}", id);
        return designerShowRepository.findOne(id);
    }

    /**
     * Delete the designerShow by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DesignerShow : {}", id);
        designerShowRepository.delete(id);
        designerShowSearchRepository.delete(id);
    }

    /**
     * Search for the designerShow corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DesignerShow> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DesignerShows for query {}", query);
        Page<DesignerShow> result = designerShowSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
