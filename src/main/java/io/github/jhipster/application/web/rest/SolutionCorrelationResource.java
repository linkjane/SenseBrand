package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.SolutionCorrelation;
import io.github.jhipster.application.service.SolutionCorrelationService;
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
 * REST controller for managing SolutionCorrelation.
 */
@RestController
@RequestMapping("/api")
public class SolutionCorrelationResource {

    private final Logger log = LoggerFactory.getLogger(SolutionCorrelationResource.class);

    private static final String ENTITY_NAME = "solutionCorrelation";

    private final SolutionCorrelationService solutionCorrelationService;

    public SolutionCorrelationResource(SolutionCorrelationService solutionCorrelationService) {
        this.solutionCorrelationService = solutionCorrelationService;
    }

    /**
     * POST  /solution-correlations : Create a new solutionCorrelation.
     *
     * @param solutionCorrelation the solutionCorrelation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new solutionCorrelation, or with status 400 (Bad Request) if the solutionCorrelation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/solution-correlations")
    @Timed
    public ResponseEntity<SolutionCorrelation> createSolutionCorrelation(@Valid @RequestBody SolutionCorrelation solutionCorrelation) throws URISyntaxException {
        log.debug("REST request to save SolutionCorrelation : {}", solutionCorrelation);
        if (solutionCorrelation.getId() != null) {
            throw new BadRequestAlertException("A new solutionCorrelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SolutionCorrelation result = solutionCorrelationService.save(solutionCorrelation);
        return ResponseEntity.created(new URI("/api/solution-correlations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /solution-correlations : Updates an existing solutionCorrelation.
     *
     * @param solutionCorrelation the solutionCorrelation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated solutionCorrelation,
     * or with status 400 (Bad Request) if the solutionCorrelation is not valid,
     * or with status 500 (Internal Server Error) if the solutionCorrelation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/solution-correlations")
    @Timed
    public ResponseEntity<SolutionCorrelation> updateSolutionCorrelation(@Valid @RequestBody SolutionCorrelation solutionCorrelation) throws URISyntaxException {
        log.debug("REST request to update SolutionCorrelation : {}", solutionCorrelation);
        if (solutionCorrelation.getId() == null) {
            return createSolutionCorrelation(solutionCorrelation);
        }
        SolutionCorrelation result = solutionCorrelationService.save(solutionCorrelation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, solutionCorrelation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /solution-correlations : get all the solutionCorrelations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of solutionCorrelations in body
     */
    @GetMapping("/solution-correlations")
    @Timed
    public ResponseEntity<List<SolutionCorrelation>> getAllSolutionCorrelations(Pageable pageable) {
        log.debug("REST request to get a page of SolutionCorrelations");
        Page<SolutionCorrelation> page = solutionCorrelationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/solution-correlations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /solution-correlations/:id : get the "id" solutionCorrelation.
     *
     * @param id the id of the solutionCorrelation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the solutionCorrelation, or with status 404 (Not Found)
     */
    @GetMapping("/solution-correlations/{id}")
    @Timed
    public ResponseEntity<SolutionCorrelation> getSolutionCorrelation(@PathVariable Long id) {
        log.debug("REST request to get SolutionCorrelation : {}", id);
        SolutionCorrelation solutionCorrelation = solutionCorrelationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(solutionCorrelation));
    }

    /**
     * DELETE  /solution-correlations/:id : delete the "id" solutionCorrelation.
     *
     * @param id the id of the solutionCorrelation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/solution-correlations/{id}")
    @Timed
    public ResponseEntity<Void> deleteSolutionCorrelation(@PathVariable Long id) {
        log.debug("REST request to delete SolutionCorrelation : {}", id);
        solutionCorrelationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/solution-correlations?query=:query : search for the solutionCorrelation corresponding
     * to the query.
     *
     * @param query the query of the solutionCorrelation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/solution-correlations")
    @Timed
    public ResponseEntity<List<SolutionCorrelation>> searchSolutionCorrelations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SolutionCorrelations for query {}", query);
        Page<SolutionCorrelation> page = solutionCorrelationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/solution-correlations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
