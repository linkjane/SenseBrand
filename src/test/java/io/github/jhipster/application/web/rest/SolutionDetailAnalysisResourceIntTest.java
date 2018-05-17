package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.SolutionDetailAnalysis;
import io.github.jhipster.application.repository.SolutionDetailAnalysisRepository;
import io.github.jhipster.application.service.SolutionDetailAnalysisService;
import io.github.jhipster.application.repository.search.SolutionDetailAnalysisSearchRepository;
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
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SolutionDetailAnalysisResource REST controller.
 *
 * @see SolutionDetailAnalysisResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class SolutionDetailAnalysisResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCTION = "BBBBBBBBBB";

    @Autowired
    private SolutionDetailAnalysisRepository solutionDetailAnalysisRepository;

    @Autowired
    private SolutionDetailAnalysisService solutionDetailAnalysisService;

    @Autowired
    private SolutionDetailAnalysisSearchRepository solutionDetailAnalysisSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSolutionDetailAnalysisMockMvc;

    private SolutionDetailAnalysis solutionDetailAnalysis;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SolutionDetailAnalysisResource solutionDetailAnalysisResource = new SolutionDetailAnalysisResource(solutionDetailAnalysisService);
        this.restSolutionDetailAnalysisMockMvc = MockMvcBuilders.standaloneSetup(solutionDetailAnalysisResource)
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
    public static SolutionDetailAnalysis createEntity(EntityManager em) {
        SolutionDetailAnalysis solutionDetailAnalysis = new SolutionDetailAnalysis()
            .title(DEFAULT_TITLE)
            .introduction(DEFAULT_INTRODUCTION);
        return solutionDetailAnalysis;
    }

    @Before
    public void initTest() {
        solutionDetailAnalysisSearchRepository.deleteAll();
        solutionDetailAnalysis = createEntity(em);
    }

    @Test
    @Transactional
    public void createSolutionDetailAnalysis() throws Exception {
        int databaseSizeBeforeCreate = solutionDetailAnalysisRepository.findAll().size();

        // Create the SolutionDetailAnalysis
        restSolutionDetailAnalysisMockMvc.perform(post("/api/solution-detail-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solutionDetailAnalysis)))
            .andExpect(status().isCreated());

        // Validate the SolutionDetailAnalysis in the database
        List<SolutionDetailAnalysis> solutionDetailAnalysisList = solutionDetailAnalysisRepository.findAll();
        assertThat(solutionDetailAnalysisList).hasSize(databaseSizeBeforeCreate + 1);
        SolutionDetailAnalysis testSolutionDetailAnalysis = solutionDetailAnalysisList.get(solutionDetailAnalysisList.size() - 1);
        assertThat(testSolutionDetailAnalysis.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSolutionDetailAnalysis.getIntroduction()).isEqualTo(DEFAULT_INTRODUCTION);

        // Validate the SolutionDetailAnalysis in Elasticsearch
        SolutionDetailAnalysis solutionDetailAnalysisEs = solutionDetailAnalysisSearchRepository.findOne(testSolutionDetailAnalysis.getId());
        assertThat(solutionDetailAnalysisEs).isEqualToIgnoringGivenFields(testSolutionDetailAnalysis);
    }

    @Test
    @Transactional
    public void createSolutionDetailAnalysisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = solutionDetailAnalysisRepository.findAll().size();

        // Create the SolutionDetailAnalysis with an existing ID
        solutionDetailAnalysis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolutionDetailAnalysisMockMvc.perform(post("/api/solution-detail-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solutionDetailAnalysis)))
            .andExpect(status().isBadRequest());

        // Validate the SolutionDetailAnalysis in the database
        List<SolutionDetailAnalysis> solutionDetailAnalysisList = solutionDetailAnalysisRepository.findAll();
        assertThat(solutionDetailAnalysisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSolutionDetailAnalyses() throws Exception {
        // Initialize the database
        solutionDetailAnalysisRepository.saveAndFlush(solutionDetailAnalysis);

        // Get all the solutionDetailAnalysisList
        restSolutionDetailAnalysisMockMvc.perform(get("/api/solution-detail-analyses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solutionDetailAnalysis.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())));
    }

    @Test
    @Transactional
    public void getSolutionDetailAnalysis() throws Exception {
        // Initialize the database
        solutionDetailAnalysisRepository.saveAndFlush(solutionDetailAnalysis);

        // Get the solutionDetailAnalysis
        restSolutionDetailAnalysisMockMvc.perform(get("/api/solution-detail-analyses/{id}", solutionDetailAnalysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(solutionDetailAnalysis.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.introduction").value(DEFAULT_INTRODUCTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSolutionDetailAnalysis() throws Exception {
        // Get the solutionDetailAnalysis
        restSolutionDetailAnalysisMockMvc.perform(get("/api/solution-detail-analyses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSolutionDetailAnalysis() throws Exception {
        // Initialize the database
        solutionDetailAnalysisService.save(solutionDetailAnalysis);

        int databaseSizeBeforeUpdate = solutionDetailAnalysisRepository.findAll().size();

        // Update the solutionDetailAnalysis
        SolutionDetailAnalysis updatedSolutionDetailAnalysis = solutionDetailAnalysisRepository.findOne(solutionDetailAnalysis.getId());
        // Disconnect from session so that the updates on updatedSolutionDetailAnalysis are not directly saved in db
        em.detach(updatedSolutionDetailAnalysis);
        updatedSolutionDetailAnalysis
            .title(UPDATED_TITLE)
            .introduction(UPDATED_INTRODUCTION);

        restSolutionDetailAnalysisMockMvc.perform(put("/api/solution-detail-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSolutionDetailAnalysis)))
            .andExpect(status().isOk());

        // Validate the SolutionDetailAnalysis in the database
        List<SolutionDetailAnalysis> solutionDetailAnalysisList = solutionDetailAnalysisRepository.findAll();
        assertThat(solutionDetailAnalysisList).hasSize(databaseSizeBeforeUpdate);
        SolutionDetailAnalysis testSolutionDetailAnalysis = solutionDetailAnalysisList.get(solutionDetailAnalysisList.size() - 1);
        assertThat(testSolutionDetailAnalysis.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSolutionDetailAnalysis.getIntroduction()).isEqualTo(UPDATED_INTRODUCTION);

        // Validate the SolutionDetailAnalysis in Elasticsearch
        SolutionDetailAnalysis solutionDetailAnalysisEs = solutionDetailAnalysisSearchRepository.findOne(testSolutionDetailAnalysis.getId());
        assertThat(solutionDetailAnalysisEs).isEqualToIgnoringGivenFields(testSolutionDetailAnalysis);
    }

    @Test
    @Transactional
    public void updateNonExistingSolutionDetailAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = solutionDetailAnalysisRepository.findAll().size();

        // Create the SolutionDetailAnalysis

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSolutionDetailAnalysisMockMvc.perform(put("/api/solution-detail-analyses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solutionDetailAnalysis)))
            .andExpect(status().isCreated());

        // Validate the SolutionDetailAnalysis in the database
        List<SolutionDetailAnalysis> solutionDetailAnalysisList = solutionDetailAnalysisRepository.findAll();
        assertThat(solutionDetailAnalysisList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSolutionDetailAnalysis() throws Exception {
        // Initialize the database
        solutionDetailAnalysisService.save(solutionDetailAnalysis);

        int databaseSizeBeforeDelete = solutionDetailAnalysisRepository.findAll().size();

        // Get the solutionDetailAnalysis
        restSolutionDetailAnalysisMockMvc.perform(delete("/api/solution-detail-analyses/{id}", solutionDetailAnalysis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean solutionDetailAnalysisExistsInEs = solutionDetailAnalysisSearchRepository.exists(solutionDetailAnalysis.getId());
        assertThat(solutionDetailAnalysisExistsInEs).isFalse();

        // Validate the database is empty
        List<SolutionDetailAnalysis> solutionDetailAnalysisList = solutionDetailAnalysisRepository.findAll();
        assertThat(solutionDetailAnalysisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSolutionDetailAnalysis() throws Exception {
        // Initialize the database
        solutionDetailAnalysisService.save(solutionDetailAnalysis);

        // Search the solutionDetailAnalysis
        restSolutionDetailAnalysisMockMvc.perform(get("/api/_search/solution-detail-analyses?query=id:" + solutionDetailAnalysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solutionDetailAnalysis.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolutionDetailAnalysis.class);
        SolutionDetailAnalysis solutionDetailAnalysis1 = new SolutionDetailAnalysis();
        solutionDetailAnalysis1.setId(1L);
        SolutionDetailAnalysis solutionDetailAnalysis2 = new SolutionDetailAnalysis();
        solutionDetailAnalysis2.setId(solutionDetailAnalysis1.getId());
        assertThat(solutionDetailAnalysis1).isEqualTo(solutionDetailAnalysis2);
        solutionDetailAnalysis2.setId(2L);
        assertThat(solutionDetailAnalysis1).isNotEqualTo(solutionDetailAnalysis2);
        solutionDetailAnalysis1.setId(null);
        assertThat(solutionDetailAnalysis1).isNotEqualTo(solutionDetailAnalysis2);
    }
}
