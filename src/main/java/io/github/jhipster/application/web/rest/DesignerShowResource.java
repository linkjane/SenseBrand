package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.DesignerShow;
import io.github.jhipster.application.service.DesignerShowService;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DesignerShow.
 */
@RestController
@RequestMapping("/api")
public class DesignerShowResource {

    private final Logger log = LoggerFactory.getLogger(DesignerShowResource.class);

    private static final String ENTITY_NAME = "designerShow";

    private final DesignerShowService designerShowService;

    public DesignerShowResource(DesignerShowService designerShowService) {
        this.designerShowService = designerShowService;
    }

    /**
     * POST  /designer-shows : Create a new designerShow.
     *
     * @param designerShow the designerShow to create
     * @return the ResponseEntity with status 201 (Created) and with body the new designerShow, or with status 400 (Bad Request) if the designerShow has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/designer-shows")
    @Timed
    public ResponseEntity<DesignerShow> createDesignerShow(@Valid @RequestBody DesignerShow designerShow) throws URISyntaxException {
        log.debug("REST request to save DesignerShow : {}", designerShow);
        if (designerShow.getId() != null) {
            throw new BadRequestAlertException("A new designerShow cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DesignerShow result = designerShowService.save(designerShow);
        return ResponseEntity.created(new URI("/api/designer-shows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /designer-shows : Updates an existing designerShow.
     *
     * @param designerShow the designerShow to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated designerShow,
     * or with status 400 (Bad Request) if the designerShow is not valid,
     * or with status 500 (Internal Server Error) if the designerShow couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/designer-shows")
    @Timed
    public ResponseEntity<DesignerShow> updateDesignerShow(@Valid @RequestBody DesignerShow designerShow) throws URISyntaxException {
        log.debug("REST request to update DesignerShow : {}", designerShow);
        if (designerShow.getId() == null) {
            return createDesignerShow(designerShow);
        }
        DesignerShow result = designerShowService.save(designerShow);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, designerShow.getId().toString()))
            .body(result);
    }

    /**
     * GET  /designer-shows : get all the designerShows.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of designerShows in body
     */
    @GetMapping("/designer-shows")
    @Timed
    public ResponseEntity<List<DesignerShow>> getAllDesignerShows(Pageable pageable) {
        log.debug("REST request to get a page of DesignerShows");
        Page<DesignerShow> page = designerShowService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/designer-shows");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /designer-shows/:id : get the "id" designerShow.
     *
     * @param id the id of the designerShow to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the designerShow, or with status 404 (Not Found)
     */
    @GetMapping("/designer-shows/{id}")
    @Timed
    public ResponseEntity<DesignerShow> getDesignerShow(@PathVariable Long id) {
        log.debug("REST request to get DesignerShow : {}", id);
        DesignerShow designerShow = designerShowService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(designerShow));
    }

    /**
     * DELETE  /designer-shows/:id : delete the "id" designerShow.
     *
     * @param id the id of the designerShow to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/designer-shows/{id}")
    @Timed
    public ResponseEntity<Void> deleteDesignerShow(@PathVariable Long id) {
        log.debug("REST request to delete DesignerShow : {}", id);
        designerShowService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/designer-shows?query=:query : search for the designerShow corresponding
     * to the query.
     *
     * @param query the query of the designerShow search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/designer-shows")
    @Timed
    public ResponseEntity<List<DesignerShow>> searchDesignerShows(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DesignerShows for query {}", query);
        Page<DesignerShow> page = designerShowService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/designer-shows");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
