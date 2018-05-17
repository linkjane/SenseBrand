package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.Brand;
import io.github.jhipster.application.repository.BrandRepository;
import io.github.jhipster.application.service.BrandService;
import io.github.jhipster.application.repository.search.BrandSearchRepository;
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
 * Test class for the BrandResource REST controller.
 *
 * @see BrandResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class BrandResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_BANNER_IMG_FILE = "AAAAAAAAAA";
    private static final String UPDATED_BANNER_IMG_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_PROFILE_IMG_FILE = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_IMG_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCTION = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    private static final String DEFAULT_ESTABLISH_TIME = "AAAAAAAAAA";
    private static final String UPDATED_ESTABLISH_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_CRADLE = "AAAAAAAAAA";
    private static final String UPDATED_CRADLE = "BBBBBBBBBB";

    private static final String DEFAULT_CHAIRMAN = "AAAAAAAAAA";
    private static final String UPDATED_CHAIRMAN = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICIAL_WEBSITE = "AAAAAAAAAA";
    private static final String UPDATED_OFFICIAL_WEBSITE = "BBBBBBBBBB";

    private static final String DEFAULT_AD_PHRASE = "AAAAAAAAAA";
    private static final String UPDATED_AD_PHRASE = "BBBBBBBBBB";

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandService brandService;

    @Autowired
    private BrandSearchRepository brandSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBrandMockMvc;

    private Brand brand;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BrandResource brandResource = new BrandResource(brandService);
        this.restBrandMockMvc = MockMvcBuilders.standaloneSetup(brandResource)
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
    public static Brand createEntity(EntityManager em) {
        Brand brand = new Brand()
            .title(DEFAULT_TITLE)
            .bannerImgFile(DEFAULT_BANNER_IMG_FILE)
            .profileImgFile(DEFAULT_PROFILE_IMG_FILE)
            .introduction(DEFAULT_INTRODUCTION)
            .logo(DEFAULT_LOGO)
            .establishTime(DEFAULT_ESTABLISH_TIME)
            .cradle(DEFAULT_CRADLE)
            .chairman(DEFAULT_CHAIRMAN)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .officialWebsite(DEFAULT_OFFICIAL_WEBSITE)
            .adPhrase(DEFAULT_AD_PHRASE);
        return brand;
    }

    @Before
    public void initTest() {
        brandSearchRepository.deleteAll();
        brand = createEntity(em);
    }

    @Test
    @Transactional
    public void createBrand() throws Exception {
        int databaseSizeBeforeCreate = brandRepository.findAll().size();

        // Create the Brand
        restBrandMockMvc.perform(post("/api/brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brand)))
            .andExpect(status().isCreated());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeCreate + 1);
        Brand testBrand = brandList.get(brandList.size() - 1);
        assertThat(testBrand.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testBrand.getBannerImgFile()).isEqualTo(DEFAULT_BANNER_IMG_FILE);
        assertThat(testBrand.getProfileImgFile()).isEqualTo(DEFAULT_PROFILE_IMG_FILE);
        assertThat(testBrand.getIntroduction()).isEqualTo(DEFAULT_INTRODUCTION);
        assertThat(testBrand.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testBrand.getEstablishTime()).isEqualTo(DEFAULT_ESTABLISH_TIME);
        assertThat(testBrand.getCradle()).isEqualTo(DEFAULT_CRADLE);
        assertThat(testBrand.getChairman()).isEqualTo(DEFAULT_CHAIRMAN);
        assertThat(testBrand.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testBrand.getOfficialWebsite()).isEqualTo(DEFAULT_OFFICIAL_WEBSITE);
        assertThat(testBrand.getAdPhrase()).isEqualTo(DEFAULT_AD_PHRASE);

        // Validate the Brand in Elasticsearch
        Brand brandEs = brandSearchRepository.findOne(testBrand.getId());
        assertThat(brandEs).isEqualToIgnoringGivenFields(testBrand);
    }

    @Test
    @Transactional
    public void createBrandWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = brandRepository.findAll().size();

        // Create the Brand with an existing ID
        brand.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBrandMockMvc.perform(post("/api/brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brand)))
            .andExpect(status().isBadRequest());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = brandRepository.findAll().size();
        // set the field null
        brand.setTitle(null);

        // Create the Brand, which fails.

        restBrandMockMvc.perform(post("/api/brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brand)))
            .andExpect(status().isBadRequest());

        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIntroductionIsRequired() throws Exception {
        int databaseSizeBeforeTest = brandRepository.findAll().size();
        // set the field null
        brand.setIntroduction(null);

        // Create the Brand, which fails.

        restBrandMockMvc.perform(post("/api/brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brand)))
            .andExpect(status().isBadRequest());

        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBrands() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get all the brandList
        restBrandMockMvc.perform(get("/api/brands?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brand.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].bannerImgFile").value(hasItem(DEFAULT_BANNER_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].profileImgFile").value(hasItem(DEFAULT_PROFILE_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO.toString())))
            .andExpect(jsonPath("$.[*].establishTime").value(hasItem(DEFAULT_ESTABLISH_TIME.toString())))
            .andExpect(jsonPath("$.[*].cradle").value(hasItem(DEFAULT_CRADLE.toString())))
            .andExpect(jsonPath("$.[*].chairman").value(hasItem(DEFAULT_CHAIRMAN.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].officialWebsite").value(hasItem(DEFAULT_OFFICIAL_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].adPhrase").value(hasItem(DEFAULT_AD_PHRASE.toString())));
    }

    @Test
    @Transactional
    public void getBrand() throws Exception {
        // Initialize the database
        brandRepository.saveAndFlush(brand);

        // Get the brand
        restBrandMockMvc.perform(get("/api/brands/{id}", brand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(brand.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.bannerImgFile").value(DEFAULT_BANNER_IMG_FILE.toString()))
            .andExpect(jsonPath("$.profileImgFile").value(DEFAULT_PROFILE_IMG_FILE.toString()))
            .andExpect(jsonPath("$.introduction").value(DEFAULT_INTRODUCTION.toString()))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO.toString()))
            .andExpect(jsonPath("$.establishTime").value(DEFAULT_ESTABLISH_TIME.toString()))
            .andExpect(jsonPath("$.cradle").value(DEFAULT_CRADLE.toString()))
            .andExpect(jsonPath("$.chairman").value(DEFAULT_CHAIRMAN.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.officialWebsite").value(DEFAULT_OFFICIAL_WEBSITE.toString()))
            .andExpect(jsonPath("$.adPhrase").value(DEFAULT_AD_PHRASE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBrand() throws Exception {
        // Get the brand
        restBrandMockMvc.perform(get("/api/brands/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBrand() throws Exception {
        // Initialize the database
        brandService.save(brand);

        int databaseSizeBeforeUpdate = brandRepository.findAll().size();

        // Update the brand
        Brand updatedBrand = brandRepository.findOne(brand.getId());
        // Disconnect from session so that the updates on updatedBrand are not directly saved in db
        em.detach(updatedBrand);
        updatedBrand
            .title(UPDATED_TITLE)
            .bannerImgFile(UPDATED_BANNER_IMG_FILE)
            .profileImgFile(UPDATED_PROFILE_IMG_FILE)
            .introduction(UPDATED_INTRODUCTION)
            .logo(UPDATED_LOGO)
            .establishTime(UPDATED_ESTABLISH_TIME)
            .cradle(UPDATED_CRADLE)
            .chairman(UPDATED_CHAIRMAN)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .officialWebsite(UPDATED_OFFICIAL_WEBSITE)
            .adPhrase(UPDATED_AD_PHRASE);

        restBrandMockMvc.perform(put("/api/brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBrand)))
            .andExpect(status().isOk());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeUpdate);
        Brand testBrand = brandList.get(brandList.size() - 1);
        assertThat(testBrand.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testBrand.getBannerImgFile()).isEqualTo(UPDATED_BANNER_IMG_FILE);
        assertThat(testBrand.getProfileImgFile()).isEqualTo(UPDATED_PROFILE_IMG_FILE);
        assertThat(testBrand.getIntroduction()).isEqualTo(UPDATED_INTRODUCTION);
        assertThat(testBrand.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testBrand.getEstablishTime()).isEqualTo(UPDATED_ESTABLISH_TIME);
        assertThat(testBrand.getCradle()).isEqualTo(UPDATED_CRADLE);
        assertThat(testBrand.getChairman()).isEqualTo(UPDATED_CHAIRMAN);
        assertThat(testBrand.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testBrand.getOfficialWebsite()).isEqualTo(UPDATED_OFFICIAL_WEBSITE);
        assertThat(testBrand.getAdPhrase()).isEqualTo(UPDATED_AD_PHRASE);

        // Validate the Brand in Elasticsearch
        Brand brandEs = brandSearchRepository.findOne(testBrand.getId());
        assertThat(brandEs).isEqualToIgnoringGivenFields(testBrand);
    }

    @Test
    @Transactional
    public void updateNonExistingBrand() throws Exception {
        int databaseSizeBeforeUpdate = brandRepository.findAll().size();

        // Create the Brand

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBrandMockMvc.perform(put("/api/brands")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(brand)))
            .andExpect(status().isCreated());

        // Validate the Brand in the database
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBrand() throws Exception {
        // Initialize the database
        brandService.save(brand);

        int databaseSizeBeforeDelete = brandRepository.findAll().size();

        // Get the brand
        restBrandMockMvc.perform(delete("/api/brands/{id}", brand.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean brandExistsInEs = brandSearchRepository.exists(brand.getId());
        assertThat(brandExistsInEs).isFalse();

        // Validate the database is empty
        List<Brand> brandList = brandRepository.findAll();
        assertThat(brandList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchBrand() throws Exception {
        // Initialize the database
        brandService.save(brand);

        // Search the brand
        restBrandMockMvc.perform(get("/api/_search/brands?query=id:" + brand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(brand.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].bannerImgFile").value(hasItem(DEFAULT_BANNER_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].profileImgFile").value(hasItem(DEFAULT_PROFILE_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO.toString())))
            .andExpect(jsonPath("$.[*].establishTime").value(hasItem(DEFAULT_ESTABLISH_TIME.toString())))
            .andExpect(jsonPath("$.[*].cradle").value(hasItem(DEFAULT_CRADLE.toString())))
            .andExpect(jsonPath("$.[*].chairman").value(hasItem(DEFAULT_CHAIRMAN.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].officialWebsite").value(hasItem(DEFAULT_OFFICIAL_WEBSITE.toString())))
            .andExpect(jsonPath("$.[*].adPhrase").value(hasItem(DEFAULT_AD_PHRASE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Brand.class);
        Brand brand1 = new Brand();
        brand1.setId(1L);
        Brand brand2 = new Brand();
        brand2.setId(brand1.getId());
        assertThat(brand1).isEqualTo(brand2);
        brand2.setId(2L);
        assertThat(brand1).isNotEqualTo(brand2);
        brand1.setId(null);
        assertThat(brand1).isNotEqualTo(brand2);
    }
}
