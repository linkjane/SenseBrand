package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.DesignerShowImg;
import io.github.jhipster.application.repository.DesignerShowImgRepository;
import io.github.jhipster.application.repository.search.DesignerShowImgSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DesignerShowImg.
 */
@Service
@Transactional
public class DesignerShowImgService {

    private final Logger log = LoggerFactory.getLogger(DesignerShowImgService.class);

    private final DesignerShowImgRepository designerShowImgRepository;

    private final DesignerShowImgSearchRepository designerShowImgSearchRepository;

    public DesignerShowImgService(DesignerShowImgRepository designerShowImgRepository, DesignerShowImgSearchRepository designerShowImgSearchRepository) {
        this.designerShowImgRepository = designerShowImgRepository;
        this.designerShowImgSearchRepository = designerShowImgSearchRepository;
    }

    /**
     * Save a designerShowImg.
     *
     * @param designerShowImg the entity to save
     * @return the persisted entity
     */
    public DesignerShowImg save(DesignerShowImg designerShowImg) {
        log.debug("Request to save DesignerShowImg : {}", designerShowImg);
        DesignerShowImg result = designerShowImgRepository.save(designerShowImg);
        designerShowImgSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the designerShowImgs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DesignerShowImg> findAll(Pageable pageable) {
        log.debug("Request to get all DesignerShowImgs");
        return designerShowImgRepository.findAll(pageable);
    }

    /**
     * Get one designerShowImg by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DesignerShowImg findOne(Long id) {
        log.debug("Request to get DesignerShowImg : {}", id);
        return designerShowImgRepository.findOne(id);
    }

    /**
     * Delete the designerShowImg by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DesignerShowImg : {}", id);
        designerShowImgRepository.delete(id);
        designerShowImgSearchRepository.delete(id);
    }

    /**
     * Search for the designerShowImg corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DesignerShowImg> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DesignerShowImgs for query {}", query);
        Page<DesignerShowImg> result = designerShowImgSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
