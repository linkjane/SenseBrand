package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.IndustryFirstClass;
import io.github.jhipster.application.service.IndustryFirstClassService;
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
 * REST controller for managing IndustryFirstClass.
 */
@RestController
@RequestMapping("/api")
public class IndustryFirstClassResource {

    private final Logger log = LoggerFactory.getLogger(IndustryFirstClassResource.class);

    private static final String ENTITY_NAME = "industryFirstClass";

    private final IndustryFirstClassService industryFirstClassService;

    public IndustryFirstClassResource(IndustryFirstClassService industryFirstClassService) {
        this.industryFirstClassService = industryFirstClassService;
    }

    /**
     * POST  /industry-first-classes : Create a new industryFirstClass.
     *
     * @param industryFirstClass the industryFirstClass to create
     * @return the ResponseEntity with status 201 (Created) and with body the new industryFirstClass, or with status 400 (Bad Request) if the industryFirstClass has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/industry-first-classes")
    @Timed
    public ResponseEntity<IndustryFirstClass> createIndustryFirstClass(@Valid @RequestBody IndustryFirstClass industryFirstClass) throws URISyntaxException {
        log.debug("REST request to save IndustryFirstClass : {}", industryFirstClass);
        if (industryFirstClass.getId() != null) {
            throw new BadRequestAlertException("A new industryFirstClass cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndustryFirstClass result = industryFirstClassService.save(industryFirstClass);
        return ResponseEntity.created(new URI("/api/industry-first-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /industry-first-classes : Updates an existing industryFirstClass.
     *
     * @param industryFirstClass the industryFirstClass to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated industryFirstClass,
     * or with status 400 (Bad Request) if the industryFirstClass is not valid,
     * or with status 500 (Internal Server Error) if the industryFirstClass couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/industry-first-classes")
    @Timed
    public ResponseEntity<IndustryFirstClass> updateIndustryFirstClass(@Valid @RequestBody IndustryFirstClass industryFirstClass) throws URISyntaxException {
        log.debug("REST request to update IndustryFirstClass : {}", industryFirstClass);
        if (industryFirstClass.getId() == null) {
            return createIndustryFirstClass(industryFirstClass);
        }
        IndustryFirstClass result = industryFirstClassService.save(industryFirstClass);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, industryFirstClass.getId().toString()))
            .body(result);
    }

    /**
     * GET  /industry-first-classes : get all the industryFirstClasses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of industryFirstClasses in body
     */
    @GetMapping("/industry-first-classes")
    @Timed
    public ResponseEntity<List<IndustryFirstClass>> getAllIndustryFirstClasses(Pageable pageable) {
        log.debug("REST request to get a page of IndustryFirstClasses");
        Page<IndustryFirstClass> page = industryFirstClassService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/industry-first-classes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /industry-first-classes/:id : get the "id" industryFirstClass.
     *
     * @param id the id of the industryFirstClass to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the industryFirstClass, or with status 404 (Not Found)
     */
    @GetMapping("/industry-first-classes/{id}")
    @Timed
    public ResponseEntity<IndustryFirstClass> getIndustryFirstClass(@PathVariable Long id) {
        log.debug("REST request to get IndustryFirstClass : {}", id);
        IndustryFirstClass industryFirstClass = industryFirstClassService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(industryFirstClass));
    }

    /**
     * DELETE  /industry-first-classes/:id : delete the "id" industryFirstClass.
     *
     * @param id the id of the industryFirstClass to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/industry-first-classes/{id}")
    @Timed
    public ResponseEntity<Void> deleteIndustryFirstClass(@PathVariable Long id) {
        log.debug("REST request to delete IndustryFirstClass : {}", id);
        industryFirstClassService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/industry-first-classes?query=:query : search for the industryFirstClass corresponding
     * to the query.
     *
     * @param query the query of the industryFirstClass search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/industry-first-classes")
    @Timed
    public ResponseEntity<List<IndustryFirstClass>> searchIndustryFirstClasses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of IndustryFirstClasses for query {}", query);
        Page<IndustryFirstClass> page = industryFirstClassService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/industry-first-classes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
