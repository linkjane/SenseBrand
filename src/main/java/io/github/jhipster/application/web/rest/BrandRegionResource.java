package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.BrandRegion;
import io.github.jhipster.application.service.BrandRegionService;
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
 * REST controller for managing BrandRegion.
 */
@RestController
@RequestMapping("/api")
public class BrandRegionResource {

    private final Logger log = LoggerFactory.getLogger(BrandRegionResource.class);

    private static final String ENTITY_NAME = "brandRegion";

    private final BrandRegionService brandRegionService;

    public BrandRegionResource(BrandRegionService brandRegionService) {
        this.brandRegionService = brandRegionService;
    }

    /**
     * POST  /brand-regions : Create a new brandRegion.
     *
     * @param brandRegion the brandRegion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new brandRegion, or with status 400 (Bad Request) if the brandRegion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/brand-regions")
    @Timed
    public ResponseEntity<BrandRegion> createBrandRegion(@Valid @RequestBody BrandRegion brandRegion) throws URISyntaxException {
        log.debug("REST request to save BrandRegion : {}", brandRegion);
        if (brandRegion.getId() != null) {
            throw new BadRequestAlertException("A new brandRegion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BrandRegion result = brandRegionService.save(brandRegion);
        return ResponseEntity.created(new URI("/api/brand-regions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /brand-regions : Updates an existing brandRegion.
     *
     * @param brandRegion the brandRegion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated brandRegion,
     * or with status 400 (Bad Request) if the brandRegion is not valid,
     * or with status 500 (Internal Server Error) if the brandRegion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/brand-regions")
    @Timed
    public ResponseEntity<BrandRegion> updateBrandRegion(@Valid @RequestBody BrandRegion brandRegion) throws URISyntaxException {
        log.debug("REST request to update BrandRegion : {}", brandRegion);
        if (brandRegion.getId() == null) {
            return createBrandRegion(brandRegion);
        }
        BrandRegion result = brandRegionService.save(brandRegion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, brandRegion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /brand-regions : get all the brandRegions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of brandRegions in body
     */
    @GetMapping("/brand-regions")
    @Timed
    public ResponseEntity<List<BrandRegion>> getAllBrandRegions(Pageable pageable) {
        log.debug("REST request to get a page of BrandRegions");
        Page<BrandRegion> page = brandRegionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/brand-regions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /brand-regions/:id : get the "id" brandRegion.
     *
     * @param id the id of the brandRegion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the brandRegion, or with status 404 (Not Found)
     */
    @GetMapping("/brand-regions/{id}")
    @Timed
    public ResponseEntity<BrandRegion> getBrandRegion(@PathVariable Long id) {
        log.debug("REST request to get BrandRegion : {}", id);
        BrandRegion brandRegion = brandRegionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(brandRegion));
    }

    /**
     * DELETE  /brand-regions/:id : delete the "id" brandRegion.
     *
     * @param id the id of the brandRegion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/brand-regions/{id}")
    @Timed
    public ResponseEntity<Void> deleteBrandRegion(@PathVariable Long id) {
        log.debug("REST request to delete BrandRegion : {}", id);
        brandRegionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/brand-regions?query=:query : search for the brandRegion corresponding
     * to the query.
     *
     * @param query the query of the brandRegion search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/brand-regions")
    @Timed
    public ResponseEntity<List<BrandRegion>> searchBrandRegions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BrandRegions for query {}", query);
        Page<BrandRegion> page = brandRegionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/brand-regions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
