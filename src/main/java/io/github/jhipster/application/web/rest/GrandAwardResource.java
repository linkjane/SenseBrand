package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.GrandAward;
import io.github.jhipster.application.service.GrandAwardService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing GrandAward.
 */
@RestController
@RequestMapping("/api")
public class GrandAwardResource {

    private final Logger log = LoggerFactory.getLogger(GrandAwardResource.class);

    private static final String ENTITY_NAME = "grandAward";

    private final GrandAwardService grandAwardService;

    public GrandAwardResource(GrandAwardService grandAwardService) {
        this.grandAwardService = grandAwardService;
    }

    /**
     * POST  /grand-awards : Create a new grandAward.
     *
     * @param grandAward the grandAward to create
     * @return the ResponseEntity with status 201 (Created) and with body the new grandAward, or with status 400 (Bad Request) if the grandAward has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/grand-awards")
    @Timed
    public ResponseEntity<GrandAward> createGrandAward(@RequestBody GrandAward grandAward) throws URISyntaxException {
        log.debug("REST request to save GrandAward : {}", grandAward);
        if (grandAward.getId() != null) {
            throw new BadRequestAlertException("A new grandAward cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrandAward result = grandAwardService.save(grandAward);
        return ResponseEntity.created(new URI("/api/grand-awards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /grand-awards : Updates an existing grandAward.
     *
     * @param grandAward the grandAward to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated grandAward,
     * or with status 400 (Bad Request) if the grandAward is not valid,
     * or with status 500 (Internal Server Error) if the grandAward couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/grand-awards")
    @Timed
    public ResponseEntity<GrandAward> updateGrandAward(@RequestBody GrandAward grandAward) throws URISyntaxException {
        log.debug("REST request to update GrandAward : {}", grandAward);
        if (grandAward.getId() == null) {
            return createGrandAward(grandAward);
        }
        GrandAward result = grandAwardService.save(grandAward);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, grandAward.getId().toString()))
            .body(result);
    }

    /**
     * GET  /grand-awards : get all the grandAwards.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of grandAwards in body
     */
    @GetMapping("/grand-awards")
    @Timed
    public ResponseEntity<List<GrandAward>> getAllGrandAwards(Pageable pageable) {
        log.debug("REST request to get a page of GrandAwards");
        Page<GrandAward> page = grandAwardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/grand-awards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /grand-awards/:id : get the "id" grandAward.
     *
     * @param id the id of the grandAward to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the grandAward, or with status 404 (Not Found)
     */
    @GetMapping("/grand-awards/{id}")
    @Timed
    public ResponseEntity<GrandAward> getGrandAward(@PathVariable Long id) {
        log.debug("REST request to get GrandAward : {}", id);
        GrandAward grandAward = grandAwardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(grandAward));
    }

    /**
     * DELETE  /grand-awards/:id : delete the "id" grandAward.
     *
     * @param id the id of the grandAward to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/grand-awards/{id}")
    @Timed
    public ResponseEntity<Void> deleteGrandAward(@PathVariable Long id) {
        log.debug("REST request to delete GrandAward : {}", id);
        grandAwardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/grand-awards?query=:query : search for the grandAward corresponding
     * to the query.
     *
     * @param query the query of the grandAward search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/grand-awards")
    @Timed
    public ResponseEntity<List<GrandAward>> searchGrandAwards(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of GrandAwards for query {}", query);
        Page<GrandAward> page = grandAwardService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/grand-awards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
