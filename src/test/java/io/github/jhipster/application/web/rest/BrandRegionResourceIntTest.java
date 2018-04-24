package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.BrandRegion;
import io.github.jhipster.application.repository.BrandRegionRepository;
import io.github.jhipster.application.service.BrandRegionService;
import io.github.jhipster.application.repository.search.BrandRegionSearchRepository;
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
 * Test class for the BrandRegionResource REST controller.
 *
 * @see BrandRegionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class BrandRegionResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BrandRegionRepository brandRegionRepository;

    @Autowired
    private BrandRegionService brandRegionService;

    @Autowired
    private BrandRegionSearchRepository brandRegionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBrandRegionMockMvc;

    private BrandRegion brandRegion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BrandRegionResource brandRegionResource = new BrandRegionResource(brandRegionService);
        this.restBrandRegionMockMvc = MockMvcBuilders.standaloneSetup(brandRegionResource)
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
    public static BrandRegion createEntity(EntityManager em) {
        BrandRegion brandRegion = new BrandRegion()
            .name(DEFAULT_NAME);
        return brandRegion;
    }

    @Before
    public void initTest() {
        brandRegionSearchRepository.deleteAll();
        brandRegion = createEntity(em);
    }

    @Test
    @Transactional
    public void createBrandRegion() throws Exception {
        int databaseSizeBeforeCreate = brandRegionRepository.findAll().size();

        // Create the BrandRegion
        restBrandRegionMockMvc.perform(post("/api/brand-regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandRegion)))
            .andExpect(status().isCreated());

        // Validate the BrandRegion in the database
        List<BrandRegion> brandRegionList = brandRegionRepository.findAll();
        assertThat(brandRegionList).hasSize(databaseSizeBeforeCreate + 1);
        BrandRegion testBrandRegion = brandRegionList.get(brandRegionList.size() - 1);
        assertThat(testBrandRegion.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the BrandRegion in Elasticsearch
        BrandRegion brandRegionEs = brandRegionSearchRepository.findOne(testBrandRegion.getId());
        assertThat(brandRegionEs).isEqualToIgnoringGivenFields(testBrandRegion);
    }

    @Test
    @Transactional
    public void createBrandRegionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = brandRegionRepository.findAll().size();

        // Create the BrandRegion with an existing ID
        brandRegion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBrandRegionMockMvc.perform(post("/api/brand-regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandRegion)))
            .andExpect(status().isBadRequest());

        // Validate the BrandRegion in the database
        List<BrandRegion> brandRegionList = brandRegionRepository.findAll();
        assertThat(brandRegionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = brandRegionRepository.findAll().size();
        // set the field null
        brandRegion.setName(null);

        // Create the BrandRegion, which fails.

        restBrandRegionMockMvc.perform(post("/api/brand-regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandRegion)))
            .andExpect(status().isBadRequest());

        List<BrandRegion> brandRegionList = brandRegionRepository.findAll();
        assertThat(brandRegionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBrandRegions() throws Exception {
        // Initialize the database
        brandRegionRepository.saveAndFlush(brandRegion);

        // Get all the brandRegionList
        restBrandRegionMockMvc.perform(get("/api/brand-regions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brandRegion.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getBrandRegion() throws Exception {
        // Initialize the database
        brandRegionRepository.saveAndFlush(brandRegion);

        // Get the brandRegion
        restBrandRegionMockMvc.perform(get("/api/brand-regions/{id}", brandRegion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(brandRegion.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBrandRegion() throws Exception {
        // Get the brandRegion
        restBrandRegionMockMvc.perform(get("/api/brand-regions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBrandRegion() throws Exception {
        // Initialize the database
        brandRegionService.save(brandRegion);

        int databaseSizeBeforeUpdate = brandRegionRepository.findAll().size();

        // Update the brandRegion
        BrandRegion updatedBrandRegion = brandRegionRepository.findOne(brandRegion.getId());
        // Disconnect from session so that the updates on updatedBrandRegion are not directly saved in db
        em.detach(updatedBrandRegion);
        updatedBrandRegion
            .name(UPDATED_NAME);

        restBrandRegionMockMvc.perform(put("/api/brand-regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBrandRegion)))
            .andExpect(status().isOk());

        // Validate the BrandRegion in the database
        List<BrandRegion> brandRegionList = brandRegionRepository.findAll();
        assertThat(brandRegionList).hasSize(databaseSizeBeforeUpdate);
        BrandRegion testBrandRegion = brandRegionList.get(brandRegionList.size() - 1);
        assertThat(testBrandRegion.getName()).isEqualTo(UPDATED_NAME);

        // Validate the BrandRegion in Elasticsearch
        BrandRegion brandRegionEs = brandRegionSearchRepository.findOne(testBrandRegion.getId());
        assertThat(brandRegionEs).isEqualToIgnoringGivenFields(testBrandRegion);
    }

    @Test
    @Transactional
    public void updateNonExistingBrandRegion() throws Exception {
        int databaseSizeBeforeUpdate = brandRegionRepository.findAll().size();

        // Create the BrandRegion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBrandRegionMockMvc.perform(put("/api/brand-regions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandRegion)))
            .andExpect(status().isCreated());

        // Validate the BrandRegion in the database
        List<BrandRegion> brandRegionList = brandRegionRepository.findAll();
        assertThat(brandRegionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBrandRegion() throws Exception {
        // Initialize the database
        brandRegionService.save(brandRegion);

        int databaseSizeBeforeDelete = brandRegionRepository.findAll().size();

        // Get the brandRegion
        restBrandRegionMockMvc.perform(delete("/api/brand-regions/{id}", brandRegion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean brandRegionExistsInEs = brandRegionSearchRepository.exists(brandRegion.getId());
        assertThat(brandRegionExistsInEs).isFalse();

        // Validate the database is empty
        List<BrandRegion> brandRegionList = brandRegionRepository.findAll();
        assertThat(brandRegionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBrandRegion() throws Exception {
        // Initialize the database
        brandRegionService.save(brandRegion);

        // Search the brandRegion
        restBrandRegionMockMvc.perform(get("/api/_search/brand-regions?query=id:" + brandRegion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brandRegion.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BrandRegion.class);
        BrandRegion brandRegion1 = new BrandRegion();
        brandRegion1.setId(1L);
        BrandRegion brandRegion2 = new BrandRegion();
        brandRegion2.setId(brandRegion1.getId());
        assertThat(brandRegion1).isEqualTo(brandRegion2);
        brandRegion2.setId(2L);
        assertThat(brandRegion1).isNotEqualTo(brandRegion2);
        brandRegion1.setId(null);
        assertThat(brandRegion1).isNotEqualTo(brandRegion2);
    }
}
