package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.DesignerSentiment;
import io.github.jhipster.application.service.DesignerSentimentService;
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
 * REST controller for managing DesignerSentiment.
 */
@RestController
@RequestMapping("/api")
public class DesignerSentimentResource {

    private final Logger log = LoggerFactory.getLogger(DesignerSentimentResource.class);

    private static final String ENTITY_NAME = "designerSentiment";

    private final DesignerSentimentService designerSentimentService;

    public DesignerSentimentResource(DesignerSentimentService designerSentimentService) {
        this.designerSentimentService = designerSentimentService;
    }

    /**
     * POST  /designer-sentiments : Create a new designerSentiment.
     *
     * @param designerSentiment the designerSentiment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new designerSentiment, or with status 400 (Bad Request) if the designerSentiment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/designer-sentiments")
    @Timed
    public ResponseEntity<DesignerSentiment> createDesignerSentiment(@Valid @RequestBody DesignerSentiment designerSentiment) throws URISyntaxException {
        log.debug("REST request to save DesignerSentiment : {}", designerSentiment);
        if (designerSentiment.getId() != null) {
            throw new BadRequestAlertException("A new designerSentiment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DesignerSentiment result = designerSentimentService.save(designerSentiment);
        return ResponseEntity.created(new URI("/api/designer-sentiments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /designer-sentiments : Updates an existing designerSentiment.
     *
     * @param designerSentiment the designerSentiment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated designerSentiment,
     * or with status 400 (Bad Request) if the designerSentiment is not valid,
     * or with status 500 (Internal Server Error) if the designerSentiment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/designer-sentiments")
    @Timed
    public ResponseEntity<DesignerSentiment> updateDesignerSentiment(@Valid @RequestBody DesignerSentiment designerSentiment) throws URISyntaxException {
        log.debug("REST request to update DesignerSentiment : {}", designerSentiment);
        if (designerSentiment.getId() == null) {
            return createDesignerSentiment(designerSentiment);
        }
        DesignerSentiment result = designerSentimentService.save(designerSentiment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, designerSentiment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /designer-sentiments : get all the designerSentiments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of designerSentiments in body
     */
    @GetMapping("/designer-sentiments")
    @Timed
    public ResponseEntity<List<DesignerSentiment>> getAllDesignerSentiments(Pageable pageable) {
        log.debug("REST request to get a page of DesignerSentiments");
        Page<DesignerSentiment> page = designerSentimentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/designer-sentiments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /designer-sentiments/:id : get the "id" designerSentiment.
     *
     * @param id the id of the designerSentiment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the designerSentiment, or with status 404 (Not Found)
     */
    @GetMapping("/designer-sentiments/{id}")
    @Timed
    public ResponseEntity<DesignerSentiment> getDesignerSentiment(@PathVariable Long id) {
        log.debug("REST request to get DesignerSentiment : {}", id);
        DesignerSentiment designerSentiment = designerSentimentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(designerSentiment));
    }

    /**
     * DELETE  /designer-sentiments/:id : delete the "id" designerSentiment.
     *
     * @param id the id of the designerSentiment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/designer-sentiments/{id}")
    @Timed
    public ResponseEntity<Void> deleteDesignerSentiment(@PathVariable Long id) {
        log.debug("REST request to delete DesignerSentiment : {}", id);
        designerSentimentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/designer-sentiments?query=:query : search for the designerSentiment corresponding
     * to the query.
     *
     * @param query the query of the designerSentiment search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/designer-sentiments")
    @Timed
    public ResponseEntity<List<DesignerSentiment>> searchDesignerSentiments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DesignerSentiments for query {}", query);
        Page<DesignerSentiment> page = designerSentimentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/designer-sentiments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
