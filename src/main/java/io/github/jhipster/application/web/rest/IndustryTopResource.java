package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.IndustryTop;
import io.github.jhipster.application.service.IndustryTopService;
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
 * REST controller for managing IndustryTop.
 */
@RestController
@RequestMapping("/api")
public class IndustryTopResource {

    private final Logger log = LoggerFactory.getLogger(IndustryTopResource.class);

    private static final String ENTITY_NAME = "industryTop";

    private final IndustryTopService industryTopService;

    public IndustryTopResource(IndustryTopService industryTopService) {
        this.industryTopService = industryTopService;
    }

    /**
     * POST  /industry-tops : Create a new industryTop.
     *
     * @param industryTop the industryTop to create
     * @return the ResponseEntity with status 201 (Created) and with body the new industryTop, or with status 400 (Bad Request) if the industryTop has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/industry-tops")
    @Timed
    public ResponseEntity<IndustryTop> createIndustryTop(@Valid @RequestBody IndustryTop industryTop) throws URISyntaxException {
        log.debug("REST request to save IndustryTop : {}", industryTop);
        if (industryTop.getId() != null) {
            throw new BadRequestAlertException("A new industryTop cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndustryTop result = industryTopService.save(industryTop);
        return ResponseEntity.created(new URI("/api/industry-tops/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /industry-tops : Updates an existing industryTop.
     *
     * @param industryTop the industryTop to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated industryTop,
     * or with status 400 (Bad Request) if the industryTop is not valid,
     * or with status 500 (Internal Server Error) if the industryTop couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/industry-tops")
    @Timed
    public ResponseEntity<IndustryTop> updateIndustryTop(@Valid @RequestBody IndustryTop industryTop) throws URISyntaxException {
        log.debug("REST request to update IndustryTop : {}", industryTop);
        if (industryTop.getId() == null) {
            return createIndustryTop(industryTop);
        }
        IndustryTop result = industryTopService.save(industryTop);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, industryTop.getId().toString()))
            .body(result);
    }

    /**
     * GET  /industry-tops : get all the industryTops.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of industryTops in body
     */
    @GetMapping("/industry-tops")
    @Timed
    public ResponseEntity<List<IndustryTop>> getAllIndustryTops(Pageable pageable) {
        log.debug("REST request to get a page of IndustryTops");
        Page<IndustryTop> page = industryTopService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/industry-tops");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /industry-tops/:id : get the "id" industryTop.
     *
     * @param id the id of the industryTop to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the industryTop, or with status 404 (Not Found)
     */
    @GetMapping("/industry-tops/{id}")
    @Timed
    public ResponseEntity<IndustryTop> getIndustryTop(@PathVariable Long id) {
        log.debug("REST request to get IndustryTop : {}", id);
        IndustryTop industryTop = industryTopService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(industryTop));
    }

    /**
     * DELETE  /industry-tops/:id : delete the "id" industryTop.
     *
     * @param id the id of the industryTop to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/industry-tops/{id}")
    @Timed
    public ResponseEntity<Void> deleteIndustryTop(@PathVariable Long id) {
        log.debug("REST request to delete IndustryTop : {}", id);
        industryTopService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/industry-tops?query=:query : search for the industryTop corresponding
     * to the query.
     *
     * @param query the query of the industryTop search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/industry-tops")
    @Timed
    public ResponseEntity<List<IndustryTop>> searchIndustryTops(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of IndustryTops for query {}", query);
        Page<IndustryTop> page = industryTopService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/industry-tops");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
