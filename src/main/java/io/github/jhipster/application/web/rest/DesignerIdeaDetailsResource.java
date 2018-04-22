package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.DesignerIdeaDetails;
import io.github.jhipster.application.service.DesignerIdeaDetailsService;
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
 * REST controller for managing DesignerIdeaDetails.
 */
@RestController
@RequestMapping("/api")
public class DesignerIdeaDetailsResource {

    private final Logger log = LoggerFactory.getLogger(DesignerIdeaDetailsResource.class);

    private static final String ENTITY_NAME = "designerIdeaDetails";

    private final DesignerIdeaDetailsService designerIdeaDetailsService;

    public DesignerIdeaDetailsResource(DesignerIdeaDetailsService designerIdeaDetailsService) {
        this.designerIdeaDetailsService = designerIdeaDetailsService;
    }

    /**
     * POST  /designer-idea-details : Create a new designerIdeaDetails.
     *
     * @param designerIdeaDetails the designerIdeaDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new designerIdeaDetails, or with status 400 (Bad Request) if the designerIdeaDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/designer-idea-details")
    @Timed
    public ResponseEntity<DesignerIdeaDetails> createDesignerIdeaDetails(@Valid @RequestBody DesignerIdeaDetails designerIdeaDetails) throws URISyntaxException {
        log.debug("REST request to save DesignerIdeaDetails : {}", designerIdeaDetails);
        if (designerIdeaDetails.getId() != null) {
            throw new BadRequestAlertException("A new designerIdeaDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DesignerIdeaDetails result = designerIdeaDetailsService.save(designerIdeaDetails);
        return ResponseEntity.created(new URI("/api/designer-idea-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /designer-idea-details : Updates an existing designerIdeaDetails.
     *
     * @param designerIdeaDetails the designerIdeaDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated designerIdeaDetails,
     * or with status 400 (Bad Request) if the designerIdeaDetails is not valid,
     * or with status 500 (Internal Server Error) if the designerIdeaDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/designer-idea-details")
    @Timed
    public ResponseEntity<DesignerIdeaDetails> updateDesignerIdeaDetails(@Valid @RequestBody DesignerIdeaDetails designerIdeaDetails) throws URISyntaxException {
        log.debug("REST request to update DesignerIdeaDetails : {}", designerIdeaDetails);
        if (designerIdeaDetails.getId() == null) {
            return createDesignerIdeaDetails(designerIdeaDetails);
        }
        DesignerIdeaDetails result = designerIdeaDetailsService.save(designerIdeaDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, designerIdeaDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /designer-idea-details : get all the designerIdeaDetails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of designerIdeaDetails in body
     */
    @GetMapping("/designer-idea-details")
    @Timed
    public ResponseEntity<List<DesignerIdeaDetails>> getAllDesignerIdeaDetails(Pageable pageable) {
        log.debug("REST request to get a page of DesignerIdeaDetails");
        Page<DesignerIdeaDetails> page = designerIdeaDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/designer-idea-details");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /designer-idea-details/:id : get the "id" designerIdeaDetails.
     *
     * @param id the id of the designerIdeaDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the designerIdeaDetails, or with status 404 (Not Found)
     */
    @GetMapping("/designer-idea-details/{id}")
    @Timed
    public ResponseEntity<DesignerIdeaDetails> getDesignerIdeaDetails(@PathVariable Long id) {
        log.debug("REST request to get DesignerIdeaDetails : {}", id);
        DesignerIdeaDetails designerIdeaDetails = designerIdeaDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(designerIdeaDetails));
    }

    /**
     * DELETE  /designer-idea-details/:id : delete the "id" designerIdeaDetails.
     *
     * @param id the id of the designerIdeaDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/designer-idea-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteDesignerIdeaDetails(@PathVariable Long id) {
        log.debug("REST request to delete DesignerIdeaDetails : {}", id);
        designerIdeaDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/designer-idea-details?query=:query : search for the designerIdeaDetails corresponding
     * to the query.
     *
     * @param query the query of the designerIdeaDetails search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/designer-idea-details")
    @Timed
    public ResponseEntity<List<DesignerIdeaDetails>> searchDesignerIdeaDetails(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DesignerIdeaDetails for query {}", query);
        Page<DesignerIdeaDetails> page = designerIdeaDetailsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/designer-idea-details");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
