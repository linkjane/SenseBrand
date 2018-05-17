package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.DesignerAward;
import io.github.jhipster.application.service.DesignerAwardService;
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
 * REST controller for managing DesignerAward.
 */
@RestController
@RequestMapping("/api")
public class DesignerAwardResource {

    private final Logger log = LoggerFactory.getLogger(DesignerAwardResource.class);

    private static final String ENTITY_NAME = "designerAward";

    private final DesignerAwardService designerAwardService;

    public DesignerAwardResource(DesignerAwardService designerAwardService) {
        this.designerAwardService = designerAwardService;
    }

    /**
     * POST  /designer-awards : Create a new designerAward.
     *
     * @param designerAward the designerAward to create
     * @return the ResponseEntity with status 201 (Created) and with body the new designerAward, or with status 400 (Bad Request) if the designerAward has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/designer-awards")
    @Timed
    public ResponseEntity<DesignerAward> createDesignerAward(@Valid @RequestBody DesignerAward designerAward) throws URISyntaxException {
        log.debug("REST request to save DesignerAward : {}", designerAward);
        if (designerAward.getId() != null) {
            throw new BadRequestAlertException("A new designerAward cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DesignerAward result = designerAwardService.save(designerAward);
        return ResponseEntity.created(new URI("/api/designer-awards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /designer-awards : Updates an existing designerAward.
     *
     * @param designerAward the designerAward to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated designerAward,
     * or with status 400 (Bad Request) if the designerAward is not valid,
     * or with status 500 (Internal Server Error) if the designerAward couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/designer-awards")
    @Timed
    public ResponseEntity<DesignerAward> updateDesignerAward(@Valid @RequestBody DesignerAward designerAward) throws URISyntaxException {
        log.debug("REST request to update DesignerAward : {}", designerAward);
        if (designerAward.getId() == null) {
            return createDesignerAward(designerAward);
        }
        DesignerAward result = designerAwardService.save(designerAward);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, designerAward.getId().toString()))
            .body(result);
    }

    /**
     * GET  /designer-awards : get all the designerAwards.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of designerAwards in body
     */
    @GetMapping("/designer-awards")
    @Timed
    public ResponseEntity<List<DesignerAward>> getAllDesignerAwards(Pageable pageable) {
        log.debug("REST request to get a page of DesignerAwards");
        Page<DesignerAward> page = designerAwardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/designer-awards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /designer-awards/:id : get the "id" designerAward.
     *
     * @param id the id of the designerAward to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the designerAward, or with status 404 (Not Found)
     */
    @GetMapping("/designer-awards/{id}")
    @Timed
    public ResponseEntity<DesignerAward> getDesignerAward(@PathVariable Long id) {
        log.debug("REST request to get DesignerAward : {}", id);
        DesignerAward designerAward = designerAwardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(designerAward));
    }

    /**
     * DELETE  /designer-awards/:id : delete the "id" designerAward.
     *
     * @param id the id of the designerAward to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/designer-awards/{id}")
    @Timed
    public ResponseEntity<Void> deleteDesignerAward(@PathVariable Long id) {
        log.debug("REST request to delete DesignerAward : {}", id);
        designerAwardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/designer-awards?query=:query : search for the designerAward corresponding
     * to the query.
     *
     * @param query the query of the designerAward search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/designer-awards")
    @Timed
    public ResponseEntity<List<DesignerAward>> searchDesignerAwards(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DesignerAwards for query {}", query);
        Page<DesignerAward> page = designerAwardService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/designer-awards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
