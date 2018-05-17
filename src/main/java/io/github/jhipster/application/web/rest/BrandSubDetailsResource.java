package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.BrandSubDetails;
import io.github.jhipster.application.service.BrandSubDetailsService;
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
 * REST controller for managing BrandSubDetails.
 */
@RestController
@RequestMapping("/api")
public class BrandSubDetailsResource {

    private final Logger log = LoggerFactory.getLogger(BrandSubDetailsResource.class);

    private static final String ENTITY_NAME = "brandSubDetails";

    private final BrandSubDetailsService brandSubDetailsService;

    public BrandSubDetailsResource(BrandSubDetailsService brandSubDetailsService) {
        this.brandSubDetailsService = brandSubDetailsService;
    }

    /**
     * POST  /brand-sub-details : Create a new brandSubDetails.
     *
     * @param brandSubDetails the brandSubDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new brandSubDetails, or with status 400 (Bad Request) if the brandSubDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/brand-sub-details")
    @Timed
    public ResponseEntity<BrandSubDetails> createBrandSubDetails(@Valid @RequestBody BrandSubDetails brandSubDetails) throws URISyntaxException {
        log.debug("REST request to save BrandSubDetails : {}", brandSubDetails);
        if (brandSubDetails.getId() != null) {
            throw new BadRequestAlertException("A new brandSubDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BrandSubDetails result = brandSubDetailsService.save(brandSubDetails);
        return ResponseEntity.created(new URI("/api/brand-sub-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /brand-sub-details : Updates an existing brandSubDetails.
     *
     * @param brandSubDetails the brandSubDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated brandSubDetails,
     * or with status 400 (Bad Request) if the brandSubDetails is not valid,
     * or with status 500 (Internal Server Error) if the brandSubDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/brand-sub-details")
    @Timed
    public ResponseEntity<BrandSubDetails> updateBrandSubDetails(@Valid @RequestBody BrandSubDetails brandSubDetails) throws URISyntaxException {
        log.debug("REST request to update BrandSubDetails : {}", brandSubDetails);
        if (brandSubDetails.getId() == null) {
            return createBrandSubDetails(brandSubDetails);
        }
        BrandSubDetails result = brandSubDetailsService.save(brandSubDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, brandSubDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /brand-sub-details : get all the brandSubDetails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of brandSubDetails in body
     */
    @GetMapping("/brand-sub-details")
    @Timed
    public ResponseEntity<List<BrandSubDetails>> getAllBrandSubDetails(Pageable pageable) {
        log.debug("REST request to get a page of BrandSubDetails");
        Page<BrandSubDetails> page = brandSubDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/brand-sub-details");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /brand-sub-details/:id : get the "id" brandSubDetails.
     *
     * @param id the id of the brandSubDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the brandSubDetails, or with status 404 (Not Found)
     */
    @GetMapping("/brand-sub-details/{id}")
    @Timed
    public ResponseEntity<BrandSubDetails> getBrandSubDetails(@PathVariable Long id) {
        log.debug("REST request to get BrandSubDetails : {}", id);
        BrandSubDetails brandSubDetails = brandSubDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(brandSubDetails));
    }

    /**
     * DELETE  /brand-sub-details/:id : delete the "id" brandSubDetails.
     *
     * @param id the id of the brandSubDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/brand-sub-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteBrandSubDetails(@PathVariable Long id) {
        log.debug("REST request to delete BrandSubDetails : {}", id);
        brandSubDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/brand-sub-details?query=:query : search for the brandSubDetails corresponding
     * to the query.
     *
     * @param query the query of the brandSubDetails search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/brand-sub-details")
    @Timed
    public ResponseEntity<List<BrandSubDetails>> searchBrandSubDetails(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BrandSubDetails for query {}", query);
        Page<BrandSubDetails> page = brandSubDetailsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/brand-sub-details");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
