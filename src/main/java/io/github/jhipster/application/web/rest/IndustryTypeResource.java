package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.IndustryType;
import io.github.jhipster.application.service.IndustryTypeService;
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
 * REST controller for managing IndustryType.
 */
@RestController
@RequestMapping("/api")
public class IndustryTypeResource {

    private final Logger log = LoggerFactory.getLogger(IndustryTypeResource.class);

    private static final String ENTITY_NAME = "industryType";

    private final IndustryTypeService industryTypeService;

    public IndustryTypeResource(IndustryTypeService industryTypeService) {
        this.industryTypeService = industryTypeService;
    }

    /**
     * POST  /industry-types : Create a new industryType.
     *
     * @param industryType the industryType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new industryType, or with status 400 (Bad Request) if the industryType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/industry-types")
    @Timed
    public ResponseEntity<IndustryType> createIndustryType(@Valid @RequestBody IndustryType industryType) throws URISyntaxException {
        log.debug("REST request to save IndustryType : {}", industryType);
        if (industryType.getId() != null) {
            throw new BadRequestAlertException("A new industryType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndustryType result = industryTypeService.save(industryType);
        return ResponseEntity.created(new URI("/api/industry-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /industry-types : Updates an existing industryType.
     *
     * @param industryType the industryType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated industryType,
     * or with status 400 (Bad Request) if the industryType is not valid,
     * or with status 500 (Internal Server Error) if the industryType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/industry-types")
    @Timed
    public ResponseEntity<IndustryType> updateIndustryType(@Valid @RequestBody IndustryType industryType) throws URISyntaxException {
        log.debug("REST request to update IndustryType : {}", industryType);
        if (industryType.getId() == null) {
            return createIndustryType(industryType);
        }
        IndustryType result = industryTypeService.save(industryType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, industryType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /industry-types : get all the industryTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of industryTypes in body
     */
    @GetMapping("/industry-types")
    @Timed
    public ResponseEntity<List<IndustryType>> getAllIndustryTypes(Pageable pageable) {
        log.debug("REST request to get a page of IndustryTypes");
        Page<IndustryType> page = industryTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/industry-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /industry-types/:id : get the "id" industryType.
     *
     * @param id the id of the industryType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the industryType, or with status 404 (Not Found)
     */
    @GetMapping("/industry-types/{id}")
    @Timed
    public ResponseEntity<IndustryType> getIndustryType(@PathVariable Long id) {
        log.debug("REST request to get IndustryType : {}", id);
        IndustryType industryType = industryTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(industryType));
    }

    /**
     * DELETE  /industry-types/:id : delete the "id" industryType.
     *
     * @param id the id of the industryType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/industry-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteIndustryType(@PathVariable Long id) {
        log.debug("REST request to delete IndustryType : {}", id);
        industryTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/industry-types?query=:query : search for the industryType corresponding
     * to the query.
     *
     * @param query the query of the industryType search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/industry-types")
    @Timed
    public ResponseEntity<List<IndustryType>> searchIndustryTypes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of IndustryTypes for query {}", query);
        Page<IndustryType> page = industryTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/industry-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
