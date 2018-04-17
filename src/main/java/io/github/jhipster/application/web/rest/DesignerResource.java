package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Designer;

import io.github.jhipster.application.repository.DesignerRepository;
import io.github.jhipster.application.repository.search.DesignerSearchRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Designer.
 */
@RestController
@RequestMapping("/api")
public class DesignerResource {

    private final Logger log = LoggerFactory.getLogger(DesignerResource.class);

    private static final String ENTITY_NAME = "designer";

    private final DesignerRepository designerRepository;

    private final DesignerSearchRepository designerSearchRepository;

    public DesignerResource(DesignerRepository designerRepository, DesignerSearchRepository designerSearchRepository) {
        this.designerRepository = designerRepository;
        this.designerSearchRepository = designerSearchRepository;
    }

    /**
     * POST  /designers : Create a new designer.
     *
     * @param designer the designer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new designer, or with status 400 (Bad Request) if the designer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/designers")
    @Timed
    public ResponseEntity<Designer> createDesigner(@Valid @RequestBody Designer designer) throws URISyntaxException {
        log.debug("REST request to save Designer : {}", designer);
        if (designer.getId() != null) {
            throw new BadRequestAlertException("A new designer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Designer result = designerRepository.save(designer);
        designerSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/designers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /designers : Updates an existing designer.
     *
     * @param designer the designer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated designer,
     * or with status 400 (Bad Request) if the designer is not valid,
     * or with status 500 (Internal Server Error) if the designer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/designers")
    @Timed
    public ResponseEntity<Designer> updateDesigner(@Valid @RequestBody Designer designer) throws URISyntaxException {
        log.debug("REST request to update Designer : {}", designer);
        if (designer.getId() == null) {
            return createDesigner(designer);
        }
        Designer result = designerRepository.save(designer);
        designerSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, designer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /designers : get all the designers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of designers in body
     */
    @GetMapping("/designers")
    @Timed
    public ResponseEntity<List<Designer>> getAllDesigners(Pageable pageable) {
        log.debug("REST request to get a page of Designers");
        Page<Designer> page = designerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/designers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /designers/:id : get the "id" designer.
     *
     * @param id the id of the designer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the designer, or with status 404 (Not Found)
     */
    @GetMapping("/designers/{id}")
    @Timed
    public ResponseEntity<Designer> getDesigner(@PathVariable Long id) {
        log.debug("REST request to get Designer : {}", id);
        Designer designer = designerRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(designer));
    }

    /**
     * DELETE  /designers/:id : delete the "id" designer.
     *
     * @param id the id of the designer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/designers/{id}")
    @Timed
    public ResponseEntity<Void> deleteDesigner(@PathVariable Long id) {
        log.debug("REST request to delete Designer : {}", id);
        designerRepository.delete(id);
        designerSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/designers?query=:query : search for the designer corresponding
     * to the query.
     *
     * @param query the query of the designer search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/designers")
    @Timed
    public ResponseEntity<List<Designer>> searchDesigners(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Designers for query {}", query);
        Page<Designer> page = designerSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/designers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
