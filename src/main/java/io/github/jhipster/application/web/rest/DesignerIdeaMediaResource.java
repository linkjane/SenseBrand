package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.DesignerIdeaMedia;
import io.github.jhipster.application.service.DesignerIdeaMediaService;
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
 * REST controller for managing DesignerIdeaMedia.
 */
@RestController
@RequestMapping("/api")
public class DesignerIdeaMediaResource {

    private final Logger log = LoggerFactory.getLogger(DesignerIdeaMediaResource.class);

    private static final String ENTITY_NAME = "designerIdeaMedia";

    private final DesignerIdeaMediaService designerIdeaMediaService;

    public DesignerIdeaMediaResource(DesignerIdeaMediaService designerIdeaMediaService) {
        this.designerIdeaMediaService = designerIdeaMediaService;
    }

    /**
     * POST  /designer-idea-medias : Create a new designerIdeaMedia.
     *
     * @param designerIdeaMedia the designerIdeaMedia to create
     * @return the ResponseEntity with status 201 (Created) and with body the new designerIdeaMedia, or with status 400 (Bad Request) if the designerIdeaMedia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/designer-idea-medias")
    @Timed
    public ResponseEntity<DesignerIdeaMedia> createDesignerIdeaMedia(@Valid @RequestBody DesignerIdeaMedia designerIdeaMedia) throws URISyntaxException {
        log.debug("REST request to save DesignerIdeaMedia : {}", designerIdeaMedia);
        if (designerIdeaMedia.getId() != null) {
            throw new BadRequestAlertException("A new designerIdeaMedia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DesignerIdeaMedia result = designerIdeaMediaService.save(designerIdeaMedia);
        return ResponseEntity.created(new URI("/api/designer-idea-medias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /designer-idea-medias : Updates an existing designerIdeaMedia.
     *
     * @param designerIdeaMedia the designerIdeaMedia to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated designerIdeaMedia,
     * or with status 400 (Bad Request) if the designerIdeaMedia is not valid,
     * or with status 500 (Internal Server Error) if the designerIdeaMedia couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/designer-idea-medias")
    @Timed
    public ResponseEntity<DesignerIdeaMedia> updateDesignerIdeaMedia(@Valid @RequestBody DesignerIdeaMedia designerIdeaMedia) throws URISyntaxException {
        log.debug("REST request to update DesignerIdeaMedia : {}", designerIdeaMedia);
        if (designerIdeaMedia.getId() == null) {
            return createDesignerIdeaMedia(designerIdeaMedia);
        }
        DesignerIdeaMedia result = designerIdeaMediaService.save(designerIdeaMedia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, designerIdeaMedia.getId().toString()))
            .body(result);
    }

    /**
     * GET  /designer-idea-medias : get all the designerIdeaMedias.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of designerIdeaMedias in body
     */
    @GetMapping("/designer-idea-medias")
    @Timed
    public ResponseEntity<List<DesignerIdeaMedia>> getAllDesignerIdeaMedias(Pageable pageable) {
        log.debug("REST request to get a page of DesignerIdeaMedias");
        Page<DesignerIdeaMedia> page = designerIdeaMediaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/designer-idea-medias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /designer-idea-medias/:id : get the "id" designerIdeaMedia.
     *
     * @param id the id of the designerIdeaMedia to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the designerIdeaMedia, or with status 404 (Not Found)
     */
    @GetMapping("/designer-idea-medias/{id}")
    @Timed
    public ResponseEntity<DesignerIdeaMedia> getDesignerIdeaMedia(@PathVariable Long id) {
        log.debug("REST request to get DesignerIdeaMedia : {}", id);
        DesignerIdeaMedia designerIdeaMedia = designerIdeaMediaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(designerIdeaMedia));
    }

    /**
     * DELETE  /designer-idea-medias/:id : delete the "id" designerIdeaMedia.
     *
     * @param id the id of the designerIdeaMedia to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/designer-idea-medias/{id}")
    @Timed
    public ResponseEntity<Void> deleteDesignerIdeaMedia(@PathVariable Long id) {
        log.debug("REST request to delete DesignerIdeaMedia : {}", id);
        designerIdeaMediaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/designer-idea-medias?query=:query : search for the designerIdeaMedia corresponding
     * to the query.
     *
     * @param query the query of the designerIdeaMedia search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/designer-idea-medias")
    @Timed
    public ResponseEntity<List<DesignerIdeaMedia>> searchDesignerIdeaMedias(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DesignerIdeaMedias for query {}", query);
        Page<DesignerIdeaMedia> page = designerIdeaMediaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/designer-idea-medias");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
