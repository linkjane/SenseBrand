package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.SolutionCorrelation;
import io.github.jhipster.application.repository.SolutionCorrelationRepository;
import io.github.jhipster.application.service.SolutionCorrelationService;
import io.github.jhipster.application.repository.search.SolutionCorrelationSearchRepository;
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
 * Test class for the SolutionCorrelationResource REST controller.
 *
 * @see SolutionCorrelationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class SolutionCorrelationResourceIntTest {

    private static final String DEFAULT_IMG_FILE = "AAAAAAAAAA";
    private static final String UPDATED_IMG_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_GO_LINK = "AAAAAAAAAA";
    private static final String UPDATED_GO_LINK = "BBBBBBBBBB";

    @Autowired
    private SolutionCorrelationRepository solutionCorrelationRepository;

    @Autowired
    private SolutionCorrelationService solutionCorrelationService;

    @Autowired
    private SolutionCorrelationSearchRepository solutionCorrelationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSolutionCorrelationMockMvc;

    private SolutionCorrelation solutionCorrelation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SolutionCorrelationResource solutionCorrelationResource = new SolutionCorrelationResource(solutionCorrelationService);
        this.restSolutionCorrelationMockMvc = MockMvcBuilders.standaloneSetup(solutionCorrelationResource)
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
    public static SolutionCorrelation createEntity(EntityManager em) {
        SolutionCorrelation solutionCorrelation = new SolutionCorrelation()
            .imgFile(DEFAULT_IMG_FILE)
            .goLink(DEFAULT_GO_LINK);
        return solutionCorrelation;
    }

    @Before
    public void initTest() {
        solutionCorrelationSearchRepository.deleteAll();
        solutionCorrelation = createEntity(em);
    }

    @Test
    @Transactional
    public void createSolutionCorrelation() throws Exception {
        int databaseSizeBeforeCreate = solutionCorrelationRepository.findAll().size();

        // Create the SolutionCorrelation
        restSolutionCorrelationMockMvc.perform(post("/api/solution-correlations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solutionCorrelation)))
            .andExpect(status().isCreated());

        // Validate the SolutionCorrelation in the database
        List<SolutionCorrelation> solutionCorrelationList = solutionCorrelationRepository.findAll();
        assertThat(solutionCorrelationList).hasSize(databaseSizeBeforeCreate + 1);
        SolutionCorrelation testSolutionCorrelation = solutionCorrelationList.get(solutionCorrelationList.size() - 1);
        assertThat(testSolutionCorrelation.getImgFile()).isEqualTo(DEFAULT_IMG_FILE);
        assertThat(testSolutionCorrelation.getGoLink()).isEqualTo(DEFAULT_GO_LINK);

        // Validate the SolutionCorrelation in Elasticsearch
        SolutionCorrelation solutionCorrelationEs = solutionCorrelationSearchRepository.findOne(testSolutionCorrelation.getId());
        assertThat(solutionCorrelationEs).isEqualToIgnoringGivenFields(testSolutionCorrelation);
    }

    @Test
    @Transactional
    public void createSolutionCorrelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = solutionCorrelationRepository.findAll().size();

        // Create the SolutionCorrelation with an existing ID
        solutionCorrelation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolutionCorrelationMockMvc.perform(post("/api/solution-correlations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solutionCorrelation)))
            .andExpect(status().isBadRequest());

        // Validate the SolutionCorrelation in the database
        List<SolutionCorrelation> solutionCorrelationList = solutionCorrelationRepository.findAll();
        assertThat(solutionCorrelationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkImgFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = solutionCorrelationRepository.findAll().size();
        // set the field null
        solutionCorrelation.setImgFile(null);

        // Create the SolutionCorrelation, which fails.

        restSolutionCorrelationMockMvc.perform(post("/api/solution-correlations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solutionCorrelation)))
            .andExpect(status().isBadRequest());

        List<SolutionCorrelation> solutionCorrelationList = solutionCorrelationRepository.findAll();
        assertThat(solutionCorrelationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSolutionCorrelations() throws Exception {
        // Initialize the database
        solutionCorrelationRepository.saveAndFlush(solutionCorrelation);

        // Get all the solutionCorrelationList
        restSolutionCorrelationMockMvc.perform(get("/api/solution-correlations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solutionCorrelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].imgFile").value(hasItem(DEFAULT_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].goLink").value(hasItem(DEFAULT_GO_LINK.toString())));
    }

    @Test
    @Transactional
    public void getSolutionCorrelation() throws Exception {
        // Initialize the database
        solutionCorrelationRepository.saveAndFlush(solutionCorrelation);

        // Get the solutionCorrelation
        restSolutionCorrelationMockMvc.perform(get("/api/solution-correlations/{id}", solutionCorrelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(solutionCorrelation.getId().intValue()))
            .andExpect(jsonPath("$.imgFile").value(DEFAULT_IMG_FILE.toString()))
            .andExpect(jsonPath("$.goLink").value(DEFAULT_GO_LINK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSolutionCorrelation() throws Exception {
        // Get the solutionCorrelation
        restSolutionCorrelationMockMvc.perform(get("/api/solution-correlations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSolutionCorrelation() throws Exception {
        // Initialize the database
        solutionCorrelationService.save(solutionCorrelation);

        int databaseSizeBeforeUpdate = solutionCorrelationRepository.findAll().size();

        // Update the solutionCorrelation
        SolutionCorrelation updatedSolutionCorrelation = solutionCorrelationRepository.findOne(solutionCorrelation.getId());
        // Disconnect from session so that the updates on updatedSolutionCorrelation are not directly saved in db
        em.detach(updatedSolutionCorrelation);
        updatedSolutionCorrelation
            .imgFile(UPDATED_IMG_FILE)
            .goLink(UPDATED_GO_LINK);

        restSolutionCorrelationMockMvc.perform(put("/api/solution-correlations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSolutionCorrelation)))
            .andExpect(status().isOk());

        // Validate the SolutionCorrelation in the database
        List<SolutionCorrelation> solutionCorrelationList = solutionCorrelationRepository.findAll();
        assertThat(solutionCorrelationList).hasSize(databaseSizeBeforeUpdate);
        SolutionCorrelation testSolutionCorrelation = solutionCorrelationList.get(solutionCorrelationList.size() - 1);
        assertThat(testSolutionCorrelation.getImgFile()).isEqualTo(UPDATED_IMG_FILE);
        assertThat(testSolutionCorrelation.getGoLink()).isEqualTo(UPDATED_GO_LINK);

        // Validate the SolutionCorrelation in Elasticsearch
        SolutionCorrelation solutionCorrelationEs = solutionCorrelationSearchRepository.findOne(testSolutionCorrelation.getId());
        assertThat(solutionCorrelationEs).isEqualToIgnoringGivenFields(testSolutionCorrelation);
    }

    @Test
    @Transactional
    public void updateNonExistingSolutionCorrelation() throws Exception {
        int databaseSizeBeforeUpdate = solutionCorrelationRepository.findAll().size();

        // Create the SolutionCorrelation

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSolutionCorrelationMockMvc.perform(put("/api/solution-correlations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solutionCorrelation)))
            .andExpect(status().isCreated());

        // Validate the SolutionCorrelation in the database
        List<SolutionCorrelation> solutionCorrelationList = solutionCorrelationRepository.findAll();
        assertThat(solutionCorrelationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSolutionCorrelation() throws Exception {
        // Initialize the database
        solutionCorrelationService.save(solutionCorrelation);

        int databaseSizeBeforeDelete = solutionCorrelationRepository.findAll().size();

        // Get the solutionCorrelation
        restSolutionCorrelationMockMvc.perform(delete("/api/solution-correlations/{id}", solutionCorrelation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean solutionCorrelationExistsInEs = solutionCorrelationSearchRepository.exists(solutionCorrelation.getId());
        assertThat(solutionCorrelationExistsInEs).isFalse();

        // Validate the database is empty
        List<SolutionCorrelation> solutionCorrelationList = solutionCorrelationRepository.findAll();
        assertThat(solutionCorrelationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSolutionCorrelation() throws Exception {
        // Initialize the database
        solutionCorrelationService.save(solutionCorrelation);

        // Search the solutionCorrelation
        restSolutionCorrelationMockMvc.perform(get("/api/_search/solution-correlations?query=id:" + solutionCorrelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solutionCorrelation.getId().intValue())))
            .andExpect(jsonPath("$.[*].imgFile").value(hasItem(DEFAULT_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].goLink").value(hasItem(DEFAULT_GO_LINK.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolutionCorrelation.class);
        SolutionCorrelation solutionCorrelation1 = new SolutionCorrelation();
        solutionCorrelation1.setId(1L);
        SolutionCorrelation solutionCorrelation2 = new SolutionCorrelation();
        solutionCorrelation2.setId(solutionCorrelation1.getId());
        assertThat(solutionCorrelation1).isEqualTo(solutionCorrelation2);
        solutionCorrelation2.setId(2L);
        assertThat(solutionCorrelation1).isNotEqualTo(solutionCorrelation2);
        solutionCorrelation1.setId(null);
        assertThat(solutionCorrelation1).isNotEqualTo(solutionCorrelation2);
    }
}
