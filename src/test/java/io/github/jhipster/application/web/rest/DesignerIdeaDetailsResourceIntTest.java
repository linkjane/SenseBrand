package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.DesignerIdeaDetails;
import io.github.jhipster.application.domain.Designer;
import io.github.jhipster.application.repository.DesignerIdeaDetailsRepository;
import io.github.jhipster.application.service.DesignerIdeaDetailsService;
import io.github.jhipster.application.repository.search.DesignerIdeaDetailsSearchRepository;
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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DesignerIdeaDetailsResource REST controller.
 *
 * @see DesignerIdeaDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class DesignerIdeaDetailsResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SHARE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SHARE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SECOND_LEVEL_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_LEVEL_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCTION = "BBBBBBBBBB";

    @Autowired
    private DesignerIdeaDetailsRepository designerIdeaDetailsRepository;

    @Autowired
    private DesignerIdeaDetailsService designerIdeaDetailsService;

    @Autowired
    private DesignerIdeaDetailsSearchRepository designerIdeaDetailsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDesignerIdeaDetailsMockMvc;

    private DesignerIdeaDetails designerIdeaDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DesignerIdeaDetailsResource designerIdeaDetailsResource = new DesignerIdeaDetailsResource(designerIdeaDetailsService);
        this.restDesignerIdeaDetailsMockMvc = MockMvcBuilders.standaloneSetup(designerIdeaDetailsResource)
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
    public static DesignerIdeaDetails createEntity(EntityManager em) {
        DesignerIdeaDetails designerIdeaDetails = new DesignerIdeaDetails()
            .title(DEFAULT_TITLE)
            .shareTime(DEFAULT_SHARE_TIME)
            .secondLevelTitle(DEFAULT_SECOND_LEVEL_TITLE)
            .introduction(DEFAULT_INTRODUCTION);
        // Add required entity
        Designer designer = DesignerResourceIntTest.createEntity(em);
        em.persist(designer);
        em.flush();
        designerIdeaDetails.setDesigner(designer);
        return designerIdeaDetails;
    }

    @Before
    public void initTest() {
        designerIdeaDetailsSearchRepository.deleteAll();
        designerIdeaDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createDesignerIdeaDetails() throws Exception {
        int databaseSizeBeforeCreate = designerIdeaDetailsRepository.findAll().size();

        // Create the DesignerIdeaDetails
        restDesignerIdeaDetailsMockMvc.perform(post("/api/designer-idea-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerIdeaDetails)))
            .andExpect(status().isCreated());

        // Validate the DesignerIdeaDetails in the database
        List<DesignerIdeaDetails> designerIdeaDetailsList = designerIdeaDetailsRepository.findAll();
        assertThat(designerIdeaDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        DesignerIdeaDetails testDesignerIdeaDetails = designerIdeaDetailsList.get(designerIdeaDetailsList.size() - 1);
        assertThat(testDesignerIdeaDetails.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDesignerIdeaDetails.getShareTime()).isEqualTo(DEFAULT_SHARE_TIME);
        assertThat(testDesignerIdeaDetails.getSecondLevelTitle()).isEqualTo(DEFAULT_SECOND_LEVEL_TITLE);
        assertThat(testDesignerIdeaDetails.getIntroduction()).isEqualTo(DEFAULT_INTRODUCTION);

        // Validate the DesignerIdeaDetails in Elasticsearch
        DesignerIdeaDetails designerIdeaDetailsEs = designerIdeaDetailsSearchRepository.findOne(testDesignerIdeaDetails.getId());
        assertThat(designerIdeaDetailsEs).isEqualToIgnoringGivenFields(testDesignerIdeaDetails);
    }

    @Test
    @Transactional
    public void createDesignerIdeaDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = designerIdeaDetailsRepository.findAll().size();

        // Create the DesignerIdeaDetails with an existing ID
        designerIdeaDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignerIdeaDetailsMockMvc.perform(post("/api/designer-idea-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerIdeaDetails)))
            .andExpect(status().isBadRequest());

        // Validate the DesignerIdeaDetails in the database
        List<DesignerIdeaDetails> designerIdeaDetailsList = designerIdeaDetailsRepository.findAll();
        assertThat(designerIdeaDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = designerIdeaDetailsRepository.findAll().size();
        // set the field null
        designerIdeaDetails.setTitle(null);

        // Create the DesignerIdeaDetails, which fails.

        restDesignerIdeaDetailsMockMvc.perform(post("/api/designer-idea-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerIdeaDetails)))
            .andExpect(status().isBadRequest());

        List<DesignerIdeaDetails> designerIdeaDetailsList = designerIdeaDetailsRepository.findAll();
        assertThat(designerIdeaDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShareTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = designerIdeaDetailsRepository.findAll().size();
        // set the field null
        designerIdeaDetails.setShareTime(null);

        // Create the DesignerIdeaDetails, which fails.

        restDesignerIdeaDetailsMockMvc.perform(post("/api/designer-idea-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerIdeaDetails)))
            .andExpect(status().isBadRequest());

        List<DesignerIdeaDetails> designerIdeaDetailsList = designerIdeaDetailsRepository.findAll();
        assertThat(designerIdeaDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDesignerIdeaDetails() throws Exception {
        // Initialize the database
        designerIdeaDetailsRepository.saveAndFlush(designerIdeaDetails);

        // Get all the designerIdeaDetailsList
        restDesignerIdeaDetailsMockMvc.perform(get("/api/designer-idea-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designerIdeaDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].shareTime").value(hasItem(DEFAULT_SHARE_TIME.toString())))
            .andExpect(jsonPath("$.[*].secondLevelTitle").value(hasItem(DEFAULT_SECOND_LEVEL_TITLE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())));
    }

    @Test
    @Transactional
    public void getDesignerIdeaDetails() throws Exception {
        // Initialize the database
        designerIdeaDetailsRepository.saveAndFlush(designerIdeaDetails);

        // Get the designerIdeaDetails
        restDesignerIdeaDetailsMockMvc.perform(get("/api/designer-idea-details/{id}", designerIdeaDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(designerIdeaDetails.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.shareTime").value(DEFAULT_SHARE_TIME.toString()))
            .andExpect(jsonPath("$.secondLevelTitle").value(DEFAULT_SECOND_LEVEL_TITLE.toString()))
            .andExpect(jsonPath("$.introduction").value(DEFAULT_INTRODUCTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDesignerIdeaDetails() throws Exception {
        // Get the designerIdeaDetails
        restDesignerIdeaDetailsMockMvc.perform(get("/api/designer-idea-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDesignerIdeaDetails() throws Exception {
        // Initialize the database
        designerIdeaDetailsService.save(designerIdeaDetails);

        int databaseSizeBeforeUpdate = designerIdeaDetailsRepository.findAll().size();

        // Update the designerIdeaDetails
        DesignerIdeaDetails updatedDesignerIdeaDetails = designerIdeaDetailsRepository.findOne(designerIdeaDetails.getId());
        // Disconnect from session so that the updates on updatedDesignerIdeaDetails are not directly saved in db
        em.detach(updatedDesignerIdeaDetails);
        updatedDesignerIdeaDetails
            .title(UPDATED_TITLE)
            .shareTime(UPDATED_SHARE_TIME)
            .secondLevelTitle(UPDATED_SECOND_LEVEL_TITLE)
            .introduction(UPDATED_INTRODUCTION);

        restDesignerIdeaDetailsMockMvc.perform(put("/api/designer-idea-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDesignerIdeaDetails)))
            .andExpect(status().isOk());

        // Validate the DesignerIdeaDetails in the database
        List<DesignerIdeaDetails> designerIdeaDetailsList = designerIdeaDetailsRepository.findAll();
        assertThat(designerIdeaDetailsList).hasSize(databaseSizeBeforeUpdate);
        DesignerIdeaDetails testDesignerIdeaDetails = designerIdeaDetailsList.get(designerIdeaDetailsList.size() - 1);
        assertThat(testDesignerIdeaDetails.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDesignerIdeaDetails.getShareTime()).isEqualTo(UPDATED_SHARE_TIME);
        assertThat(testDesignerIdeaDetails.getSecondLevelTitle()).isEqualTo(UPDATED_SECOND_LEVEL_TITLE);
        assertThat(testDesignerIdeaDetails.getIntroduction()).isEqualTo(UPDATED_INTRODUCTION);

        // Validate the DesignerIdeaDetails in Elasticsearch
        DesignerIdeaDetails designerIdeaDetailsEs = designerIdeaDetailsSearchRepository.findOne(testDesignerIdeaDetails.getId());
        assertThat(designerIdeaDetailsEs).isEqualToIgnoringGivenFields(testDesignerIdeaDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingDesignerIdeaDetails() throws Exception {
        int databaseSizeBeforeUpdate = designerIdeaDetailsRepository.findAll().size();

        // Create the DesignerIdeaDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDesignerIdeaDetailsMockMvc.perform(put("/api/designer-idea-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerIdeaDetails)))
            .andExpect(status().isCreated());

        // Validate the DesignerIdeaDetails in the database
        List<DesignerIdeaDetails> designerIdeaDetailsList = designerIdeaDetailsRepository.findAll();
        assertThat(designerIdeaDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDesignerIdeaDetails() throws Exception {
        // Initialize the database
        designerIdeaDetailsService.save(designerIdeaDetails);

        int databaseSizeBeforeDelete = designerIdeaDetailsRepository.findAll().size();

        // Get the designerIdeaDetails
        restDesignerIdeaDetailsMockMvc.perform(delete("/api/designer-idea-details/{id}", designerIdeaDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean designerIdeaDetailsExistsInEs = designerIdeaDetailsSearchRepository.exists(designerIdeaDetails.getId());
        assertThat(designerIdeaDetailsExistsInEs).isFalse();

        // Validate the database is empty
        List<DesignerIdeaDetails> designerIdeaDetailsList = designerIdeaDetailsRepository.findAll();
        assertThat(designerIdeaDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDesignerIdeaDetails() throws Exception {
        // Initialize the database
        designerIdeaDetailsService.save(designerIdeaDetails);

        // Search the designerIdeaDetails
        restDesignerIdeaDetailsMockMvc.perform(get("/api/_search/designer-idea-details?query=id:" + designerIdeaDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designerIdeaDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].shareTime").value(hasItem(DEFAULT_SHARE_TIME.toString())))
            .andExpect(jsonPath("$.[*].secondLevelTitle").value(hasItem(DEFAULT_SECOND_LEVEL_TITLE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesignerIdeaDetails.class);
        DesignerIdeaDetails designerIdeaDetails1 = new DesignerIdeaDetails();
        designerIdeaDetails1.setId(1L);
        DesignerIdeaDetails designerIdeaDetails2 = new DesignerIdeaDetails();
        designerIdeaDetails2.setId(designerIdeaDetails1.getId());
        assertThat(designerIdeaDetails1).isEqualTo(designerIdeaDetails2);
        designerIdeaDetails2.setId(2L);
        assertThat(designerIdeaDetails1).isNotEqualTo(designerIdeaDetails2);
        designerIdeaDetails1.setId(null);
        assertThat(designerIdeaDetails1).isNotEqualTo(designerIdeaDetails2);
    }
}
