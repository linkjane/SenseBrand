package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.IndustryAll;
import io.github.jhipster.application.service.IndustryAllService;
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
 * REST controller for managing IndustryAll.
 */
@RestController
@RequestMapping("/api")
public class IndustryAllResource {

    private final Logger log = LoggerFactory.getLogger(IndustryAllResource.class);

    private static final String ENTITY_NAME = "industryAll";

    private final IndustryAllService industryAllService;

    public IndustryAllResource(IndustryAllService industryAllService) {
        this.industryAllService = industryAllService;
    }

    /**
     * POST  /industry-alls : Create a new industryAll.
     *
     * @param industryAll the industryAll to create
     * @return the ResponseEntity with status 201 (Created) and with body the new industryAll, or with status 400 (Bad Request) if the industryAll has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/industry-alls")
    @Timed
    public ResponseEntity<IndustryAll> createIndustryAll(@Valid @RequestBody IndustryAll industryAll) throws URISyntaxException {
        log.debug("REST request to save IndustryAll : {}", industryAll);
        if (industryAll.getId() != null) {
            throw new BadRequestAlertException("A new industryAll cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndustryAll result = industryAllService.save(industryAll);
        return ResponseEntity.created(new URI("/api/industry-alls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /industry-alls : Updates an existing industryAll.
     *
     * @param industryAll the industryAll to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated industryAll,
     * or with status 400 (Bad Request) if the industryAll is not valid,
     * or with status 500 (Internal Server Error) if the industryAll couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/industry-alls")
    @Timed
    public ResponseEntity<IndustryAll> updateIndustryAll(@Valid @RequestBody IndustryAll industryAll) throws URISyntaxException {
        log.debug("REST request to update IndustryAll : {}", industryAll);
        if (industryAll.getId() == null) {
            return createIndustryAll(industryAll);
        }
        IndustryAll result = industryAllService.save(industryAll);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, industryAll.getId().toString()))
            .body(result);
    }

    /**
     * GET  /industry-alls : get all the industryAlls.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of industryAlls in body
     */
    @GetMapping("/industry-alls")
    @Timed
    public ResponseEntity<List<IndustryAll>> getAllIndustryAlls(Pageable pageable) {
        log.debug("REST request to get a page of IndustryAlls");
        Page<IndustryAll> page = industryAllService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/industry-alls");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /industry-alls/:id : get the "id" industryAll.
     *
     * @param id the id of the industryAll to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the industryAll, or with status 404 (Not Found)
     */
    @GetMapping("/industry-alls/{id}")
    @Timed
    public ResponseEntity<IndustryAll> getIndustryAll(@PathVariable Long id) {
        log.debug("REST request to get IndustryAll : {}", id);
        IndustryAll industryAll = industryAllService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(industryAll));
    }

    /**
     * DELETE  /industry-alls/:id : delete the "id" industryAll.
     *
     * @param id the id of the industryAll to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/industry-alls/{id}")
    @Timed
    public ResponseEntity<Void> deleteIndustryAll(@PathVariable Long id) {
        log.debug("REST request to delete IndustryAll : {}", id);
        industryAllService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/industry-alls?query=:query : search for the industryAll corresponding
     * to the query.
     *
     * @param query the query of the industryAll search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/industry-alls")
    @Timed
    public ResponseEntity<List<IndustryAll>> searchIndustryAlls(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of IndustryAlls for query {}", query);
        Page<IndustryAll> page = industryAllService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/industry-alls");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
