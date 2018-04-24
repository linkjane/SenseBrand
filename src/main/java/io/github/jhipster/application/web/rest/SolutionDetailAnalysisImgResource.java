package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.SolutionDetailAnalysisImg;
import io.github.jhipster.application.service.SolutionDetailAnalysisImgService;
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
 * REST controller for managing SolutionDetailAnalysisImg.
 */
@RestController
@RequestMapping("/api")
public class SolutionDetailAnalysisImgResource {

    private final Logger log = LoggerFactory.getLogger(SolutionDetailAnalysisImgResource.class);

    private static final String ENTITY_NAME = "solutionDetailAnalysisImg";

    private final SolutionDetailAnalysisImgService solutionDetailAnalysisImgService;

    public SolutionDetailAnalysisImgResource(SolutionDetailAnalysisImgService solutionDetailAnalysisImgService) {
        this.solutionDetailAnalysisImgService = solutionDetailAnalysisImgService;
    }

    /**
     * POST  /solution-detail-analysis-imgs : Create a new solutionDetailAnalysisImg.
     *
     * @param solutionDetailAnalysisImg the solutionDetailAnalysisImg to create
     * @return the ResponseEntity with status 201 (Created) and with body the new solutionDetailAnalysisImg, or with status 400 (Bad Request) if the solutionDetailAnalysisImg has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/solution-detail-analysis-imgs")
    @Timed
    public ResponseEntity<SolutionDetailAnalysisImg> createSolutionDetailAnalysisImg(@Valid @RequestBody SolutionDetailAnalysisImg solutionDetailAnalysisImg) throws URISyntaxException {
        log.debug("REST request to save SolutionDetailAnalysisImg : {}", solutionDetailAnalysisImg);
        if (solutionDetailAnalysisImg.getId() != null) {
            throw new BadRequestAlertException("A new solutionDetailAnalysisImg cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SolutionDetailAnalysisImg result = solutionDetailAnalysisImgService.save(solutionDetailAnalysisImg);
        return ResponseEntity.created(new URI("/api/solution-detail-analysis-imgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /solution-detail-analysis-imgs : Updates an existing solutionDetailAnalysisImg.
     *
     * @param solutionDetailAnalysisImg the solutionDetailAnalysisImg to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated solutionDetailAnalysisImg,
     * or with status 400 (Bad Request) if the solutionDetailAnalysisImg is not valid,
     * or with status 500 (Internal Server Error) if the solutionDetailAnalysisImg couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/solution-detail-analysis-imgs")
    @Timed
    public ResponseEntity<SolutionDetailAnalysisImg> updateSolutionDetailAnalysisImg(@Valid @RequestBody SolutionDetailAnalysisImg solutionDetailAnalysisImg) throws URISyntaxException {
        log.debug("REST request to update SolutionDetailAnalysisImg : {}", solutionDetailAnalysisImg);
        if (solutionDetailAnalysisImg.getId() == null) {
            return createSolutionDetailAnalysisImg(solutionDetailAnalysisImg);
        }
        SolutionDetailAnalysisImg result = solutionDetailAnalysisImgService.save(solutionDetailAnalysisImg);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, solutionDetailAnalysisImg.getId().toString()))
            .body(result);
    }

    /**
     * GET  /solution-detail-analysis-imgs : get all the solutionDetailAnalysisImgs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of solutionDetailAnalysisImgs in body
     */
    @GetMapping("/solution-detail-analysis-imgs")
    @Timed
    public ResponseEntity<List<SolutionDetailAnalysisImg>> getAllSolutionDetailAnalysisImgs(Pageable pageable) {
        log.debug("REST request to get a page of SolutionDetailAnalysisImgs");
        Page<SolutionDetailAnalysisImg> page = solutionDetailAnalysisImgService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/solution-detail-analysis-imgs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /solution-detail-analysis-imgs/:id : get the "id" solutionDetailAnalysisImg.
     *
     * @param id the id of the solutionDetailAnalysisImg to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the solutionDetailAnalysisImg, or with status 404 (Not Found)
     */
    @GetMapping("/solution-detail-analysis-imgs/{id}")
    @Timed
    public ResponseEntity<SolutionDetailAnalysisImg> getSolutionDetailAnalysisImg(@PathVariable Long id) {
        log.debug("REST request to get SolutionDetailAnalysisImg : {}", id);
        SolutionDetailAnalysisImg solutionDetailAnalysisImg = solutionDetailAnalysisImgService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(solutionDetailAnalysisImg));
    }

    /**
     * DELETE  /solution-detail-analysis-imgs/:id : delete the "id" solutionDetailAnalysisImg.
     *
     * @param id the id of the solutionDetailAnalysisImg to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/solution-detail-analysis-imgs/{id}")
    @Timed
    public ResponseEntity<Void> deleteSolutionDetailAnalysisImg(@PathVariable Long id) {
        log.debug("REST request to delete SolutionDetailAnalysisImg : {}", id);
        solutionDetailAnalysisImgService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/solution-detail-analysis-imgs?query=:query : search for the solutionDetailAnalysisImg corresponding
     * to the query.
     *
     * @param query the query of the solutionDetailAnalysisImg search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/solution-detail-analysis-imgs")
    @Timed
    public ResponseEntity<List<SolutionDetailAnalysisImg>> searchSolutionDetailAnalysisImgs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SolutionDetailAnalysisImgs for query {}", query);
        Page<SolutionDetailAnalysisImg> page = solutionDetailAnalysisImgService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/solution-detail-analysis-imgs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
