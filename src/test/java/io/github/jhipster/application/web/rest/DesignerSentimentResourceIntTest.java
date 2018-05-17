package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.DesignerSentiment;
import io.github.jhipster.application.repository.DesignerSentimentRepository;
import io.github.jhipster.application.service.DesignerSentimentService;
import io.github.jhipster.application.repository.search.DesignerSentimentSearchRepository;
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
 * Test class for the DesignerSentimentResource REST controller.
 *
 * @see DesignerSentimentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class DesignerSentimentResourceIntTest {

    private static final String DEFAULT_FIRST_LEVEL_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_LEVEL_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_LEVEL_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_LEVEL_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_FILE = "AAAAAAAAAA";
    private static final String UPDATED_IMG_FILE = "BBBBBBBBBB";

    @Autowired
    private DesignerSentimentRepository designerSentimentRepository;

    @Autowired
    private DesignerSentimentService designerSentimentService;

    @Autowired
    private DesignerSentimentSearchRepository designerSentimentSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDesignerSentimentMockMvc;

    private DesignerSentiment designerSentiment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DesignerSentimentResource designerSentimentResource = new DesignerSentimentResource(designerSentimentService);
        this.restDesignerSentimentMockMvc = MockMvcBuilders.standaloneSetup(designerSentimentResource)
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
    public static DesignerSentiment createEntity(EntityManager em) {
        DesignerSentiment designerSentiment = new DesignerSentiment()
            .firstLevelTitle(DEFAULT_FIRST_LEVEL_TITLE)
            .secondLevelTitle(DEFAULT_SECOND_LEVEL_TITLE)
            .imgFile(DEFAULT_IMG_FILE);
        return designerSentiment;
    }

    @Before
    public void initTest() {
        designerSentimentSearchRepository.deleteAll();
        designerSentiment = createEntity(em);
    }

    @Test
    @Transactional
    public void createDesignerSentiment() throws Exception {
        int databaseSizeBeforeCreate = designerSentimentRepository.findAll().size();

        // Create the DesignerSentiment
        restDesignerSentimentMockMvc.perform(post("/api/designer-sentiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerSentiment)))
            .andExpect(status().isCreated());

        // Validate the DesignerSentiment in the database
        List<DesignerSentiment> designerSentimentList = designerSentimentRepository.findAll();
        assertThat(designerSentimentList).hasSize(databaseSizeBeforeCreate + 1);
        DesignerSentiment testDesignerSentiment = designerSentimentList.get(designerSentimentList.size() - 1);
        assertThat(testDesignerSentiment.getFirstLevelTitle()).isEqualTo(DEFAULT_FIRST_LEVEL_TITLE);
        assertThat(testDesignerSentiment.getSecondLevelTitle()).isEqualTo(DEFAULT_SECOND_LEVEL_TITLE);
        assertThat(testDesignerSentiment.getImgFile()).isEqualTo(DEFAULT_IMG_FILE);

        // Validate the DesignerSentiment in Elasticsearch
        DesignerSentiment designerSentimentEs = designerSentimentSearchRepository.findOne(testDesignerSentiment.getId());
        assertThat(designerSentimentEs).isEqualToIgnoringGivenFields(testDesignerSentiment);
    }

    @Test
    @Transactional
    public void createDesignerSentimentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = designerSentimentRepository.findAll().size();

        // Create the DesignerSentiment with an existing ID
        designerSentiment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignerSentimentMockMvc.perform(post("/api/designer-sentiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerSentiment)))
            .andExpect(status().isBadRequest());

        // Validate the DesignerSentiment in the database
        List<DesignerSentiment> designerSentimentList = designerSentimentRepository.findAll();
        assertThat(designerSentimentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstLevelTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = designerSentimentRepository.findAll().size();
        // set the field null
        designerSentiment.setFirstLevelTitle(null);

        // Create the DesignerSentiment, which fails.

        restDesignerSentimentMockMvc.perform(post("/api/designer-sentiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerSentiment)))
            .andExpect(status().isBadRequest());

        List<DesignerSentiment> designerSentimentList = designerSentimentRepository.findAll();
        assertThat(designerSentimentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImgFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = designerSentimentRepository.findAll().size();
        // set the field null
        designerSentiment.setImgFile(null);

        // Create the DesignerSentiment, which fails.

        restDesignerSentimentMockMvc.perform(post("/api/designer-sentiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerSentiment)))
            .andExpect(status().isBadRequest());

        List<DesignerSentiment> designerSentimentList = designerSentimentRepository.findAll();
        assertThat(designerSentimentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDesignerSentiments() throws Exception {
        // Initialize the database
        designerSentimentRepository.saveAndFlush(designerSentiment);

        // Get all the designerSentimentList
        restDesignerSentimentMockMvc.perform(get("/api/designer-sentiments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designerSentiment.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstLevelTitle").value(hasItem(DEFAULT_FIRST_LEVEL_TITLE.toString())))
            .andExpect(jsonPath("$.[*].secondLevelTitle").value(hasItem(DEFAULT_SECOND_LEVEL_TITLE.toString())))
            .andExpect(jsonPath("$.[*].imgFile").value(hasItem(DEFAULT_IMG_FILE.toString())));
    }

    @Test
    @Transactional
    public void getDesignerSentiment() throws Exception {
        // Initialize the database
        designerSentimentRepository.saveAndFlush(designerSentiment);

        // Get the designerSentiment
        restDesignerSentimentMockMvc.perform(get("/api/designer-sentiments/{id}", designerSentiment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(designerSentiment.getId().intValue()))
            .andExpect(jsonPath("$.firstLevelTitle").value(DEFAULT_FIRST_LEVEL_TITLE.toString()))
            .andExpect(jsonPath("$.secondLevelTitle").value(DEFAULT_SECOND_LEVEL_TITLE.toString()))
            .andExpect(jsonPath("$.imgFile").value(DEFAULT_IMG_FILE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDesignerSentiment() throws Exception {
        // Get the designerSentiment
        restDesignerSentimentMockMvc.perform(get("/api/designer-sentiments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDesignerSentiment() throws Exception {
        // Initialize the database
        designerSentimentService.save(designerSentiment);

        int databaseSizeBeforeUpdate = designerSentimentRepository.findAll().size();

        // Update the designerSentiment
        DesignerSentiment updatedDesignerSentiment = designerSentimentRepository.findOne(designerSentiment.getId());
        // Disconnect from session so that the updates on updatedDesignerSentiment are not directly saved in db
        em.detach(updatedDesignerSentiment);
        updatedDesignerSentiment
            .firstLevelTitle(UPDATED_FIRST_LEVEL_TITLE)
            .secondLevelTitle(UPDATED_SECOND_LEVEL_TITLE)
            .imgFile(UPDATED_IMG_FILE);

        restDesignerSentimentMockMvc.perform(put("/api/designer-sentiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDesignerSentiment)))
            .andExpect(status().isOk());

        // Validate the DesignerSentiment in the database
        List<DesignerSentiment> designerSentimentList = designerSentimentRepository.findAll();
        assertThat(designerSentimentList).hasSize(databaseSizeBeforeUpdate);
        DesignerSentiment testDesignerSentiment = designerSentimentList.get(designerSentimentList.size() - 1);
        assertThat(testDesignerSentiment.getFirstLevelTitle()).isEqualTo(UPDATED_FIRST_LEVEL_TITLE);
        assertThat(testDesignerSentiment.getSecondLevelTitle()).isEqualTo(UPDATED_SECOND_LEVEL_TITLE);
        assertThat(testDesignerSentiment.getImgFile()).isEqualTo(UPDATED_IMG_FILE);

        // Validate the DesignerSentiment in Elasticsearch
        DesignerSentiment designerSentimentEs = designerSentimentSearchRepository.findOne(testDesignerSentiment.getId());
        assertThat(designerSentimentEs).isEqualToIgnoringGivenFields(testDesignerSentiment);
    }

    @Test
    @Transactional
    public void updateNonExistingDesignerSentiment() throws Exception {
        int databaseSizeBeforeUpdate = designerSentimentRepository.findAll().size();

        // Create the DesignerSentiment

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDesignerSentimentMockMvc.perform(put("/api/designer-sentiments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerSentiment)))
            .andExpect(status().isCreated());

        // Validate the DesignerSentiment in the database
        List<DesignerSentiment> designerSentimentList = designerSentimentRepository.findAll();
        assertThat(designerSentimentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDesignerSentiment() throws Exception {
        // Initialize the database
        designerSentimentService.save(designerSentiment);

        int databaseSizeBeforeDelete = designerSentimentRepository.findAll().size();

        // Get the designerSentiment
        restDesignerSentimentMockMvc.perform(delete("/api/designer-sentiments/{id}", designerSentiment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean designerSentimentExistsInEs = designerSentimentSearchRepository.exists(designerSentiment.getId());
        assertThat(designerSentimentExistsInEs).isFalse();

        // Validate the database is empty
        List<DesignerSentiment> designerSentimentList = designerSentimentRepository.findAll();
        assertThat(designerSentimentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDesignerSentiment() throws Exception {
        // Initialize the database
        designerSentimentService.save(designerSentiment);

        // Search the designerSentiment
        restDesignerSentimentMockMvc.perform(get("/api/_search/designer-sentiments?query=id:" + designerSentiment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designerSentiment.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstLevelTitle").value(hasItem(DEFAULT_FIRST_LEVEL_TITLE.toString())))
            .andExpect(jsonPath("$.[*].secondLevelTitle").value(hasItem(DEFAULT_SECOND_LEVEL_TITLE.toString())))
            .andExpect(jsonPath("$.[*].imgFile").value(hasItem(DEFAULT_IMG_FILE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesignerSentiment.class);
        DesignerSentiment designerSentiment1 = new DesignerSentiment();
        designerSentiment1.setId(1L);
        DesignerSentiment designerSentiment2 = new DesignerSentiment();
        designerSentiment2.setId(designerSentiment1.getId());
        assertThat(designerSentiment1).isEqualTo(designerSentiment2);
        designerSentiment2.setId(2L);
        assertThat(designerSentiment1).isNotEqualTo(designerSentiment2);
        designerSentiment1.setId(null);
        assertThat(designerSentiment1).isNotEqualTo(designerSentiment2);
    }
}
