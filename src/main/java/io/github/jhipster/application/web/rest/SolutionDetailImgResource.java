package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.SolutionDetailImg;
import io.github.jhipster.application.service.SolutionDetailImgService;
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
 * REST controller for managing SolutionDetailImg.
 */
@RestController
@RequestMapping("/api")
public class SolutionDetailImgResource {

    private final Logger log = LoggerFactory.getLogger(SolutionDetailImgResource.class);

    private static final String ENTITY_NAME = "solutionDetailImg";

    private final SolutionDetailImgService solutionDetailImgService;

    public SolutionDetailImgResource(SolutionDetailImgService solutionDetailImgService) {
        this.solutionDetailImgService = solutionDetailImgService;
    }

    /**
     * POST  /solution-detail-imgs : Create a new solutionDetailImg.
     *
     * @param solutionDetailImg the solutionDetailImg to create
     * @return the ResponseEntity with status 201 (Created) and with body the new solutionDetailImg, or with status 400 (Bad Request) if the solutionDetailImg has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/solution-detail-imgs")
    @Timed
    public ResponseEntity<SolutionDetailImg> createSolutionDetailImg(@Valid @RequestBody SolutionDetailImg solutionDetailImg) throws URISyntaxException {
        log.debug("REST request to save SolutionDetailImg : {}", solutionDetailImg);
        if (solutionDetailImg.getId() != null) {
            throw new BadRequestAlertException("A new solutionDetailImg cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SolutionDetailImg result = solutionDetailImgService.save(solutionDetailImg);
        return ResponseEntity.created(new URI("/api/solution-detail-imgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /solution-detail-imgs : Updates an existing solutionDetailImg.
     *
     * @param solutionDetailImg the solutionDetailImg to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated solutionDetailImg,
     * or with status 400 (Bad Request) if the solutionDetailImg is not valid,
     * or with status 500 (Internal Server Error) if the solutionDetailImg couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/solution-detail-imgs")
    @Timed
    public ResponseEntity<SolutionDetailImg> updateSolutionDetailImg(@Valid @RequestBody SolutionDetailImg solutionDetailImg) throws URISyntaxException {
        log.debug("REST request to update SolutionDetailImg : {}", solutionDetailImg);
        if (solutionDetailImg.getId() == null) {
            return createSolutionDetailImg(solutionDetailImg);
        }
        SolutionDetailImg result = solutionDetailImgService.save(solutionDetailImg);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, solutionDetailImg.getId().toString()))
            .body(result);
    }

    /**
     * GET  /solution-detail-imgs : get all the solutionDetailImgs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of solutionDetailImgs in body
     */
    @GetMapping("/solution-detail-imgs")
    @Timed
    public ResponseEntity<List<SolutionDetailImg>> getAllSolutionDetailImgs(Pageable pageable) {
        log.debug("REST request to get a page of SolutionDetailImgs");
        Page<SolutionDetailImg> page = solutionDetailImgService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/solution-detail-imgs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /solution-detail-imgs/:id : get the "id" solutionDetailImg.
     *
     * @param id the id of the solutionDetailImg to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the solutionDetailImg, or with status 404 (Not Found)
     */
    @GetMapping("/solution-detail-imgs/{id}")
    @Timed
    public ResponseEntity<SolutionDetailImg> getSolutionDetailImg(@PathVariable Long id) {
        log.debug("REST request to get SolutionDetailImg : {}", id);
        SolutionDetailImg solutionDetailImg = solutionDetailImgService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(solutionDetailImg));
    }

    /**
     * DELETE  /solution-detail-imgs/:id : delete the "id" solutionDetailImg.
     *
     * @param id the id of the solutionDetailImg to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/solution-detail-imgs/{id}")
    @Timed
    public ResponseEntity<Void> deleteSolutionDetailImg(@PathVariable Long id) {
        log.debug("REST request to delete SolutionDetailImg : {}", id);
        solutionDetailImgService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/solution-detail-imgs?query=:query : search for the solutionDetailImg corresponding
     * to the query.
     *
     * @param query the query of the solutionDetailImg search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/solution-detail-imgs")
    @Timed
    public ResponseEntity<List<SolutionDetailImg>> searchSolutionDetailImgs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SolutionDetailImgs for query {}", query);
        Page<SolutionDetailImg> page = solutionDetailImgService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/solution-detail-imgs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
