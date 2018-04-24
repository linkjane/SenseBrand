package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Industry;
import io.github.jhipster.application.service.IndustryService;
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
 * REST controller for managing Industry.
 */
@RestController
@RequestMapping("/api")
public class IndustryResource {

    private final Logger log = LoggerFactory.getLogger(IndustryResource.class);

    private static final String ENTITY_NAME = "industry";

    private final IndustryService industryService;

    public IndustryResource(IndustryService industryService) {
        this.industryService = industryService;
    }

    /**
     * POST  /industries : Create a new industry.
     *
     * @param industry the industry to create
     * @return the ResponseEntity with status 201 (Created) and with body the new industry, or with status 400 (Bad Request) if the industry has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/industries")
    @Timed
    public ResponseEntity<Industry> createIndustry(@Valid @RequestBody Industry industry) throws URISyntaxException {
        log.debug("REST request to save Industry : {}", industry);
        if (industry.getId() != null) {
            throw new BadRequestAlertException("A new industry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Industry result = industryService.save(industry);
        return ResponseEntity.created(new URI("/api/industries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /industries : Updates an existing industry.
     *
     * @param industry the industry to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated industry,
     * or with status 400 (Bad Request) if the industry is not valid,
     * or with status 500 (Internal Server Error) if the industry couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/industries")
    @Timed
    public ResponseEntity<Industry> updateIndustry(@Valid @RequestBody Industry industry) throws URISyntaxException {
        log.debug("REST request to update Industry : {}", industry);
        if (industry.getId() == null) {
            return createIndustry(industry);
        }
        Industry result = industryService.save(industry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, industry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /industries : get all the industries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of industries in body
     */
    @GetMapping("/industries")
    @Timed
    public ResponseEntity<List<Industry>> getAllIndustries(Pageable pageable) {
        log.debug("REST request to get a page of Industries");
        Page<Industry> page = industryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/industries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /industries/:id : get the "id" industry.
     *
     * @param id the id of the industry to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the industry, or with status 404 (Not Found)
     */
    @GetMapping("/industries/{id}")
    @Timed
    public ResponseEntity<Industry> getIndustry(@PathVariable Long id) {
        log.debug("REST request to get Industry : {}", id);
        Industry industry = industryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(industry));
    }

    /**
     * DELETE  /industries/:id : delete the "id" industry.
     *
     * @param id the id of the industry to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/industries/{id}")
    @Timed
    public ResponseEntity<Void> deleteIndustry(@PathVariable Long id) {
        log.debug("REST request to delete Industry : {}", id);
        industryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/industries?query=:query : search for the industry corresponding
     * to the query.
     *
     * @param query the query of the industry search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/industries")
    @Timed
    public ResponseEntity<List<Industry>> searchIndustries(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Industries for query {}", query);
        Page<Industry> page = industryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/industries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
