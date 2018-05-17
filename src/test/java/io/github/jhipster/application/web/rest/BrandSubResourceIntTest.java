package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.BrandSub;
import io.github.jhipster.application.repository.BrandSubRepository;
import io.github.jhipster.application.service.BrandSubService;
import io.github.jhipster.application.repository.search.BrandSubSearchRepository;
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
 * Test class for the BrandSubResource REST controller.
 *
 * @see BrandSubResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class BrandSubResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCTION = "BBBBBBBBBB";

    @Autowired
    private BrandSubRepository brandSubRepository;

    @Autowired
    private BrandSubService brandSubService;

    @Autowired
    private BrandSubSearchRepository brandSubSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBrandSubMockMvc;

    private BrandSub brandSub;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BrandSubResource brandSubResource = new BrandSubResource(brandSubService);
        this.restBrandSubMockMvc = MockMvcBuilders.standaloneSetup(brandSubResource)
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
    public static BrandSub createEntity(EntityManager em) {
        BrandSub brandSub = new BrandSub()
            .title(DEFAULT_TITLE)
            .introduction(DEFAULT_INTRODUCTION);
        return brandSub;
    }

    @Before
    public void initTest() {
        brandSubSearchRepository.deleteAll();
        brandSub = createEntity(em);
    }

    @Test
    @Transactional
    public void createBrandSub() throws Exception {
        int databaseSizeBeforeCreate = brandSubRepository.findAll().size();

        // Create the BrandSub
        restBrandSubMockMvc.perform(post("/api/brand-subs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandSub)))
            .andExpect(status().isCreated());

        // Validate the BrandSub in the database
        List<BrandSub> brandSubList = brandSubRepository.findAll();
        assertThat(brandSubList).hasSize(databaseSizeBeforeCreate + 1);
        BrandSub testBrandSub = brandSubList.get(brandSubList.size() - 1);
        assertThat(testBrandSub.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBrandSub.getIntroduction()).isEqualTo(DEFAULT_INTRODUCTION);

        // Validate the BrandSub in Elasticsearch
        BrandSub brandSubEs = brandSubSearchRepository.findOne(testBrandSub.getId());
        assertThat(brandSubEs).isEqualToIgnoringGivenFields(testBrandSub);
    }

    @Test
    @Transactional
    public void createBrandSubWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = brandSubRepository.findAll().size();

        // Create the BrandSub with an existing ID
        brandSub.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBrandSubMockMvc.perform(post("/api/brand-subs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandSub)))
            .andExpect(status().isBadRequest());

        // Validate the BrandSub in the database
        List<BrandSub> brandSubList = brandSubRepository.findAll();
        assertThat(brandSubList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = brandSubRepository.findAll().size();
        // set the field null
        brandSub.setTitle(null);

        // Create the BrandSub, which fails.

        restBrandSubMockMvc.perform(post("/api/brand-subs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandSub)))
            .andExpect(status().isBadRequest());

        List<BrandSub> brandSubList = brandSubRepository.findAll();
        assertThat(brandSubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBrandSubs() throws Exception {
        // Initialize the database
        brandSubRepository.saveAndFlush(brandSub);

        // Get all the brandSubList
        restBrandSubMockMvc.perform(get("/api/brand-subs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brandSub.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())));
    }

    @Test
    @Transactional
    public void getBrandSub() throws Exception {
        // Initialize the database
        brandSubRepository.saveAndFlush(brandSub);

        // Get the brandSub
        restBrandSubMockMvc.perform(get("/api/brand-subs/{id}", brandSub.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(brandSub.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.introduction").value(DEFAULT_INTRODUCTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBrandSub() throws Exception {
        // Get the brandSub
        restBrandSubMockMvc.perform(get("/api/brand-subs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBrandSub() throws Exception {
        // Initialize the database
        brandSubService.save(brandSub);

        int databaseSizeBeforeUpdate = brandSubRepository.findAll().size();

        // Update the brandSub
        BrandSub updatedBrandSub = brandSubRepository.findOne(brandSub.getId());
        // Disconnect from session so that the updates on updatedBrandSub are not directly saved in db
        em.detach(updatedBrandSub);
        updatedBrandSub
            .title(UPDATED_TITLE)
            .introduction(UPDATED_INTRODUCTION);

        restBrandSubMockMvc.perform(put("/api/brand-subs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBrandSub)))
            .andExpect(status().isOk());

        // Validate the BrandSub in the database
        List<BrandSub> brandSubList = brandSubRepository.findAll();
        assertThat(brandSubList).hasSize(databaseSizeBeforeUpdate);
        BrandSub testBrandSub = brandSubList.get(brandSubList.size() - 1);
        assertThat(testBrandSub.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBrandSub.getIntroduction()).isEqualTo(UPDATED_INTRODUCTION);

        // Validate the BrandSub in Elasticsearch
        BrandSub brandSubEs = brandSubSearchRepository.findOne(testBrandSub.getId());
        assertThat(brandSubEs).isEqualToIgnoringGivenFields(testBrandSub);
    }

    @Test
    @Transactional
    public void updateNonExistingBrandSub() throws Exception {
        int databaseSizeBeforeUpdate = brandSubRepository.findAll().size();

        // Create the BrandSub

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBrandSubMockMvc.perform(put("/api/brand-subs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brandSub)))
            .andExpect(status().isCreated());

        // Validate the BrandSub in the database
        List<BrandSub> brandSubList = brandSubRepository.findAll();
        assertThat(brandSubList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBrandSub() throws Exception {
        // Initialize the database
        brandSubService.save(brandSub);

        int databaseSizeBeforeDelete = brandSubRepository.findAll().size();

        // Get the brandSub
        restBrandSubMockMvc.perform(delete("/api/brand-subs/{id}", brandSub.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean brandSubExistsInEs = brandSubSearchRepository.exists(brandSub.getId());
        assertThat(brandSubExistsInEs).isFalse();

        // Validate the database is empty
        List<BrandSub> brandSubList = brandSubRepository.findAll();
        assertThat(brandSubList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBrandSub() throws Exception {
        // Initialize the database
        brandSubService.save(brandSub);

        // Search the brandSub
        restBrandSubMockMvc.perform(get("/api/_search/brand-subs?query=id:" + brandSub.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brandSub.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BrandSub.class);
        BrandSub brandSub1 = new BrandSub();
        brandSub1.setId(1L);
        BrandSub brandSub2 = new BrandSub();
        brandSub2.setId(brandSub1.getId());
        assertThat(brandSub1).isEqualTo(brandSub2);
        brandSub2.setId(2L);
        assertThat(brandSub1).isNotEqualTo(brandSub2);
        brandSub1.setId(null);
        assertThat(brandSub1).isNotEqualTo(brandSub2);
    }
}
