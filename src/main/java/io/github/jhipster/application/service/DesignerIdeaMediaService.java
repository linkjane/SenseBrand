package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.DesignerIdeaMedia;
import io.github.jhipster.application.repository.DesignerIdeaMediaRepository;
import io.github.jhipster.application.repository.search.DesignerIdeaMediaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DesignerIdeaMedia.
 */
@Service
@Transactional
public class DesignerIdeaMediaService {

    private final Logger log = LoggerFactory.getLogger(DesignerIdeaMediaService.class);

    private final DesignerIdeaMediaRepository designerIdeaMediaRepository;

    private final DesignerIdeaMediaSearchRepository designerIdeaMediaSearchRepository;

    public DesignerIdeaMediaService(DesignerIdeaMediaRepository designerIdeaMediaRepository, DesignerIdeaMediaSearchRepository designerIdeaMediaSearchRepository) {
        this.designerIdeaMediaRepository = designerIdeaMediaRepository;
        this.designerIdeaMediaSearchRepository = designerIdeaMediaSearchRepository;
    }

    /**
     * Save a designerIdeaMedia.
     *
     * @param designerIdeaMedia the entity to save
     * @return the persisted entity
     */
    public DesignerIdeaMedia save(DesignerIdeaMedia designerIdeaMedia) {
        log.debug("Request to save DesignerIdeaMedia : {}", designerIdeaMedia);
        DesignerIdeaMedia result = designerIdeaMediaRepository.save(designerIdeaMedia);
        designerIdeaMediaSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the designerIdeaMedias.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DesignerIdeaMedia> findAll(Pageable pageable) {
        log.debug("Request to get all DesignerIdeaMedias");
        return designerIdeaMediaRepository.findAll(pageable);
    }

    /**
     * Get one designerIdeaMedia by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DesignerIdeaMedia findOne(Long id) {
        log.debug("Request to get DesignerIdeaMedia : {}", id);
        return designerIdeaMediaRepository.findOne(id);
    }

    /**
     * Delete the designerIdeaMedia by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DesignerIdeaMedia : {}", id);
        designerIdeaMediaRepository.delete(id);
        designerIdeaMediaSearchRepository.delete(id);
    }

    /**
     * Search for the designerIdeaMedia corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DesignerIdeaMedia> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DesignerIdeaMedias for query {}", query);
        Page<DesignerIdeaMedia> result = designerIdeaMediaSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
