package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.SolutionDetailImg;
import io.github.jhipster.application.repository.SolutionDetailImgRepository;
import io.github.jhipster.application.service.SolutionDetailImgService;
import io.github.jhipster.application.repository.search.SolutionDetailImgSearchRepository;
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
 * Test class for the SolutionDetailImgResource REST controller.
 *
 * @see SolutionDetailImgResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class SolutionDetailImgResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_FILE = "AAAAAAAAAA";
    private static final String UPDATED_IMG_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCTION = "BBBBBBBBBB";

    @Autowired
    private SolutionDetailImgRepository solutionDetailImgRepository;

    @Autowired
    private SolutionDetailImgService solutionDetailImgService;

    @Autowired
    private SolutionDetailImgSearchRepository solutionDetailImgSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSolutionDetailImgMockMvc;

    private SolutionDetailImg solutionDetailImg;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SolutionDetailImgResource solutionDetailImgResource = new SolutionDetailImgResource(solutionDetailImgService);
        this.restSolutionDetailImgMockMvc = MockMvcBuilders.standaloneSetup(solutionDetailImgResource)
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
    public static SolutionDetailImg createEntity(EntityManager em) {
        SolutionDetailImg solutionDetailImg = new SolutionDetailImg()
            .title(DEFAULT_TITLE)
            .imgFile(DEFAULT_IMG_FILE)
            .introduction(DEFAULT_INTRODUCTION);
        return solutionDetailImg;
    }

    @Before
    public void initTest() {
        solutionDetailImgSearchRepository.deleteAll();
        solutionDetailImg = createEntity(em);
    }

    @Test
    @Transactional
    public void createSolutionDetailImg() throws Exception {
        int databaseSizeBeforeCreate = solutionDetailImgRepository.findAll().size();

        // Create the SolutionDetailImg
        restSolutionDetailImgMockMvc.perform(post("/api/solution-detail-imgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solutionDetailImg)))
            .andExpect(status().isCreated());

        // Validate the SolutionDetailImg in the database
        List<SolutionDetailImg> solutionDetailImgList = solutionDetailImgRepository.findAll();
        assertThat(solutionDetailImgList).hasSize(databaseSizeBeforeCreate + 1);
        SolutionDetailImg testSolutionDetailImg = solutionDetailImgList.get(solutionDetailImgList.size() - 1);
        assertThat(testSolutionDetailImg.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSolutionDetailImg.getImgFile()).isEqualTo(DEFAULT_IMG_FILE);
        assertThat(testSolutionDetailImg.getIntroduction()).isEqualTo(DEFAULT_INTRODUCTION);

        // Validate the SolutionDetailImg in Elasticsearch
        SolutionDetailImg solutionDetailImgEs = solutionDetailImgSearchRepository.findOne(testSolutionDetailImg.getId());
        assertThat(solutionDetailImgEs).isEqualToIgnoringGivenFields(testSolutionDetailImg);
    }

    @Test
    @Transactional
    public void createSolutionDetailImgWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = solutionDetailImgRepository.findAll().size();

        // Create the SolutionDetailImg with an existing ID
        solutionDetailImg.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolutionDetailImgMockMvc.perform(post("/api/solution-detail-imgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solutionDetailImg)))
            .andExpect(status().isBadRequest());

        // Validate the SolutionDetailImg in the database
        List<SolutionDetailImg> solutionDetailImgList = solutionDetailImgRepository.findAll();
        assertThat(solutionDetailImgList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkImgFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = solutionDetailImgRepository.findAll().size();
        // set the field null
        solutionDetailImg.setImgFile(null);

        // Create the SolutionDetailImg, which fails.

        restSolutionDetailImgMockMvc.perform(post("/api/solution-detail-imgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solutionDetailImg)))
            .andExpect(status().isBadRequest());

        List<SolutionDetailImg> solutionDetailImgList = solutionDetailImgRepository.findAll();
        assertThat(solutionDetailImgList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSolutionDetailImgs() throws Exception {
        // Initialize the database
        solutionDetailImgRepository.saveAndFlush(solutionDetailImg);

        // Get all the solutionDetailImgList
        restSolutionDetailImgMockMvc.perform(get("/api/solution-detail-imgs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solutionDetailImg.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].imgFile").value(hasItem(DEFAULT_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())));
    }

    @Test
    @Transactional
    public void getSolutionDetailImg() throws Exception {
        // Initialize the database
        solutionDetailImgRepository.saveAndFlush(solutionDetailImg);

        // Get the solutionDetailImg
        restSolutionDetailImgMockMvc.perform(get("/api/solution-detail-imgs/{id}", solutionDetailImg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(solutionDetailImg.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.imgFile").value(DEFAULT_IMG_FILE.toString()))
            .andExpect(jsonPath("$.introduction").value(DEFAULT_INTRODUCTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSolutionDetailImg() throws Exception {
        // Get the solutionDetailImg
        restSolutionDetailImgMockMvc.perform(get("/api/solution-detail-imgs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSolutionDetailImg() throws Exception {
        // Initialize the database
        solutionDetailImgService.save(solutionDetailImg);

        int databaseSizeBeforeUpdate = solutionDetailImgRepository.findAll().size();

        // Update the solutionDetailImg
        SolutionDetailImg updatedSolutionDetailImg = solutionDetailImgRepository.findOne(solutionDetailImg.getId());
        // Disconnect from session so that the updates on updatedSolutionDetailImg are not directly saved in db
        em.detach(updatedSolutionDetailImg);
        updatedSolutionDetailImg
            .title(UPDATED_TITLE)
            .imgFile(UPDATED_IMG_FILE)
            .introduction(UPDATED_INTRODUCTION);

        restSolutionDetailImgMockMvc.perform(put("/api/solution-detail-imgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSolutionDetailImg)))
            .andExpect(status().isOk());

        // Validate the SolutionDetailImg in the database
        List<SolutionDetailImg> solutionDetailImgList = solutionDetailImgRepository.findAll();
        assertThat(solutionDetailImgList).hasSize(databaseSizeBeforeUpdate);
        SolutionDetailImg testSolutionDetailImg = solutionDetailImgList.get(solutionDetailImgList.size() - 1);
        assertThat(testSolutionDetailImg.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSolutionDetailImg.getImgFile()).isEqualTo(UPDATED_IMG_FILE);
        assertThat(testSolutionDetailImg.getIntroduction()).isEqualTo(UPDATED_INTRODUCTION);

        // Validate the SolutionDetailImg in Elasticsearch
        SolutionDetailImg solutionDetailImgEs = solutionDetailImgSearchRepository.findOne(testSolutionDetailImg.getId());
        assertThat(solutionDetailImgEs).isEqualToIgnoringGivenFields(testSolutionDetailImg);
    }

    @Test
    @Transactional
    public void updateNonExistingSolutionDetailImg() throws Exception {
        int databaseSizeBeforeUpdate = solutionDetailImgRepository.findAll().size();

        // Create the SolutionDetailImg

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSolutionDetailImgMockMvc.perform(put("/api/solution-detail-imgs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(solutionDetailImg)))
            .andExpect(status().isCreated());

        // Validate the SolutionDetailImg in the database
        List<SolutionDetailImg> solutionDetailImgList = solutionDetailImgRepository.findAll();
        assertThat(solutionDetailImgList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSolutionDetailImg() throws Exception {
        // Initialize the database
        solutionDetailImgService.save(solutionDetailImg);

        int databaseSizeBeforeDelete = solutionDetailImgRepository.findAll().size();

        // Get the solutionDetailImg
        restSolutionDetailImgMockMvc.perform(delete("/api/solution-detail-imgs/{id}", solutionDetailImg.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean solutionDetailImgExistsInEs = solutionDetailImgSearchRepository.exists(solutionDetailImg.getId());
        assertThat(solutionDetailImgExistsInEs).isFalse();

        // Validate the database is empty
        List<SolutionDetailImg> solutionDetailImgList = solutionDetailImgRepository.findAll();
        assertThat(solutionDetailImgList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchSolutionDetailImg() throws Exception {
        // Initialize the database
        solutionDetailImgService.save(solutionDetailImg);

        // Search the solutionDetailImg
        restSolutionDetailImgMockMvc.perform(get("/api/_search/solution-detail-imgs?query=id:" + solutionDetailImg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solutionDetailImg.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].imgFile").value(hasItem(DEFAULT_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolutionDetailImg.class);
        SolutionDetailImg solutionDetailImg1 = new SolutionDetailImg();
        solutionDetailImg1.setId(1L);
        SolutionDetailImg solutionDetailImg2 = new SolutionDetailImg();
        solutionDetailImg2.setId(solutionDetailImg1.getId());
        assertThat(solutionDetailImg1).isEqualTo(solutionDetailImg2);
        solutionDetailImg2.setId(2L);
        assertThat(solutionDetailImg1).isNotEqualTo(solutionDetailImg2);
        solutionDetailImg1.setId(null);
        assertThat(solutionDetailImg1).isNotEqualTo(solutionDetailImg2);
    }
}
