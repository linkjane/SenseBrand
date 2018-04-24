package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.BrandSubDetails;
import io.github.jhipster.application.repository.BrandSubDetailsRepository;
import io.github.jhipster.application.service.BrandSubDetailsService;
import io.github.jhipster.application.repository.search.BrandSubDetailsSearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BrandSubDetailsResource REST controller.
 *
 * @see BrandSubDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class BrandSubDetailsResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCTION = "BBBBBBBBBB";

    private static final String DEFAULT_BANNER_IMG_FILE = "AAAAAAAAAA";
    private static final String UPDATED_BANNER_IMG_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_TIME = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private BrandSubDetailsRepository brandSubDetailsRepository;

    @Autowired
    private BrandSubDetailsService brandSubDetailsService;

    @Autowired
    private BrandSubDetailsSearchRepository brandSubDetailsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBrandSubDetailsMockMvc;

    private BrandSubDetails brandSubDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BrandSubDetailsResource brandSubDetailsResource = new BrandSubDetailsResource(brandSubDetailsService);
        this.restBrandSubDetailsMockMvc = MockMvcBuilders.standaloneSetup(brandSubDetailsResource)
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
    public static BrandSubDetails createEntity(EntityManager em) {
        BrandSubDetails brandSubDetails = new BrandSubDetails()
            .title(DEFAULT_TITLE)
            .introduction(DEFAULT_INTRODUCTION)
            .bannerImgFile(DEFAULT_BANNER_IMG_FILE)
            .content(DEFAULT_CONTENT)
            .createdTime(DEFAULT_CREATED_TIME);
        return brandSubDetails;
    }

    @Before
    public void initTest() {
        brandSubDetailsSearchRepository.deleteAll();
        brandSubDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createBrandSubDetails() throws Exception {
        int databaseSizeBeforeCreate = brandSubDetailsRepository.findAll().size();

        // Create the BrandSubDetails
        restBrandSubDetailsMockMvc.perform(post("/api/brand-sub-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandSubDetails)))
            .andExpect(status().isCreated());

        // Validate the BrandSubDetails in the database
        List<BrandSubDetails> brandSubDetailsList = brandSubDetailsRepository.findAll();
        assertThat(brandSubDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        BrandSubDetails testBrandSubDetails = brandSubDetailsList.get(brandSubDetailsList.size() - 1);
        assertThat(testBrandSubDetails.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBrandSubDetails.getIntroduction()).isEqualTo(DEFAULT_INTRODUCTION);
        assertThat(testBrandSubDetails.getBannerImgFile()).isEqualTo(DEFAULT_BANNER_IMG_FILE);
        assertThat(testBrandSubDetails.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testBrandSubDetails.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);

        // Validate the BrandSubDetails in Elasticsearch
        BrandSubDetails brandSubDetailsEs = brandSubDetailsSearchRepository.findOne(testBrandSubDetails.getId());
        assertThat(brandSubDetailsEs).isEqualToIgnoringGivenFields(testBrandSubDetails);
    }

    @Test
    @Transactional
    public void createBrandSubDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = brandSubDetailsRepository.findAll().size();

        // Create the BrandSubDetails with an existing ID
        brandSubDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBrandSubDetailsMockMvc.perform(post("/api/brand-sub-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandSubDetails)))
            .andExpect(status().isBadRequest());

        // Validate the BrandSubDetails in the database
        List<BrandSubDetails> brandSubDetailsList = brandSubDetailsRepository.findAll();
        assertThat(brandSubDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = brandSubDetailsRepository.findAll().size();
        // set the field null
        brandSubDetails.setTitle(null);

        // Create the BrandSubDetails, which fails.

        restBrandSubDetailsMockMvc.perform(post("/api/brand-sub-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandSubDetails)))
            .andExpect(status().isBadRequest());

        List<BrandSubDetails> brandSubDetailsList = brandSubDetailsRepository.findAll();
        assertThat(brandSubDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBrandSubDetails() throws Exception {
        // Initialize the database
        brandSubDetailsRepository.saveAndFlush(brandSubDetails);

        // Get all the brandSubDetailsList
        restBrandSubDetailsMockMvc.perform(get("/api/brand-sub-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brandSubDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].bannerImgFile").value(hasItem(DEFAULT_BANNER_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())));
    }

    @Test
    @Transactional
    public void getBrandSubDetails() throws Exception {
        // Initialize the database
        brandSubDetailsRepository.saveAndFlush(brandSubDetails);

        // Get the brandSubDetails
        restBrandSubDetailsMockMvc.perform(get("/api/brand-sub-details/{id}", brandSubDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(brandSubDetails.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.introduction").value(DEFAULT_INTRODUCTION.toString()))
            .andExpect(jsonPath("$.bannerImgFile").value(DEFAULT_BANNER_IMG_FILE.toString()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBrandSubDetails() throws Exception {
        // Get the brandSubDetails
        restBrandSubDetailsMockMvc.perform(get("/api/brand-sub-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBrandSubDetails() throws Exception {
        // Initialize the database
        brandSubDetailsService.save(brandSubDetails);

        int databaseSizeBeforeUpdate = brandSubDetailsRepository.findAll().size();

        // Update the brandSubDetails
        BrandSubDetails updatedBrandSubDetails = brandSubDetailsRepository.findOne(brandSubDetails.getId());
        // Disconnect from session so that the updates on updatedBrandSubDetails are not directly saved in db
        em.detach(updatedBrandSubDetails);
        updatedBrandSubDetails
            .title(UPDATED_TITLE)
            .introduction(UPDATED_INTRODUCTION)
            .bannerImgFile(UPDATED_BANNER_IMG_FILE)
            .content(UPDATED_CONTENT)
            .createdTime(UPDATED_CREATED_TIME);

        restBrandSubDetailsMockMvc.perform(put("/api/brand-sub-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBrandSubDetails)))
            .andExpect(status().isOk());

        // Validate the BrandSubDetails in the database
        List<BrandSubDetails> brandSubDetailsList = brandSubDetailsRepository.findAll();
        assertThat(brandSubDetailsList).hasSize(databaseSizeBeforeUpdate);
        BrandSubDetails testBrandSubDetails = brandSubDetailsList.get(brandSubDetailsList.size() - 1);
        assertThat(testBrandSubDetails.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBrandSubDetails.getIntroduction()).isEqualTo(UPDATED_INTRODUCTION);
        assertThat(testBrandSubDetails.getBannerImgFile()).isEqualTo(UPDATED_BANNER_IMG_FILE);
        assertThat(testBrandSubDetails.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testBrandSubDetails.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);

        // Validate the BrandSubDetails in Elasticsearch
        BrandSubDetails brandSubDetailsEs = brandSubDetailsSearchRepository.findOne(testBrandSubDetails.getId());
        assertThat(brandSubDetailsEs).isEqualToIgnoringGivenFields(testBrandSubDetails);
    }

    @Test
    @Transactional
    public void updateNonExistingBrandSubDetails() throws Exception {
        int databaseSizeBeforeUpdate = brandSubDetailsRepository.findAll().size();

        // Create the BrandSubDetails

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBrandSubDetailsMockMvc.perform(put("/api/brand-sub-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandSubDetails)))
            .andExpect(status().isCreated());

        // Validate the BrandSubDetails in the database
        List<BrandSubDetails> brandSubDetailsList = brandSubDetailsRepository.findAll();
        assertThat(brandSubDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBrandSubDetails() throws Exception {
        // Initialize the database
        brandSubDetailsService.save(brandSubDetails);

        int databaseSizeBeforeDelete = brandSubDetailsRepository.findAll().size();

        // Get the brandSubDetails
        restBrandSubDetailsMockMvc.perform(delete("/api/brand-sub-details/{id}", brandSubDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean brandSubDetailsExistsInEs = brandSubDetailsSearchRepository.exists(brandSubDetails.getId());
        assertThat(brandSubDetailsExistsInEs).isFalse();

        // Validate the database is empty
        List<BrandSubDetails> brandSubDetailsList = brandSubDetailsRepository.findAll();
        assertThat(brandSubDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBrandSubDetails() throws Exception {
        // Initialize the database
        brandSubDetailsService.save(brandSubDetails);

        // Search the brandSubDetails
        restBrandSubDetailsMockMvc.perform(get("/api/_search/brand-sub-details?query=id:" + brandSubDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brandSubDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].bannerImgFile").value(hasItem(DEFAULT_BANNER_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BrandSubDetails.class);
        BrandSubDetails brandSubDetails1 = new BrandSubDetails();
        brandSubDetails1.setId(1L);
        BrandSubDetails brandSubDetails2 = new BrandSubDetails();
        brandSubDetails2.setId(brandSubDetails1.getId());
        assertThat(brandSubDetails1).isEqualTo(brandSubDetails2);
        brandSubDetails2.setId(2L);
        assertThat(brandSubDetails1).isNotEqualTo(brandSubDetails2);
        brandSubDetails1.setId(null);
        assertThat(brandSubDetails1).isNotEqualTo(brandSubDetails2);
    }
}
