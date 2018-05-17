package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.DesignerShowImg;
import io.github.jhipster.application.service.DesignerShowImgService;
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
 * REST controller for managing DesignerShowImg.
 */
@RestController
@RequestMapping("/api")
public class DesignerShowImgResource {

    private final Logger log = LoggerFactory.getLogger(DesignerShowImgResource.class);

    private static final String ENTITY_NAME = "designerShowImg";

    private final DesignerShowImgService designerShowImgService;

    public DesignerShowImgResource(DesignerShowImgService designerShowImgService) {
        this.designerShowImgService = designerShowImgService;
    }

    /**
     * POST  /designer-show-imgs : Create a new designerShowImg.
     *
     * @param designerShowImg the designerShowImg to create
     * @return the ResponseEntity with status 201 (Created) and with body the new designerShowImg, or with status 400 (Bad Request) if the designerShowImg has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/designer-show-imgs")
    @Timed
    public ResponseEntity<DesignerShowImg> createDesignerShowImg(@Valid @RequestBody DesignerShowImg designerShowImg) throws URISyntaxException {
        log.debug("REST request to save DesignerShowImg : {}", designerShowImg);
        if (designerShowImg.getId() != null) {
            throw new BadRequestAlertException("A new designerShowImg cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DesignerShowImg result = designerShowImgService.save(designerShowImg);
        return ResponseEntity.created(new URI("/api/designer-show-imgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /designer-show-imgs : Updates an existing designerShowImg.
     *
     * @param designerShowImg the designerShowImg to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated designerShowImg,
     * or with status 400 (Bad Request) if the designerShowImg is not valid,
     * or with status 500 (Internal Server Error) if the designerShowImg couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/designer-show-imgs")
    @Timed
    public ResponseEntity<DesignerShowImg> updateDesignerShowImg(@Valid @RequestBody DesignerShowImg designerShowImg) throws URISyntaxException {
        log.debug("REST request to update DesignerShowImg : {}", designerShowImg);
        if (designerShowImg.getId() == null) {
            return createDesignerShowImg(designerShowImg);
        }
        DesignerShowImg result = designerShowImgService.save(designerShowImg);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, designerShowImg.getId().toString()))
            .body(result);
    }

    /**
     * GET  /designer-show-imgs : get all the designerShowImgs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of designerShowImgs in body
     */
    @GetMapping("/designer-show-imgs")
    @Timed
    public ResponseEntity<List<DesignerShowImg>> getAllDesignerShowImgs(Pageable pageable) {
        log.debug("REST request to get a page of DesignerShowImgs");
        Page<DesignerShowImg> page = designerShowImgService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/designer-show-imgs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /designer-show-imgs/:id : get the "id" designerShowImg.
     *
     * @param id the id of the designerShowImg to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the designerShowImg, or with status 404 (Not Found)
     */
    @GetMapping("/designer-show-imgs/{id}")
    @Timed
    public ResponseEntity<DesignerShowImg> getDesignerShowImg(@PathVariable Long id) {
        log.debug("REST request to get DesignerShowImg : {}", id);
        DesignerShowImg designerShowImg = designerShowImgService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(designerShowImg));
    }

    /**
     * DELETE  /designer-show-imgs/:id : delete the "id" designerShowImg.
     *
     * @param id the id of the designerShowImg to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/designer-show-imgs/{id}")
    @Timed
    public ResponseEntity<Void> deleteDesignerShowImg(@PathVariable Long id) {
        log.debug("REST request to delete DesignerShowImg : {}", id);
        designerShowImgService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/designer-show-imgs?query=:query : search for the designerShowImg corresponding
     * to the query.
     *
     * @param query the query of the designerShowImg search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/designer-show-imgs")
    @Timed
    public ResponseEntity<List<DesignerShowImg>> searchDesignerShowImgs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DesignerShowImgs for query {}", query);
        Page<DesignerShowImg> page = designerShowImgService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/designer-show-imgs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
