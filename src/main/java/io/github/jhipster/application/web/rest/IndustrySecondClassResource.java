package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.IndustrySecondClass;
import io.github.jhipster.application.service.IndustrySecondClassService;
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
 * REST controller for managing IndustrySecondClass.
 */
@RestController
@RequestMapping("/api")
public class IndustrySecondClassResource {

    private final Logger log = LoggerFactory.getLogger(IndustrySecondClassResource.class);

    private static final String ENTITY_NAME = "industrySecondClass";

    private final IndustrySecondClassService industrySecondClassService;

    public IndustrySecondClassResource(IndustrySecondClassService industrySecondClassService) {
        this.industrySecondClassService = industrySecondClassService;
    }

    /**
     * POST  /industry-second-classes : Create a new industrySecondClass.
     *
     * @param industrySecondClass the industrySecondClass to create
     * @return the ResponseEntity with status 201 (Created) and with body the new industrySecondClass, or with status 400 (Bad Request) if the industrySecondClass has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/industry-second-classes")
    @Timed
    public ResponseEntity<IndustrySecondClass> createIndustrySecondClass(@Valid @RequestBody IndustrySecondClass industrySecondClass) throws URISyntaxException {
        log.debug("REST request to save IndustrySecondClass : {}", industrySecondClass);
        if (industrySecondClass.getId() != null) {
            throw new BadRequestAlertException("A new industrySecondClass cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IndustrySecondClass result = industrySecondClassService.save(industrySecondClass);
        return ResponseEntity.created(new URI("/api/industry-second-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /industry-second-classes : Updates an existing industrySecondClass.
     *
     * @param industrySecondClass the industrySecondClass to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated industrySecondClass,
     * or with status 400 (Bad Request) if the industrySecondClass is not valid,
     * or with status 500 (Internal Server Error) if the industrySecondClass couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/industry-second-classes")
    @Timed
    public ResponseEntity<IndustrySecondClass> updateIndustrySecondClass(@Valid @RequestBody IndustrySecondClass industrySecondClass) throws URISyntaxException {
        log.debug("REST request to update IndustrySecondClass : {}", industrySecondClass);
        if (industrySecondClass.getId() == null) {
            return createIndustrySecondClass(industrySecondClass);
        }
        IndustrySecondClass result = industrySecondClassService.save(industrySecondClass);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, industrySecondClass.getId().toString()))
            .body(result);
    }

    /**
     * GET  /industry-second-classes : get all the industrySecondClasses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of industrySecondClasses in body
     */
    @GetMapping("/industry-second-classes")
    @Timed
    public ResponseEntity<List<IndustrySecondClass>> getAllIndustrySecondClasses(Pageable pageable) {
        log.debug("REST request to get a page of IndustrySecondClasses");
        Page<IndustrySecondClass> page = industrySecondClassService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/industry-second-classes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /industry-second-classes/:id : get the "id" industrySecondClass.
     *
     * @param id the id of the industrySecondClass to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the industrySecondClass, or with status 404 (Not Found)
     */
    @GetMapping("/industry-second-classes/{id}")
    @Timed
    public ResponseEntity<IndustrySecondClass> getIndustrySecondClass(@PathVariable Long id) {
        log.debug("REST request to get IndustrySecondClass : {}", id);
        IndustrySecondClass industrySecondClass = industrySecondClassService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(industrySecondClass));
    }

    /**
     * DELETE  /industry-second-classes/:id : delete the "id" industrySecondClass.
     *
     * @param id the id of the industrySecondClass to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/industry-second-classes/{id}")
    @Timed
    public ResponseEntity<Void> deleteIndustrySecondClass(@PathVariable Long id) {
        log.debug("REST request to delete IndustrySecondClass : {}", id);
        industrySecondClassService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/industry-second-classes?query=:query : search for the industrySecondClass corresponding
     * to the query.
     *
     * @param query the query of the industrySecondClass search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/industry-second-classes")
    @Timed
    public ResponseEntity<List<IndustrySecondClass>> searchIndustrySecondClasses(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of IndustrySecondClasses for query {}", query);
        Page<IndustrySecondClass> page = industrySecondClassService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/industry-second-classes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
