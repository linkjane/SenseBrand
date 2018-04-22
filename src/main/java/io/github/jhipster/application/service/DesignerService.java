package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Designer;
import io.github.jhipster.application.repository.DesignerRepository;
import io.github.jhipster.application.repository.search.DesignerSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Designer.
 */
@Service
@Transactional
public class DesignerService {

    private final Logger log = LoggerFactory.getLogger(DesignerService.class);

    private final DesignerRepository designerRepository;

    private final DesignerSearchRepository designerSearchRepository;

    public DesignerService(DesignerRepository designerRepository, DesignerSearchRepository designerSearchRepository) {
        this.designerRepository = designerRepository;
        this.designerSearchRepository = designerSearchRepository;
    }

    /**
     * Save a designer.
     *
     * @param designer the entity to save
     * @return the persisted entity
     */
    public Designer save(Designer designer) {
        log.debug("Request to save Designer : {}", designer);
        Designer result = designerRepository.save(designer);
        designerSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the designers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Designer> findAll(Pageable pageable) {
        log.debug("Request to get all Designers");
        return designerRepository.findAll(pageable);
    }


    /**
     *  get all the designers where DesignerIdeaDetails is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Designer> findAllWhereDesignerIdeaDetailsIsNull() {
        log.debug("Request to get all designers where DesignerIdeaDetails is null");
        return StreamSupport
            .stream(designerRepository.findAll().spliterator(), false)
            .filter(designer -> designer.getDesignerIdeaDetails() == null)
            .collect(Collectors.toList());
    }


    /**
     *  get all the designers where DesignerShow is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Designer> findAllWhereDesignerShowIsNull() {
        log.debug("Request to get all designers where DesignerShow is null");
        return StreamSupport
            .stream(designerRepository.findAll().spliterator(), false)
            .filter(designer -> designer.getDesignerShow() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one designer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Designer findOne(Long id) {
        log.debug("Request to get Designer : {}", id);
        return designerRepository.findOne(id);
    }

    /**
     * Delete the designer by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Designer : {}", id);
        designerRepository.delete(id);
        designerSearchRepository.delete(id);
    }

    /**
     * Search for the designer corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Designer> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Designers for query {}", query);
        Page<Designer> result = designerSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
