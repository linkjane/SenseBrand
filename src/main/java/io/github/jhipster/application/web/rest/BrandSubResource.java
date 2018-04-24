package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.BrandSub;
import io.github.jhipster.application.service.BrandSubService;
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
 * REST controller for managing BrandSub.
 */
@RestController
@RequestMapping("/api")
public class BrandSubResource {

    private final Logger log = LoggerFactory.getLogger(BrandSubResource.class);

    private static final String ENTITY_NAME = "brandSub";

    private final BrandSubService brandSubService;

    public BrandSubResource(BrandSubService brandSubService) {
        this.brandSubService = brandSubService;
    }

    /**
     * POST  /brand-subs : Create a new brandSub.
     *
     * @param brandSub the brandSub to create
     * @return the ResponseEntity with status 201 (Created) and with body the new brandSub, or with status 400 (Bad Request) if the brandSub has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/brand-subs")
    @Timed
    public ResponseEntity<BrandSub> createBrandSub(@Valid @RequestBody BrandSub brandSub) throws URISyntaxException {
        log.debug("REST request to save BrandSub : {}", brandSub);
        if (brandSub.getId() != null) {
            throw new BadRequestAlertException("A new brandSub cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BrandSub result = brandSubService.save(brandSub);
        return ResponseEntity.created(new URI("/api/brand-subs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /brand-subs : Updates an existing brandSub.
     *
     * @param brandSub the brandSub to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated brandSub,
     * or with status 400 (Bad Request) if the brandSub is not valid,
     * or with status 500 (Internal Server Error) if the brandSub couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/brand-subs")
    @Timed
    public ResponseEntity<BrandSub> updateBrandSub(@Valid @RequestBody BrandSub brandSub) throws URISyntaxException {
        log.debug("REST request to update BrandSub : {}", brandSub);
        if (brandSub.getId() == null) {
            return createBrandSub(brandSub);
        }
        BrandSub result = brandSubService.save(brandSub);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, brandSub.getId().toString()))
            .body(result);
    }

    /**
     * GET  /brand-subs : get all the brandSubs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of brandSubs in body
     */
    @GetMapping("/brand-subs")
    @Timed
    public ResponseEntity<List<BrandSub>> getAllBrandSubs(Pageable pageable) {
        log.debug("REST request to get a page of BrandSubs");
        Page<BrandSub> page = brandSubService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/brand-subs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /brand-subs/:id : get the "id" brandSub.
     *
     * @param id the id of the brandSub to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the brandSub, or with status 404 (Not Found)
     */
    @GetMapping("/brand-subs/{id}")
    @Timed
    public ResponseEntity<BrandSub> getBrandSub(@PathVariable Long id) {
        log.debug("REST request to get BrandSub : {}", id);
        BrandSub brandSub = brandSubService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(brandSub));
    }

    /**
     * DELETE  /brand-subs/:id : delete the "id" brandSub.
     *
     * @param id the id of the brandSub to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/brand-subs/{id}")
    @Timed
    public ResponseEntity<Void> deleteBrandSub(@PathVariable Long id) {
        log.debug("REST request to delete BrandSub : {}", id);
        brandSubService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/brand-subs?query=:query : search for the brandSub corresponding
     * to the query.
     *
     * @param query the query of the brandSub search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/brand-subs")
    @Timed
    public ResponseEntity<List<BrandSub>> searchBrandSubs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of BrandSubs for query {}", query);
        Page<BrandSub> page = brandSubService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/brand-subs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
