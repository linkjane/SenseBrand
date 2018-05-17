package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.IndustryTypeName;
import io.github.jhipster.application.service.IndustryTypeNameService;
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
 * REST controller for managing IndustryTypeName.
 */
@RestController
@RequestMapping("/api")
public class IndustryTypeNameResource {

    private final Logger log = LoggerFactory.getLogger(IndustryTypeNameResource.class);

    private static final String ENTITY_NAME = "industryTypeName";

    private final IndustryTypeNameService industryTypeNameService;

    public IndustryTypeNameResource(IndustryTypeNameService industryTypeNameService) {
        this.industryTypeNameService = industryTypeNameService;
    }

    /**
     * POST  /industry-type-names : Create a new industryTypeName.
     *
     * @param industryTypeName the industryTypeName to create
     * @return the ResponseEntity with status 201 (Created) and with body the new industryTypeName, or with status 400 (Bad Request) if the industryTypeName has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/industry-type-names")
    @Timed
    public ResponseEntity<IndustryTypeName> createIndustryTypeName(@Valid @RequestBody IndustryTypeName industryTypeName) throws URISyntaxException {
        log.debug("REST request to save IndustryTypeName : {}", industryTypeName);
        if (industryTypeName.getId() != null) {
            throw new BadRequestAlertException("A new industryTypeName cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndustryTypeName result = industryTypeNameService.save(industryTypeName);
        return ResponseEntity.created(new URI("/api/industry-type-names/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /industry-type-names : Updates an existing industryTypeName.
     *
     * @param industryTypeName the industryTypeName to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated industryTypeName,
     * or with status 400 (Bad Request) if the industryTypeName is not valid,
     * or with status 500 (Internal Server Error) if the industryTypeName couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/industry-type-names")
    @Timed
    public ResponseEntity<IndustryTypeName> updateIndustryTypeName(@Valid @RequestBody IndustryTypeName industryTypeName) throws URISyntaxException {
        log.debug("REST request to update IndustryTypeName : {}", industryTypeName);
        if (industryTypeName.getId() == null) {
            return createIndustryTypeName(industryTypeName);
        }
        IndustryTypeName result = industryTypeNameService.save(industryTypeName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, industryTypeName.getId().toString()))
            .body(result);
    }

    /**
     * GET  /industry-type-names : get all the industryTypeNames.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of industryTypeNames in body
     */
    @GetMapping("/industry-type-names")
    @Timed
    public ResponseEntity<List<IndustryTypeName>> getAllIndustryTypeNames(Pageable pageable) {
        log.debug("REST request to get a page of IndustryTypeNames");
        Page<IndustryTypeName> page = industryTypeNameService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/industry-type-names");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /industry-type-names/:id : get the "id" industryTypeName.
     *
     * @param id the id of the industryTypeName to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the industryTypeName, or with status 404 (Not Found)
     */
    @GetMapping("/industry-type-names/{id}")
    @Timed
    public ResponseEntity<IndustryTypeName> getIndustryTypeName(@PathVariable Long id) {
        log.debug("REST request to get IndustryTypeName : {}", id);
        IndustryTypeName industryTypeName = industryTypeNameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(industryTypeName));
    }

    /**
     * DELETE  /industry-type-names/:id : delete the "id" industryTypeName.
     *
     * @param id the id of the industryTypeName to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/industry-type-names/{id}")
    @Timed
    public ResponseEntity<Void> deleteIndustryTypeName(@PathVariable Long id) {
        log.debug("REST request to delete IndustryTypeName : {}", id);
        industryTypeNameService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/industry-type-names?query=:query : search for the industryTypeName corresponding
     * to the query.
     *
     * @param query the query of the industryTypeName search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/industry-type-names")
    @Timed
    public ResponseEntity<List<IndustryTypeName>> searchIndustryTypeNames(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of IndustryTypeNames for query {}", query);
        Page<IndustryTypeName> page = industryTypeNameService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/industry-type-names");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
