package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.Solution;
import io.github.jhipster.application.repository.SolutionRepository;
import io.github.jhipster.application.service.SolutionService;
import io.github.jhipster.application.repository.search.SolutionSearchRepository;
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
 * Test class for the SolutionResource REST controller.
 *
 * @see SolutionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class SolutionResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCTION = "BBBBBBBBBB";

    private static final String DEFAULT_BANNER_IMG_FILE = "AAAAAAAAAA";
    private static final String UPDATED_BANNER_IMG_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_BANNER_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_BANNER_INTRODUCTION = "BBBBBBBBBB";

    private static final String DEFAULT_SUMMARIZE_IMG_FILE = "AAAAAAAAAA";
    private static final String UPDATED_SUMMARIZE_IMG_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_SUMMARIZE_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_SUMMARIZE_INTRODUCTION = "BBBBBBBBBB";

    private static final String DEFAULT_SIGNIFICANCE_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SIGNIFICANCE_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SIGNIFICANCE_IMG_FILE = "AAAAAAAAAA";
    private static final String UPDATED_SIGNIFICANCE_IMG_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_SIGNIFICANCE_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_SIGNIFICANCE_INTRODUCTION = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL_ANALYSIS_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL_ANALYSIS_TITLE = "BBBBBBBBBB";

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private SolutionService solutionService;

    @Autowired
    private SolutionSearchRepository solutionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSolutionMockMvc;

    private Solution solution;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SolutionResource solutionResource = new SolutionResource(solutionService);
        this.restSolutionMockMvc = MockMvcBuilders.standaloneSetup(solutionResource)
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
    public static Solution createEntity(EntityManager em) {
        Solution solution = new Solution()
            .title(DEFAULT_TITLE)
            .introduction(DEFAULT_INTRODUCTION)
            .bannerImgFile(DEFAULT_BANNER_IMG_FILE)
            .bannerIntroduction(DEFAULT_BANNER_INTRODUCTION)
            .summarizeImgFile(DEFAULT_SUMMARIZE_IMG_FILE)
            .summarizeIntroduction(DEFAULT_SUMMARIZE_INTRODUCTION)
            .significanceTitle(DEFAULT_SIGNIFICANCE_TITLE)
            .significanceImgFile(DEFAULT_SIGNIFICANCE_IMG_FILE)
            .significanceIntroduction(DEFAULT_SIGNIFICANCE_INTRODUCTION)
            .detailAnalysisTitle(DEFAULT_DETAIL_ANALYSIS_TITLE);
        return solution;
    }

    @Before
    public void initTest() {
        solutionSearchRepository.deleteAll();
        solution = createEntity(em);
    }

    @Test
    @Transactional
    public void createSolution() throws Exception {
        int databaseSizeBeforeCreate = solutionRepository.findAll().size();

        // Create the Solution
        restSolutionMockMvc.perform(post("/api/solutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solution)))
            .andExpect(status().isCreated());

        // Validate the Solution in the database
        List<Solution> solutionList = solutionRepository.findAll();
        assertThat(solutionList).hasSize(databaseSizeBeforeCreate + 1);
        Solution testSolution = solutionList.get(solutionList.size() - 1);
        assertThat(testSolution.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSolution.getIntroduction()).isEqualTo(DEFAULT_INTRODUCTION);
        assertThat(testSolution.getBannerImgFile()).isEqualTo(DEFAULT_BANNER_IMG_FILE);
        assertThat(testSolution.getBannerIntroduction()).isEqualTo(DEFAULT_BANNER_INTRODUCTION);
        assertThat(testSolution.getSummarizeImgFile()).isEqualTo(DEFAULT_SUMMARIZE_IMG_FILE);
        assertThat(testSolution.getSummarizeIntroduction()).isEqualTo(DEFAULT_SUMMARIZE_INTRODUCTION);
        assertThat(testSolution.getSignificanceTitle()).isEqualTo(DEFAULT_SIGNIFICANCE_TITLE);
        assertThat(testSolution.getSignificanceImgFile()).isEqualTo(DEFAULT_SIGNIFICANCE_IMG_FILE);
        assertThat(testSolution.getSignificanceIntroduction()).isEqualTo(DEFAULT_SIGNIFICANCE_INTRODUCTION);
        assertThat(testSolution.getDetailAnalysisTitle()).isEqualTo(DEFAULT_DETAIL_ANALYSIS_TITLE);

        // Validate the Solution in Elasticsearch
        Solution solutionEs = solutionSearchRepository.findOne(testSolution.getId());
        assertThat(solutionEs).isEqualToIgnoringGivenFields(testSolution);
    }

    @Test
    @Transactional
    public void createSolutionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = solutionRepository.findAll().size();

        // Create the Solution with an existing ID
        solution.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolutionMockMvc.perform(post("/api/solutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solution)))
            .andExpect(status().isBadRequest());

        // Validate the Solution in the database
        List<Solution> solutionList = solutionRepository.findAll();
        assertThat(solutionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = solutionRepository.findAll().size();
        // set the field null
        solution.setTitle(null);

        // Create the Solution, which fails.

        restSolutionMockMvc.perform(post("/api/solutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solution)))
            .andExpect(status().isBadRequest());

        List<Solution> solutionList = solutionRepository.findAll();
        assertThat(solutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIntroductionIsRequired() throws Exception {
        int databaseSizeBeforeTest = solutionRepository.findAll().size();
        // set the field null
        solution.setIntroduction(null);

        // Create the Solution, which fails.

        restSolutionMockMvc.perform(post("/api/solutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solution)))
            .andExpect(status().isBadRequest());

        List<Solution> solutionList = solutionRepository.findAll();
        assertThat(solutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBannerImgFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = solutionRepository.findAll().size();
        // set the field null
        solution.setBannerImgFile(null);

        // Create the Solution, which fails.

        restSolutionMockMvc.perform(post("/api/solutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solution)))
            .andExpect(status().isBadRequest());

        List<Solution> solutionList = solutionRepository.findAll();
        assertThat(solutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBannerIntroductionIsRequired() throws Exception {
        int databaseSizeBeforeTest = solutionRepository.findAll().size();
        // set the field null
        solution.setBannerIntroduction(null);

        // Create the Solution, which fails.

        restSolutionMockMvc.perform(post("/api/solutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solution)))
            .andExpect(status().isBadRequest());

        List<Solution> solutionList = solutionRepository.findAll();
        assertThat(solutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSummarizeImgFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = solutionRepository.findAll().size();
        // set the field null
        solution.setSummarizeImgFile(null);

        // Create the Solution, which fails.

        restSolutionMockMvc.perform(post("/api/solutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solution)))
            .andExpect(status().isBadRequest());

        List<Solution> solutionList = solutionRepository.findAll();
        assertThat(solutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSummarizeIntroductionIsRequired() throws Exception {
        int databaseSizeBeforeTest = solutionRepository.findAll().size();
        // set the field null
        solution.setSummarizeIntroduction(null);

        // Create the Solution, which fails.

        restSolutionMockMvc.perform(post("/api/solutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solution)))
            .andExpect(status().isBadRequest());

        List<Solution> solutionList = solutionRepository.findAll();
        assertThat(solutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSignificanceTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = solutionRepository.findAll().size();
        // set the field null
        solution.setSignificanceTitle(null);

        // Create the Solution, which fails.

        restSolutionMockMvc.perform(post("/api/solutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solution)))
            .andExpect(status().isBadRequest());

        List<Solution> solutionList = solutionRepository.findAll();
        assertThat(solutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSignificanceImgFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = solutionRepository.findAll().size();
        // set the field null
        solution.setSignificanceImgFile(null);

        // Create the Solution, which fails.

        restSolutionMockMvc.perform(post("/api/solutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solution)))
            .andExpect(status().isBadRequest());

        List<Solution> solutionList = solutionRepository.findAll();
        assertThat(solutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSignificanceIntroductionIsRequired() throws Exception {
        int databaseSizeBeforeTest = solutionRepository.findAll().size();
        // set the field null
        solution.setSignificanceIntroduction(null);

        // Create the Solution, which fails.

        restSolutionMockMvc.perform(post("/api/solutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solution)))
            .andExpect(status().isBadRequest());

        List<Solution> solutionList = solutionRepository.findAll();
        assertThat(solutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDetailAnalysisTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = solutionRepository.findAll().size();
        // set the field null
        solution.setDetailAnalysisTitle(null);

        // Create the Solution, which fails.

        restSolutionMockMvc.perform(post("/api/solutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solution)))
            .andExpect(status().isBadRequest());

        List<Solution> solutionList = solutionRepository.findAll();
        assertThat(solutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSolutions() throws Exception {
        // Initialize the database
        solutionRepository.saveAndFlush(solution);

        // Get all the solutionList
        restSolutionMockMvc.perform(get("/api/solutions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solution.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].bannerImgFile").value(hasItem(DEFAULT_BANNER_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].bannerIntroduction").value(hasItem(DEFAULT_BANNER_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].summarizeImgFile").value(hasItem(DEFAULT_SUMMARIZE_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].summarizeIntroduction").value(hasItem(DEFAULT_SUMMARIZE_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].significanceTitle").value(hasItem(DEFAULT_SIGNIFICANCE_TITLE.toString())))
            .andExpect(jsonPath("$.[*].significanceImgFile").value(hasItem(DEFAULT_SIGNIFICANCE_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].significanceIntroduction").value(hasItem(DEFAULT_SIGNIFICANCE_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].detailAnalysisTitle").value(hasItem(DEFAULT_DETAIL_ANALYSIS_TITLE.toString())));
    }

    @Test
    @Transactional
    public void getSolution() throws Exception {
        // Initialize the database
        solutionRepository.saveAndFlush(solution);

        // Get the solution
        restSolutionMockMvc.perform(get("/api/solutions/{id}", solution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(solution.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.introduction").value(DEFAULT_INTRODUCTION.toString()))
            .andExpect(jsonPath("$.bannerImgFile").value(DEFAULT_BANNER_IMG_FILE.toString()))
            .andExpect(jsonPath("$.bannerIntroduction").value(DEFAULT_BANNER_INTRODUCTION.toString()))
            .andExpect(jsonPath("$.summarizeImgFile").value(DEFAULT_SUMMARIZE_IMG_FILE.toString()))
            .andExpect(jsonPath("$.summarizeIntroduction").value(DEFAULT_SUMMARIZE_INTRODUCTION.toString()))
            .andExpect(jsonPath("$.significanceTitle").value(DEFAULT_SIGNIFICANCE_TITLE.toString()))
            .andExpect(jsonPath("$.significanceImgFile").value(DEFAULT_SIGNIFICANCE_IMG_FILE.toString()))
            .andExpect(jsonPath("$.significanceIntroduction").value(DEFAULT_SIGNIFICANCE_INTRODUCTION.toString()))
            .andExpect(jsonPath("$.detailAnalysisTitle").value(DEFAULT_DETAIL_ANALYSIS_TITLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSolution() throws Exception {
        // Get the solution
        restSolutionMockMvc.perform(get("/api/solutions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSolution() throws Exception {
        // Initialize the database
        solutionService.save(solution);

        int databaseSizeBeforeUpdate = solutionRepository.findAll().size();

        // Update the solution
        Solution updatedSolution = solutionRepository.findOne(solution.getId());
        // Disconnect from session so that the updates on updatedSolution are not directly saved in db
        em.detach(updatedSolution);
        updatedSolution
            .title(UPDATED_TITLE)
            .introduction(UPDATED_INTRODUCTION)
            .bannerImgFile(UPDATED_BANNER_IMG_FILE)
            .bannerIntroduction(UPDATED_BANNER_INTRODUCTION)
            .summarizeImgFile(UPDATED_SUMMARIZE_IMG_FILE)
            .summarizeIntroduction(UPDATED_SUMMARIZE_INTRODUCTION)
            .significanceTitle(UPDATED_SIGNIFICANCE_TITLE)
            .significanceImgFile(UPDATED_SIGNIFICANCE_IMG_FILE)
            .significanceIntroduction(UPDATED_SIGNIFICANCE_INTRODUCTION)
            .detailAnalysisTitle(UPDATED_DETAIL_ANALYSIS_TITLE);

        restSolutionMockMvc.perform(put("/api/solutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSolution)))
            .andExpect(status().isOk());

        // Validate the Solution in the database
        List<Solution> solutionList = solutionRepository.findAll();
        assertThat(solutionList).hasSize(databaseSizeBeforeUpdate);
        Solution testSolution = solutionList.get(solutionList.size() - 1);
        assertThat(testSolution.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSolution.getIntroduction()).isEqualTo(UPDATED_INTRODUCTION);
        assertThat(testSolution.getBannerImgFile()).isEqualTo(UPDATED_BANNER_IMG_FILE);
        assertThat(testSolution.getBannerIntroduction()).isEqualTo(UPDATED_BANNER_INTRODUCTION);
        assertThat(testSolution.getSummarizeImgFile()).isEqualTo(UPDATED_SUMMARIZE_IMG_FILE);
        assertThat(testSolution.getSummarizeIntroduction()).isEqualTo(UPDATED_SUMMARIZE_INTRODUCTION);
        assertThat(testSolution.getSignificanceTitle()).isEqualTo(UPDATED_SIGNIFICANCE_TITLE);
        assertThat(testSolution.getSignificanceImgFile()).isEqualTo(UPDATED_SIGNIFICANCE_IMG_FILE);
        assertThat(testSolution.getSignificanceIntroduction()).isEqualTo(UPDATED_SIGNIFICANCE_INTRODUCTION);
        assertThat(testSolution.getDetailAnalysisTitle()).isEqualTo(UPDATED_DETAIL_ANALYSIS_TITLE);

        // Validate the Solution in Elasticsearch
        Solution solutionEs = solutionSearchRepository.findOne(testSolution.getId());
        assertThat(solutionEs).isEqualToIgnoringGivenFields(testSolution);
    }

    @Test
    @Transactional
    public void updateNonExistingSolution() throws Exception {
        int databaseSizeBeforeUpdate = solutionRepository.findAll().size();

        // Create the Solution

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSolutionMockMvc.perform(put("/api/solutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solution)))
            .andExpect(status().isCreated());

        // Validate the Solution in the database
        List<Solution> solutionList = solutionRepository.findAll();
        assertThat(solutionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSolution() throws Exception {
        // Initialize the database
        solutionService.save(solution);

        int databaseSizeBeforeDelete = solutionRepository.findAll().size();

        // Get the solution
        restSolutionMockMvc.perform(delete("/api/solutions/{id}", solution.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean solutionExistsInEs = solutionSearchRepository.exists(solution.getId());
        assertThat(solutionExistsInEs).isFalse();

        // Validate the database is empty
        List<Solution> solutionList = solutionRepository.findAll();
        assertThat(solutionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSolution() throws Exception {
        // Initialize the database
        solutionService.save(solution);

        // Search the solution
        restSolutionMockMvc.perform(get("/api/_search/solutions?query=id:" + solution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solution.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].bannerImgFile").value(hasItem(DEFAULT_BANNER_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].bannerIntroduction").value(hasItem(DEFAULT_BANNER_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].summarizeImgFile").value(hasItem(DEFAULT_SUMMARIZE_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].summarizeIntroduction").value(hasItem(DEFAULT_SUMMARIZE_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].significanceTitle").value(hasItem(DEFAULT_SIGNIFICANCE_TITLE.toString())))
            .andExpect(jsonPath("$.[*].significanceImgFile").value(hasItem(DEFAULT_SIGNIFICANCE_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].significanceIntroduction").value(hasItem(DEFAULT_SIGNIFICANCE_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].detailAnalysisTitle").value(hasItem(DEFAULT_DETAIL_ANALYSIS_TITLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Solution.class);
        Solution solution1 = new Solution();
        solution1.setId(1L);
        Solution solution2 = new Solution();
        solution2.setId(solution1.getId());
        assertThat(solution1).isEqualTo(solution2);
        solution2.setId(2L);
        assertThat(solution1).isNotEqualTo(solution2);
        solution1.setId(null);
        assertThat(solution1).isNotEqualTo(solution2);
    }
}
