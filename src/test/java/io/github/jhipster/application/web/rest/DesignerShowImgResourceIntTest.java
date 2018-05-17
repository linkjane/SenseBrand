package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.DesignerShowImg;
import io.github.jhipster.application.repository.DesignerShowImgRepository;
import io.github.jhipster.application.service.DesignerShowImgService;
import io.github.jhipster.application.repository.search.DesignerShowImgSearchRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DesignerShowImgResource REST controller.
 *
 * @see DesignerShowImgResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class DesignerShowImgResourceIntTest {

    private static final String DEFAULT_IMG_TILE = "AAAAAAAAAA";
    private static final String UPDATED_IMG_TILE = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_FILE = "AAAAAAAAAA";
    private static final String UPDATED_IMG_FILE = "BBBBBBBBBB";

    @Autowired
    private DesignerShowImgRepository designerShowImgRepository;

    @Autowired
    private DesignerShowImgService designerShowImgService;

    @Autowired
    private DesignerShowImgSearchRepository designerShowImgSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDesignerShowImgMockMvc;

    private DesignerShowImg designerShowImg;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DesignerShowImgResource designerShowImgResource = new DesignerShowImgResource(designerShowImgService);
        this.restDesignerShowImgMockMvc = MockMvcBuilders.standaloneSetup(designerShowImgResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DesignerShowImg createEntity(EntityManager em) {
        DesignerShowImg designerShowImg = new DesignerShowImg()
            .imgTile(DEFAULT_IMG_TILE)
            .imgFile(DEFAULT_IMG_FILE);
        return designerShowImg;
    }

    @Before
    public void initTest() {
        designerShowImgSearchRepository.deleteAll();
        designerShowImg = createEntity(em);
    }

    @Test
    @Transactional
    public void createDesignerShowImg() throws Exception {
        int databaseSizeBeforeCreate = designerShowImgRepository.findAll().size();

        // Create the DesignerShowImg
        restDesignerShowImgMockMvc.perform(post("/api/designer-show-imgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerShowImg)))
            .andExpect(status().isCreated());

        // Validate the DesignerShowImg in the database
        List<DesignerShowImg> designerShowImgList = designerShowImgRepository.findAll();
        assertThat(designerShowImgList).hasSize(databaseSizeBeforeCreate + 1);
        DesignerShowImg testDesignerShowImg = designerShowImgList.get(designerShowImgList.size() - 1);
        assertThat(testDesignerShowImg.getImgTile()).isEqualTo(DEFAULT_IMG_TILE);
        assertThat(testDesignerShowImg.getImgFile()).isEqualTo(DEFAULT_IMG_FILE);

        // Validate the DesignerShowImg in Elasticsearch
        DesignerShowImg designerShowImgEs = designerShowImgSearchRepository.findOne(testDesignerShowImg.getId());
        assertThat(designerShowImgEs).isEqualToIgnoringGivenFields(testDesignerShowImg);
    }

    @Test
    @Transactional
    public void createDesignerShowImgWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = designerShowImgRepository.findAll().size();

        // Create the DesignerShowImg with an existing ID
        designerShowImg.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignerShowImgMockMvc.perform(post("/api/designer-show-imgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerShowImg)))
            .andExpect(status().isBadRequest());

        // Validate the DesignerShowImg in the database
        List<DesignerShowImg> designerShowImgList = designerShowImgRepository.findAll();
        assertThat(designerShowImgList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkImgTileIsRequired() throws Exception {
        int databaseSizeBeforeTest = designerShowImgRepository.findAll().size();
        // set the field null
        designerShowImg.setImgTile(null);

        // Create the DesignerShowImg, which fails.

        restDesignerShowImgMockMvc.perform(post("/api/designer-show-imgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerShowImg)))
            .andExpect(status().isBadRequest());

        List<DesignerShowImg> designerShowImgList = designerShowImgRepository.findAll();
        assertThat(designerShowImgList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImgFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = designerShowImgRepository.findAll().size();
        // set the field null
        designerShowImg.setImgFile(null);

        // Create the DesignerShowImg, which fails.

        restDesignerShowImgMockMvc.perform(post("/api/designer-show-imgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerShowImg)))
            .andExpect(status().isBadRequest());

        List<DesignerShowImg> designerShowImgList = designerShowImgRepository.findAll();
        assertThat(designerShowImgList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDesignerShowImgs() throws Exception {
        // Initialize the database
        designerShowImgRepository.saveAndFlush(designerShowImg);

        // Get all the designerShowImgList
        restDesignerShowImgMockMvc.perform(get("/api/designer-show-imgs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designerShowImg.getId().intValue())))
            .andExpect(jsonPath("$.[*].imgTile").value(hasItem(DEFAULT_IMG_TILE.toString())))
            .andExpect(jsonPath("$.[*].imgFile").value(hasItem(DEFAULT_IMG_FILE.toString())));
    }

    @Test
    @Transactional
    public void getDesignerShowImg() throws Exception {
        // Initialize the database
        designerShowImgRepository.saveAndFlush(designerShowImg);

        // Get the designerShowImg
        restDesignerShowImgMockMvc.perform(get("/api/designer-show-imgs/{id}", designerShowImg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(designerShowImg.getId().intValue()))
            .andExpect(jsonPath("$.imgTile").value(DEFAULT_IMG_TILE.toString()))
            .andExpect(jsonPath("$.imgFile").value(DEFAULT_IMG_FILE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDesignerShowImg() throws Exception {
        // Get the designerShowImg
        restDesignerShowImgMockMvc.perform(get("/api/designer-show-imgs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDesignerShowImg() throws Exception {
        // Initialize the database
        designerShowImgService.save(designerShowImg);

        int databaseSizeBeforeUpdate = designerShowImgRepository.findAll().size();

        // Update the designerShowImg
        DesignerShowImg updatedDesignerShowImg = designerShowImgRepository.findOne(designerShowImg.getId());
        // Disconnect from session so that the updates on updatedDesignerShowImg are not directly saved in db
        em.detach(updatedDesignerShowImg);
        updatedDesignerShowImg
            .imgTile(UPDATED_IMG_TILE)
            .imgFile(UPDATED_IMG_FILE);

        restDesignerShowImgMockMvc.perform(put("/api/designer-show-imgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDesignerShowImg)))
            .andExpect(status().isOk());

        // Validate the DesignerShowImg in the database
        List<DesignerShowImg> designerShowImgList = designerShowImgRepository.findAll();
        assertThat(designerShowImgList).hasSize(databaseSizeBeforeUpdate);
        DesignerShowImg testDesignerShowImg = designerShowImgList.get(designerShowImgList.size() - 1);
        assertThat(testDesignerShowImg.getImgTile()).isEqualTo(UPDATED_IMG_TILE);
        assertThat(testDesignerShowImg.getImgFile()).isEqualTo(UPDATED_IMG_FILE);

        // Validate the DesignerShowImg in Elasticsearch
        DesignerShowImg designerShowImgEs = designerShowImgSearchRepository.findOne(testDesignerShowImg.getId());
        assertThat(designerShowImgEs).isEqualToIgnoringGivenFields(testDesignerShowImg);
    }

    @Test
    @Transactional
    public void updateNonExistingDesignerShowImg() throws Exception {
        int databaseSizeBeforeUpdate = designerShowImgRepository.findAll().size();

        // Create the DesignerShowImg

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDesignerShowImgMockMvc.perform(put("/api/designer-show-imgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerShowImg)))
            .andExpect(status().isCreated());

        // Validate the DesignerShowImg in the database
        List<DesignerShowImg> designerShowImgList = designerShowImgRepository.findAll();
        assertThat(designerShowImgList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDesignerShowImg() throws Exception {
        // Initialize the database
        designerShowImgService.save(designerShowImg);

        int databaseSizeBeforeDelete = designerShowImgRepository.findAll().size();

        // Get the designerShowImg
        restDesignerShowImgMockMvc.perform(delete("/api/designer-show-imgs/{id}", designerShowImg.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean designerShowImgExistsInEs = designerShowImgSearchRepository.exists(designerShowImg.getId());
        assertThat(designerShowImgExistsInEs).isFalse();

        // Validate the database is empty
        List<DesignerShowImg> designerShowImgList = designerShowImgRepository.findAll();
        assertThat(designerShowImgList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDesignerShowImg() throws Exception {
        // Initialize the database
        designerShowImgService.save(designerShowImg);

        // Search the designerShowImg
        restDesignerShowImgMockMvc.perform(get("/api/_search/designer-show-imgs?query=id:" + designerShowImg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designerShowImg.getId().intValue())))
            .andExpect(jsonPath("$.[*].imgTile").value(hasItem(DEFAULT_IMG_TILE.toString())))
            .andExpect(jsonPath("$.[*].imgFile").value(hasItem(DEFAULT_IMG_FILE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesignerShowImg.class);
        DesignerShowImg designerShowImg1 = new DesignerShowImg();
        designerShowImg1.setId(1L);
        DesignerShowImg designerShowImg2 = new DesignerShowImg();
        designerShowImg2.setId(designerShowImg1.getId());
        assertThat(designerShowImg1).isEqualTo(designerShowImg2);
        designerShowImg2.setId(2L);
        assertThat(designerShowImg1).isNotEqualTo(designerShowImg2);
        designerShowImg1.setId(null);
        assertThat(designerShowImg1).isNotEqualTo(designerShowImg2);
    }
}
