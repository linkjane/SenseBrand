package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.BrandRank;
import io.github.jhipster.application.repository.BrandRankRepository;
import io.github.jhipster.application.service.BrandRankService;
import io.github.jhipster.application.repository.search.BrandRankSearchRepository;
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
 * Test class for the BrandRankResource REST controller.
 *
 * @see BrandRankResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class BrandRankResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BrandRankRepository brandRankRepository;

    @Autowired
    private BrandRankService brandRankService;

    @Autowired
    private BrandRankSearchRepository brandRankSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBrandRankMockMvc;

    private BrandRank brandRank;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BrandRankResource brandRankResource = new BrandRankResource(brandRankService);
        this.restBrandRankMockMvc = MockMvcBuilders.standaloneSetup(brandRankResource)
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
    public static BrandRank createEntity(EntityManager em) {
        BrandRank brandRank = new BrandRank()
            .name(DEFAULT_NAME);
        return brandRank;
    }

    @Before
    public void initTest() {
        brandRankSearchRepository.deleteAll();
        brandRank = createEntity(em);
    }

    @Test
    @Transactional
    public void createBrandRank() throws Exception {
        int databaseSizeBeforeCreate = brandRankRepository.findAll().size();

        // Create the BrandRank
        restBrandRankMockMvc.perform(post("/api/brand-ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandRank)))
            .andExpect(status().isCreated());

        // Validate the BrandRank in the database
        List<BrandRank> brandRankList = brandRankRepository.findAll();
        assertThat(brandRankList).hasSize(databaseSizeBeforeCreate + 1);
        BrandRank testBrandRank = brandRankList.get(brandRankList.size() - 1);
        assertThat(testBrandRank.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the BrandRank in Elasticsearch
        BrandRank brandRankEs = brandRankSearchRepository.findOne(testBrandRank.getId());
        assertThat(brandRankEs).isEqualToIgnoringGivenFields(testBrandRank);
    }

    @Test
    @Transactional
    public void createBrandRankWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = brandRankRepository.findAll().size();

        // Create the BrandRank with an existing ID
        brandRank.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBrandRankMockMvc.perform(post("/api/brand-ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandRank)))
            .andExpect(status().isBadRequest());

        // Validate the BrandRank in the database
        List<BrandRank> brandRankList = brandRankRepository.findAll();
        assertThat(brandRankList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = brandRankRepository.findAll().size();
        // set the field null
        brandRank.setName(null);

        // Create the BrandRank, which fails.

        restBrandRankMockMvc.perform(post("/api/brand-ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandRank)))
            .andExpect(status().isBadRequest());

        List<BrandRank> brandRankList = brandRankRepository.findAll();
        assertThat(brandRankList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBrandRanks() throws Exception {
        // Initialize the database
        brandRankRepository.saveAndFlush(brandRank);

        // Get all the brandRankList
        restBrandRankMockMvc.perform(get("/api/brand-ranks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brandRank.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getBrandRank() throws Exception {
        // Initialize the database
        brandRankRepository.saveAndFlush(brandRank);

        // Get the brandRank
        restBrandRankMockMvc.perform(get("/api/brand-ranks/{id}", brandRank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(brandRank.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBrandRank() throws Exception {
        // Get the brandRank
        restBrandRankMockMvc.perform(get("/api/brand-ranks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBrandRank() throws Exception {
        // Initialize the database
        brandRankService.save(brandRank);

        int databaseSizeBeforeUpdate = brandRankRepository.findAll().size();

        // Update the brandRank
        BrandRank updatedBrandRank = brandRankRepository.findOne(brandRank.getId());
        // Disconnect from session so that the updates on updatedBrandRank are not directly saved in db
        em.detach(updatedBrandRank);
        updatedBrandRank
            .name(UPDATED_NAME);

        restBrandRankMockMvc.perform(put("/api/brand-ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBrandRank)))
            .andExpect(status().isOk());

        // Validate the BrandRank in the database
        List<BrandRank> brandRankList = brandRankRepository.findAll();
        assertThat(brandRankList).hasSize(databaseSizeBeforeUpdate);
        BrandRank testBrandRank = brandRankList.get(brandRankList.size() - 1);
        assertThat(testBrandRank.getName()).isEqualTo(UPDATED_NAME);

        // Validate the BrandRank in Elasticsearch
        BrandRank brandRankEs = brandRankSearchRepository.findOne(testBrandRank.getId());
        assertThat(brandRankEs).isEqualToIgnoringGivenFields(testBrandRank);
    }

    @Test
    @Transactional
    public void updateNonExistingBrandRank() throws Exception {
        int databaseSizeBeforeUpdate = brandRankRepository.findAll().size();

        // Create the BrandRank

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBrandRankMockMvc.perform(put("/api/brand-ranks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandRank)))
            .andExpect(status().isCreated());

        // Validate the BrandRank in the database
        List<BrandRank> brandRankList = brandRankRepository.findAll();
        assertThat(brandRankList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBrandRank() throws Exception {
        // Initialize the database
        brandRankService.save(brandRank);

        int databaseSizeBeforeDelete = brandRankRepository.findAll().size();

        // Get the brandRank
        restBrandRankMockMvc.perform(delete("/api/brand-ranks/{id}", brandRank.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean brandRankExistsInEs = brandRankSearchRepository.exists(brandRank.getId());
        assertThat(brandRankExistsInEs).isFalse();

        // Validate the database is empty
        List<BrandRank> brandRankList = brandRankRepository.findAll();
        assertThat(brandRankList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBrandRank() throws Exception {
        // Initialize the database
        brandRankService.save(brandRank);

        // Search the brandRank
        restBrandRankMockMvc.perform(get("/api/_search/brand-ranks?query=id:" + brandRank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brandRank.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BrandRank.class);
        BrandRank brandRank1 = new BrandRank();
        brandRank1.setId(1L);
        BrandRank brandRank2 = new BrandRank();
        brandRank2.setId(brandRank1.getId());
        assertThat(brandRank1).isEqualTo(brandRank2);
        brandRank2.setId(2L);
        assertThat(brandRank1).isNotEqualTo(brandRank2);
        brandRank1.setId(null);
        assertThat(brandRank1).isNotEqualTo(brandRank2);
    }
}
