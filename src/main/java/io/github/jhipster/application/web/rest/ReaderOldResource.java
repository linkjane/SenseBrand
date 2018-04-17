package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.ReaderOld;

import io.github.jhipster.application.repository.ReaderOldRepository;
import io.github.jhipster.application.repository.search.ReaderOldSearchRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ReaderOld.
 */
@RestController
@RequestMapping("/api")
public class ReaderOldResource {

    private final Logger log = LoggerFactory.getLogger(ReaderOldResource.class);

    private static final String ENTITY_NAME = "readerOld";

    private final ReaderOldRepository readerOldRepository;

    private final ReaderOldSearchRepository readerOldSearchRepository;

    public ReaderOldResource(ReaderOldRepository readerOldRepository, ReaderOldSearchRepository readerOldSearchRepository) {
        this.readerOldRepository = readerOldRepository;
        this.readerOldSearchRepository = readerOldSearchRepository;
    }

    /**
     * POST  /reader-olds : Create a new readerOld.
     *
     * @param readerOld the readerOld to create
     * @return the ResponseEntity with status 201 (Created) and with body the new readerOld, or with status 400 (Bad Request) if the readerOld has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reader-olds")
    @Timed
    public ResponseEntity<ReaderOld> createReaderOld(@Valid @RequestBody ReaderOld readerOld) throws URISyntaxException {
        log.debug("REST request to save ReaderOld : {}", readerOld);
        if (readerOld.getId() != null) {
            throw new BadRequestAlertException("A new readerOld cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReaderOld result = readerOldRepository.save(readerOld);
        readerOldSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/reader-olds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /reader-olds : Updates an existing readerOld.
     *
     * @param readerOld the readerOld to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated readerOld,
     * or with status 400 (Bad Request) if the readerOld is not valid,
     * or with status 500 (Internal Server Error) if the readerOld couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reader-olds")
    @Timed
    public ResponseEntity<ReaderOld> updateReaderOld(@Valid @RequestBody ReaderOld readerOld) throws URISyntaxException {
        log.debug("REST request to update ReaderOld : {}", readerOld);
        if (readerOld.getId() == null) {
            return createReaderOld(readerOld);
        }
        ReaderOld result = readerOldRepository.save(readerOld);
        readerOldSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, readerOld.getId().toString()))
            .body(result);
    }

    /**
     * GET  /reader-olds : get all the readerOlds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of readerOlds in body
     */
    @GetMapping("/reader-olds")
    @Timed
    public List<ReaderOld> getAllReaderOlds() {
        log.debug("REST request to get all ReaderOlds");
        return readerOldRepository.findAll();
        }

    /**
     * GET  /reader-olds/:id : get the "id" readerOld.
     *
     * @param id the id of the readerOld to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the readerOld, or with status 404 (Not Found)
     */
    @GetMapping("/reader-olds/{id}")
    @Timed
    public ResponseEntity<ReaderOld> getReaderOld(@PathVariable Long id) {
        log.debug("REST request to get ReaderOld : {}", id);
        ReaderOld readerOld = readerOldRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(readerOld));
    }

    /**
     * DELETE  /reader-olds/:id : delete the "id" readerOld.
     *
     * @param id the id of the readerOld to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reader-olds/{id}")
    @Timed
    public ResponseEntity<Void> deleteReaderOld(@PathVariable Long id) {
        log.debug("REST request to delete ReaderOld : {}", id);
        readerOldRepository.delete(id);
        readerOldSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/reader-olds?query=:query : search for the readerOld corresponding
     * to the query.
     *
     * @param query the query of the readerOld search
     * @return the result of the search
     */
    @GetMapping("/_search/reader-olds")
    @Timed
    public List<ReaderOld> searchReaderOlds(@RequestParam String query) {
        log.debug("REST request to search ReaderOlds for query {}", query);
        return StreamSupport
            .stream(readerOldSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
