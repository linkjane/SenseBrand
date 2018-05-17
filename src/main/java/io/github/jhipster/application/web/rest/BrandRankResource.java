package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.BrandRank;
import io.github.jhipster.application.service.BrandRankService;
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
 * REST controller for managing BrandRank.
 */
@RestController
@RequestMapping("/api")
public class BrandRankResource {

    private final Logger log = LoggerFactory.getLogger(BrandRankResource.class);

    private static final String ENTITY_NAME = "brandRank";

    private final BrandRankService brandRankService;

    public BrandRankResource(BrandRankService brandRankService) {
        this.brandRankService = brandRankService;
    }

    /**
     * POST  /brand-ranks : Create a new brandRank.
     *
     * @param brandRank the brandRank to create
     * @return the ResponseEntity with status 201 (Created) and with body the new brandRank, or with status 400 (Bad Request) if the brandRank has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/brand-ranks")
    @Timed
    public ResponseEntity<BrandRank> createBrandRank(@Valid @RequestBody BrandRank brandRank) throws URISyntaxException {
        log.debug("REST request to save BrandRank : {}", brandRank);
        if (brandRank.getId() != null) {
            throw new BadRequestAlertException("A new brandRank cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BrandRank result = brandRankService.save(brandRank);
        return ResponseEntity.created(new URI("/api/brand-ranks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /brand-ranks : Updates an existing brandRank.
     *
     * @param brandRank the brandRank to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated brandRank,
     * or with status 400 (Bad Request) if the brandRank is not valid,
     * or with status 500 (Internal Server Error) if the brandRank couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/brand-ranks")
    @Timed
    public ResponseEntity<BrandRank> updateBrandRank(@Valid @RequestBody BrandRank brandRank) throws URISyntaxException {
        log.debug("REST request to update BrandRank : {}", brandRank);
        if (brandRank.getId() == null) {
            return createBrandRank(brandRank);
        }
        BrandRank result = brandRankService.save(brandRank);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, brandRank.getId().toString()))
            .body(result);
    }

    /**
     * GET  /brand-ranks : get all the brandRanks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of brandRanks in body
     */
    @GetMapping("/brand-ranks")
    @Timed
    public ResponseEntity<List<BrandRank>> getAllBrandRanks(Pageable pageable) {
        log.debug("REST request to get a page of BrandRanks");
        Page<BrandRank> page = brandRankService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/brand-ranks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /brand-ranks/:id : get the "id" brandRank.
     *
     * @param id the id of the brandRank to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the brandRank, or with status 404 (Not Found)
     */
    @GetMapping("/brand-ranks/{id}")
    @Timed
    public ResponseEntity<BrandRank> getBrandRank(@PathVariable Long id) {
        log.debug("REST request to get BrandRank : {}", id);
        BrandRank brandRank = brandRankService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(brandRank));
    }

    /**
     * DELETE  /brand-ranks/:id : delete the "id" brandRank.
     *
     * @param id the id of the brandRank to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/brand-ranks/{id}")
    @Timed
    public ResponseEntity<Void> deleteBrandRank(@PathVariable Long id) {
        log.debug("REST request to delete BrandRank : {}", id);
        brandRankService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/brand-ranks?query=:query : search for the brandRank corresponding
     * to the query.
     *
     * @param query the query of the brandRank search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/brand-ranks")
    @Timed
    public ResponseEntity<List<BrandRank>> searchBrandRanks(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BrandRanks for query {}", query);
        Page<BrandRank> page = brandRankService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/brand-ranks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
