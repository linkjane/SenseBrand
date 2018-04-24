package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.OurSight;
import io.github.jhipster.application.service.OurSightService;
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
 * REST controller for managing OurSight.
 */
@RestController
@RequestMapping("/api")
public class OurSightResource {

    private final Logger log = LoggerFactory.getLogger(OurSightResource.class);

    private static final String ENTITY_NAME = "ourSight";

    private final OurSightService ourSightService;

    public OurSightResource(OurSightService ourSightService) {
        this.ourSightService = ourSightService;
    }

    /**
     * POST  /our-sights : Create a new ourSight.
     *
     * @param ourSight the ourSight to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ourSight, or with status 400 (Bad Request) if the ourSight has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/our-sights")
    @Timed
    public ResponseEntity<OurSight> createOurSight(@RequestBody OurSight ourSight) throws URISyntaxException {
        log.debug("REST request to save OurSight : {}", ourSight);
        if (ourSight.getId() != null) {
            throw new BadRequestAlertException("A new ourSight cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OurSight result = ourSightService.save(ourSight);
        return ResponseEntity.created(new URI("/api/our-sights/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /our-sights : Updates an existing ourSight.
     *
     * @param ourSight the ourSight to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ourSight,
     * or with status 400 (Bad Request) if the ourSight is not valid,
     * or with status 500 (Internal Server Error) if the ourSight couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/our-sights")
    @Timed
    public ResponseEntity<OurSight> updateOurSight(@RequestBody OurSight ourSight) throws URISyntaxException {
        log.debug("REST request to update OurSight : {}", ourSight);
        if (ourSight.getId() == null) {
            return createOurSight(ourSight);
        }
        OurSight result = ourSightService.save(ourSight);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ourSight.getId().toString()))
            .body(result);
    }

    /**
     * GET  /our-sights : get all the ourSights.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ourSights in body
     */
    @GetMapping("/our-sights")
    @Timed
    public ResponseEntity<List<OurSight>> getAllOurSights(Pageable pageable) {
        log.debug("REST request to get a page of OurSights");
        Page<OurSight> page = ourSightService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/our-sights");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /our-sights/:id : get the "id" ourSight.
     *
     * @param id the id of the ourSight to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ourSight, or with status 404 (Not Found)
     */
    @GetMapping("/our-sights/{id}")
    @Timed
    public ResponseEntity<OurSight> getOurSight(@PathVariable Long id) {
        log.debug("REST request to get OurSight : {}", id);
        OurSight ourSight = ourSightService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ourSight));
    }

    /**
     * DELETE  /our-sights/:id : delete the "id" ourSight.
     *
     * @param id the id of the ourSight to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/our-sights/{id}")
    @Timed
    public ResponseEntity<Void> deleteOurSight(@PathVariable Long id) {
        log.debug("REST request to delete OurSight : {}", id);
        ourSightService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/our-sights?query=:query : search for the ourSight corresponding
     * to the query.
     *
     * @param query the query of the ourSight search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/our-sights")
    @Timed
    public ResponseEntity<List<OurSight>> searchOurSights(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of OurSights for query {}", query);
        Page<OurSight> page = ourSightService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/our-sights");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
