package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.MyFile;

import io.github.jhipster.application.repository.MyFileRepository;
import io.github.jhipster.application.repository.search.MyFileSearchRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MyFile.
 */
@RestController
@RequestMapping("/api")
public class MyFileResource {

    private final Logger log = LoggerFactory.getLogger(MyFileResource.class);

    private static final String ENTITY_NAME = "myFile";

    private final MyFileRepository myFileRepository;

    private final MyFileSearchRepository myFileSearchRepository;

    public MyFileResource(MyFileRepository myFileRepository, MyFileSearchRepository myFileSearchRepository) {
        this.myFileRepository = myFileRepository;
        this.myFileSearchRepository = myFileSearchRepository;
    }

    /**
     * POST  /my-files : Create a new myFile.
     *
     * @param myFile the myFile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new myFile, or with status 400 (Bad Request) if the myFile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/my-files")
    @Timed
    public ResponseEntity<MyFile> createMyFile(@Valid @RequestBody MyFile myFile) throws URISyntaxException {
        log.debug("REST request to save MyFile : {}", myFile);
        if (myFile.getId() != null) {
            throw new BadRequestAlertException("A new myFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MyFile result = myFileRepository.save(myFile);
        myFileSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/my-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /my-files : Updates an existing myFile.
     *
     * @param myFile the myFile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated myFile,
     * or with status 400 (Bad Request) if the myFile is not valid,
     * or with status 500 (Internal Server Error) if the myFile couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/my-files")
    @Timed
    public ResponseEntity<MyFile> updateMyFile(@Valid @RequestBody MyFile myFile) throws URISyntaxException {
        log.debug("REST request to update MyFile : {}", myFile);
        if (myFile.getId() == null) {
            return createMyFile(myFile);
        }
        MyFile result = myFileRepository.save(myFile);
        myFileSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, myFile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /my-files : get all the myFiles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of myFiles in body
     */
    @GetMapping("/my-files")
    @Timed
    public ResponseEntity<List<MyFile>> getAllMyFiles(Pageable pageable) {
        log.debug("REST request to get a page of MyFiles");
        Page<MyFile> page = myFileRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/my-files");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /my-files/:id : get the "id" myFile.
     *
     * @param id the id of the myFile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the myFile, or with status 404 (Not Found)
     */
    @GetMapping("/my-files/{id}")
    @Timed
    public ResponseEntity<MyFile> getMyFile(@PathVariable Long id) {
        log.debug("REST request to get MyFile : {}", id);
        MyFile myFile = myFileRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(myFile));
    }

    /**
     * DELETE  /my-files/:id : delete the "id" myFile.
     *
     * @param id the id of the myFile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/my-files/{id}")
    @Timed
    public ResponseEntity<Void> deleteMyFile(@PathVariable Long id) {
        log.debug("REST request to delete MyFile : {}", id);
        myFileRepository.delete(id);
        myFileSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/my-files?query=:query : search for the myFile corresponding
     * to the query.
     *
     * @param query the query of the myFile search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/my-files")
    @Timed
    public ResponseEntity<List<MyFile>> searchMyFiles(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of MyFiles for query {}", query);
        Page<MyFile> page = myFileSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/my-files");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
