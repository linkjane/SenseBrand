package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.SolutionDetailAnalysis;
import io.github.jhipster.application.service.SolutionDetailAnalysisService;
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
 * REST controller for managing SolutionDetailAnalysis.
 */
@RestController
@RequestMapping("/api")
public class SolutionDetailAnalysisResource {

    private final Logger log = LoggerFactory.getLogger(SolutionDetailAnalysisResource.class);

    private static final String ENTITY_NAME = "solutionDetailAnalysis";

    private final SolutionDetailAnalysisService solutionDetailAnalysisService;

    public SolutionDetailAnalysisResource(SolutionDetailAnalysisService solutionDetailAnalysisService) {
        this.solutionDetailAnalysisService = solutionDetailAnalysisService;
    }

    /**
     * POST  /solution-detail-analyses : Create a new solutionDetailAnalysis.
     *
     * @param solutionDetailAnalysis the solutionDetailAnalysis to create
     * @return the ResponseEntity with status 201 (Created) and with body the new solutionDetailAnalysis, or with status 400 (Bad Request) if the solutionDetailAnalysis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/solution-detail-analyses")
    @Timed
    public ResponseEntity<SolutionDetailAnalysis> createSolutionDetailAnalysis(@Valid @RequestBody SolutionDetailAnalysis solutionDetailAnalysis) throws URISyntaxException {
        log.debug("REST request to save SolutionDetailAnalysis : {}", solutionDetailAnalysis);
        if (solutionDetailAnalysis.getId() != null) {
            throw new BadRequestAlertException("A new solutionDetailAnalysis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SolutionDetailAnalysis result = solutionDetailAnalysisService.save(solutionDetailAnalysis);
        return ResponseEntity.created(new URI("/api/solution-detail-analyses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /solution-detail-analyses : Updates an existing solutionDetailAnalysis.
     *
     * @param solutionDetailAnalysis the solutionDetailAnalysis to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated solutionDetailAnalysis,
     * or with status 400 (Bad Request) if the solutionDetailAnalysis is not valid,
     * or with status 500 (Internal Server Error) if the solutionDetailAnalysis couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/solution-detail-analyses")
    @Timed
    public ResponseEntity<SolutionDetailAnalysis> updateSolutionDetailAnalysis(@Valid @RequestBody SolutionDetailAnalysis solutionDetailAnalysis) throws URISyntaxException {
        log.debug("REST request to update SolutionDetailAnalysis : {}", solutionDetailAnalysis);
        if (solutionDetailAnalysis.getId() == null) {
            return createSolutionDetailAnalysis(solutionDetailAnalysis);
        }
        SolutionDetailAnalysis result = solutionDetailAnalysisService.save(solutionDetailAnalysis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, solutionDetailAnalysis.getId().toString()))
            .body(result);
    }

    /**
     * GET  /solution-detail-analyses : get all the solutionDetailAnalyses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of solutionDetailAnalyses in body
     */
    @GetMapping("/solution-detail-analyses")
    @Timed
    public ResponseEntity<List<SolutionDetailAnalysis>> getAllSolutionDetailAnalyses(Pageable pageable) {
        log.debug("REST request to get a page of SolutionDetailAnalyses");
        Page<SolutionDetailAnalysis> page = solutionDetailAnalysisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/solution-detail-analyses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /solution-detail-analyses/:id : get the "id" solutionDetailAnalysis.
     *
     * @param id the id of the solutionDetailAnalysis to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the solutionDetailAnalysis, or with status 404 (Not Found)
     */
    @GetMapping("/solution-detail-analyses/{id}")
    @Timed
    public ResponseEntity<SolutionDetailAnalysis> getSolutionDetailAnalysis(@PathVariable Long id) {
        log.debug("REST request to get SolutionDetailAnalysis : {}", id);
        SolutionDetailAnalysis solutionDetailAnalysis = solutionDetailAnalysisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(solutionDetailAnalysis));
    }

    /**
     * DELETE  /solution-detail-analyses/:id : delete the "id" solutionDetailAnalysis.
     *
     * @param id the id of the solutionDetailAnalysis to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/solution-detail-analyses/{id}")
    @Timed
    public ResponseEntity<Void> deleteSolutionDetailAnalysis(@PathVariable Long id) {
        log.debug("REST request to delete SolutionDetailAnalysis : {}", id);
        solutionDetailAnalysisService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/solution-detail-analyses?query=:query : search for the solutionDetailAnalysis corresponding
     * to the query.
     *
     * @param query the query of the solutionDetailAnalysis search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/solution-detail-analyses")
    @Timed
    public ResponseEntity<List<SolutionDetailAnalysis>> searchSolutionDetailAnalyses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SolutionDetailAnalyses for query {}", query);
        Page<SolutionDetailAnalysis> page = solutionDetailAnalysisService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/solution-detail-analyses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
