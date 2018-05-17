package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.SolutionDetailAnalysisImg;
import io.github.jhipster.application.repository.SolutionDetailAnalysisImgRepository;
import io.github.jhipster.application.service.SolutionDetailAnalysisImgService;
import io.github.jhipster.application.repository.search.SolutionDetailAnalysisImgSearchRepository;
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
 * Test class for the SolutionDetailAnalysisImgResource REST controller.
 *
 * @see SolutionDetailAnalysisImgResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class SolutionDetailAnalysisImgResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_FILE = "AAAAAAAAAA";
    private static final String UPDATED_IMG_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCTION = "BBBBBBBBBB";

    @Autowired
    private SolutionDetailAnalysisImgRepository solutionDetailAnalysisImgRepository;

    @Autowired
    private SolutionDetailAnalysisImgService solutionDetailAnalysisImgService;

    @Autowired
    private SolutionDetailAnalysisImgSearchRepository solutionDetailAnalysisImgSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSolutionDetailAnalysisImgMockMvc;

    private SolutionDetailAnalysisImg solutionDetailAnalysisImg;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SolutionDetailAnalysisImgResource solutionDetailAnalysisImgResource = new SolutionDetailAnalysisImgResource(solutionDetailAnalysisImgService);
        this.restSolutionDetailAnalysisImgMockMvc = MockMvcBuilders.standaloneSetup(solutionDetailAnalysisImgResource)
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
    public static SolutionDetailAnalysisImg createEntity(EntityManager em) {
        SolutionDetailAnalysisImg solutionDetailAnalysisImg = new SolutionDetailAnalysisImg()
            .title(DEFAULT_TITLE)
            .imgFile(DEFAULT_IMG_FILE)
            .introduction(DEFAULT_INTRODUCTION);
        return solutionDetailAnalysisImg;
    }

    @Before
    public void initTest() {
        solutionDetailAnalysisImgSearchRepository.deleteAll();
        solutionDetailAnalysisImg = createEntity(em);
    }

    @Test
    @Transactional
    public void createSolutionDetailAnalysisImg() throws Exception {
        int databaseSizeBeforeCreate = solutionDetailAnalysisImgRepository.findAll().size();

        // Create the SolutionDetailAnalysisImg
        restSolutionDetailAnalysisImgMockMvc.perform(post("/api/solution-detail-analysis-imgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solutionDetailAnalysisImg)))
            .andExpect(status().isCreated());

        // Validate the SolutionDetailAnalysisImg in the database
        List<SolutionDetailAnalysisImg> solutionDetailAnalysisImgList = solutionDetailAnalysisImgRepository.findAll();
        assertThat(solutionDetailAnalysisImgList).hasSize(databaseSizeBeforeCreate + 1);
        SolutionDetailAnalysisImg testSolutionDetailAnalysisImg = solutionDetailAnalysisImgList.get(solutionDetailAnalysisImgList.size() - 1);
        assertThat(testSolutionDetailAnalysisImg.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSolutionDetailAnalysisImg.getImgFile()).isEqualTo(DEFAULT_IMG_FILE);
        assertThat(testSolutionDetailAnalysisImg.getIntroduction()).isEqualTo(DEFAULT_INTRODUCTION);

        // Validate the SolutionDetailAnalysisImg in Elasticsearch
        SolutionDetailAnalysisImg solutionDetailAnalysisImgEs = solutionDetailAnalysisImgSearchRepository.findOne(testSolutionDetailAnalysisImg.getId());
        assertThat(solutionDetailAnalysisImgEs).isEqualToIgnoringGivenFields(testSolutionDetailAnalysisImg);
    }

    @Test
    @Transactional
    public void createSolutionDetailAnalysisImgWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = solutionDetailAnalysisImgRepository.findAll().size();

        // Create the SolutionDetailAnalysisImg with an existing ID
        solutionDetailAnalysisImg.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolutionDetailAnalysisImgMockMvc.perform(post("/api/solution-detail-analysis-imgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solutionDetailAnalysisImg)))
            .andExpect(status().isBadRequest());

        // Validate the SolutionDetailAnalysisImg in the database
        List<SolutionDetailAnalysisImg> solutionDetailAnalysisImgList = solutionDetailAnalysisImgRepository.findAll();
        assertThat(solutionDetailAnalysisImgList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkImgFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = solutionDetailAnalysisImgRepository.findAll().size();
        // set the field null
        solutionDetailAnalysisImg.setImgFile(null);

        // Create the SolutionDetailAnalysisImg, which fails.

        restSolutionDetailAnalysisImgMockMvc.perform(post("/api/solution-detail-analysis-imgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solutionDetailAnalysisImg)))
            .andExpect(status().isBadRequest());

        List<SolutionDetailAnalysisImg> solutionDetailAnalysisImgList = solutionDetailAnalysisImgRepository.findAll();
        assertThat(solutionDetailAnalysisImgList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSolutionDetailAnalysisImgs() throws Exception {
        // Initialize the database
        solutionDetailAnalysisImgRepository.saveAndFlush(solutionDetailAnalysisImg);

        // Get all the solutionDetailAnalysisImgList
        restSolutionDetailAnalysisImgMockMvc.perform(get("/api/solution-detail-analysis-imgs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solutionDetailAnalysisImg.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].imgFile").value(hasItem(DEFAULT_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())));
    }

    @Test
    @Transactional
    public void getSolutionDetailAnalysisImg() throws Exception {
        // Initialize the database
        solutionDetailAnalysisImgRepository.saveAndFlush(solutionDetailAnalysisImg);

        // Get the solutionDetailAnalysisImg
        restSolutionDetailAnalysisImgMockMvc.perform(get("/api/solution-detail-analysis-imgs/{id}", solutionDetailAnalysisImg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(solutionDetailAnalysisImg.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.imgFile").value(DEFAULT_IMG_FILE.toString()))
            .andExpect(jsonPath("$.introduction").value(DEFAULT_INTRODUCTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSolutionDetailAnalysisImg() throws Exception {
        // Get the solutionDetailAnalysisImg
        restSolutionDetailAnalysisImgMockMvc.perform(get("/api/solution-detail-analysis-imgs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSolutionDetailAnalysisImg() throws Exception {
        // Initialize the database
        solutionDetailAnalysisImgService.save(solutionDetailAnalysisImg);

        int databaseSizeBeforeUpdate = solutionDetailAnalysisImgRepository.findAll().size();

        // Update the solutionDetailAnalysisImg
        SolutionDetailAnalysisImg updatedSolutionDetailAnalysisImg = solutionDetailAnalysisImgRepository.findOne(solutionDetailAnalysisImg.getId());
        // Disconnect from session so that the updates on updatedSolutionDetailAnalysisImg are not directly saved in db
        em.detach(updatedSolutionDetailAnalysisImg);
        updatedSolutionDetailAnalysisImg
            .title(UPDATED_TITLE)
            .imgFile(UPDATED_IMG_FILE)
            .introduction(UPDATED_INTRODUCTION);

        restSolutionDetailAnalysisImgMockMvc.perform(put("/api/solution-detail-analysis-imgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSolutionDetailAnalysisImg)))
            .andExpect(status().isOk());

        // Validate the SolutionDetailAnalysisImg in the database
        List<SolutionDetailAnalysisImg> solutionDetailAnalysisImgList = solutionDetailAnalysisImgRepository.findAll();
        assertThat(solutionDetailAnalysisImgList).hasSize(databaseSizeBeforeUpdate);
        SolutionDetailAnalysisImg testSolutionDetailAnalysisImg = solutionDetailAnalysisImgList.get(solutionDetailAnalysisImgList.size() - 1);
        assertThat(testSolutionDetailAnalysisImg.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSolutionDetailAnalysisImg.getImgFile()).isEqualTo(UPDATED_IMG_FILE);
        assertThat(testSolutionDetailAnalysisImg.getIntroduction()).isEqualTo(UPDATED_INTRODUCTION);

        // Validate the SolutionDetailAnalysisImg in Elasticsearch
        SolutionDetailAnalysisImg solutionDetailAnalysisImgEs = solutionDetailAnalysisImgSearchRepository.findOne(testSolutionDetailAnalysisImg.getId());
        assertThat(solutionDetailAnalysisImgEs).isEqualToIgnoringGivenFields(testSolutionDetailAnalysisImg);
    }

    @Test
    @Transactional
    public void updateNonExistingSolutionDetailAnalysisImg() throws Exception {
        int databaseSizeBeforeUpdate = solutionDetailAnalysisImgRepository.findAll().size();

        // Create the SolutionDetailAnalysisImg

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSolutionDetailAnalysisImgMockMvc.perform(put("/api/solution-detail-analysis-imgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solutionDetailAnalysisImg)))
            .andExpect(status().isCreated());

        // Validate the SolutionDetailAnalysisImg in the database
        List<SolutionDetailAnalysisImg> solutionDetailAnalysisImgList = solutionDetailAnalysisImgRepository.findAll();
        assertThat(solutionDetailAnalysisImgList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSolutionDetailAnalysisImg() throws Exception {
        // Initialize the database
        solutionDetailAnalysisImgService.save(solutionDetailAnalysisImg);

        int databaseSizeBeforeDelete = solutionDetailAnalysisImgRepository.findAll().size();

        // Get the solutionDetailAnalysisImg
        restSolutionDetailAnalysisImgMockMvc.perform(delete("/api/solution-detail-analysis-imgs/{id}", solutionDetailAnalysisImg.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean solutionDetailAnalysisImgExistsInEs = solutionDetailAnalysisImgSearchRepository.exists(solutionDetailAnalysisImg.getId());
        assertThat(solutionDetailAnalysisImgExistsInEs).isFalse();

        // Validate the database is empty
        List<SolutionDetailAnalysisImg> solutionDetailAnalysisImgList = solutionDetailAnalysisImgRepository.findAll();
        assertThat(solutionDetailAnalysisImgList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSolutionDetailAnalysisImg() throws Exception {
        // Initialize the database
        solutionDetailAnalysisImgService.save(solutionDetailAnalysisImg);

        // Search the solutionDetailAnalysisImg
        restSolutionDetailAnalysisImgMockMvc.perform(get("/api/_search/solution-detail-analysis-imgs?query=id:" + solutionDetailAnalysisImg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solutionDetailAnalysisImg.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].imgFile").value(hasItem(DEFAULT_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolutionDetailAnalysisImg.class);
        SolutionDetailAnalysisImg solutionDetailAnalysisImg1 = new SolutionDetailAnalysisImg();
        solutionDetailAnalysisImg1.setId(1L);
        SolutionDetailAnalysisImg solutionDetailAnalysisImg2 = new SolutionDetailAnalysisImg();
        solutionDetailAnalysisImg2.setId(solutionDetailAnalysisImg1.getId());
        assertThat(solutionDetailAnalysisImg1).isEqualTo(solutionDetailAnalysisImg2);
        solutionDetailAnalysisImg2.setId(2L);
        assertThat(solutionDetailAnalysisImg1).isNotEqualTo(solutionDetailAnalysisImg2);
        solutionDetailAnalysisImg1.setId(null);
        assertThat(solutionDetailAnalysisImg1).isNotEqualTo(solutionDetailAnalysisImg2);
    }
}
