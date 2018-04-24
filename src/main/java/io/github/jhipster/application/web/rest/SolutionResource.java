package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Solution;
import io.github.jhipster.application.service.SolutionService;
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
 * REST controller for managing Solution.
 */
@RestController
@RequestMapping("/api")
public class SolutionResource {

    private final Logger log = LoggerFactory.getLogger(SolutionResource.class);

    private static final String ENTITY_NAME = "solution";

    private final SolutionService solutionService;

    public SolutionResource(SolutionService solutionService) {
        this.solutionService = solutionService;
    }

    /**
     * POST  /solutions : Create a new solution.
     *
     * @param solution the solution to create
     * @return the ResponseEntity with status 201 (Created) and with body the new solution, or with status 400 (Bad Request) if the solution has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/solutions")
    @Timed
    public ResponseEntity<Solution> createSolution(@Valid @RequestBody Solution solution) throws URISyntaxException {
        log.debug("REST request to save Solution : {}", solution);
        if (solution.getId() != null) {
            throw new BadRequestAlertException("A new solution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Solution result = solutionService.save(solution);
        return ResponseEntity.created(new URI("/api/solutions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /solutions : Updates an existing solution.
     *
     * @param solution the solution to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated solution,
     * or with status 400 (Bad Request) if the solution is not valid,
     * or with status 500 (Internal Server Error) if the solution couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/solutions")
    @Timed
    public ResponseEntity<Solution> updateSolution(@Valid @RequestBody Solution solution) throws URISyntaxException {
        log.debug("REST request to update Solution : {}", solution);
        if (solution.getId() == null) {
            return createSolution(solution);
        }
        Solution result = solutionService.save(solution);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, solution.getId().toString()))
            .body(result);
    }

    /**
     * GET  /solutions : get all the solutions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of solutions in body
     */
    @GetMapping("/solutions")
    @Timed
    public ResponseEntity<List<Solution>> getAllSolutions(Pageable pageable) {
        log.debug("REST request to get a page of Solutions");
        Page<Solution> page = solutionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/solutions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /solutions/:id : get the "id" solution.
     *
     * @param id the id of the solution to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the solution, or with status 404 (Not Found)
     */
    @GetMapping("/solutions/{id}")
    @Timed
    public ResponseEntity<Solution> getSolution(@PathVariable Long id) {
        log.debug("REST request to get Solution : {}", id);
        Solution solution = solutionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(solution));
    }

    /**
     * DELETE  /solutions/:id : delete the "id" solution.
     *
     * @param id the id of the solution to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/solutions/{id}")
    @Timed
    public ResponseEntity<Void> deleteSolution(@PathVariable Long id) {
        log.debug("REST request to delete Solution : {}", id);
        solutionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/solutions?query=:query : search for the solution corresponding
     * to the query.
     *
     * @param query the query of the solution search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/solutions")
    @Timed
    public ResponseEntity<List<Solution>> searchSolutions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Solutions for query {}", query);
        Page<Solution> page = solutionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/solutions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
