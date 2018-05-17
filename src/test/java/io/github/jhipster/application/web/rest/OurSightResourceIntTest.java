package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.OurSight;
import io.github.jhipster.application.repository.OurSightRepository;
import io.github.jhipster.application.service.OurSightService;
import io.github.jhipster.application.repository.search.OurSightSearchRepository;
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
 * Test class for the OurSightResource REST controller.
 *
 * @see OurSightResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class OurSightResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_KEY_WORD = "AAAAAAAAAA";
    private static final String UPDATED_KEY_WORD = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_FILE = "AAAAAAAAAA";
    private static final String UPDATED_IMG_FILE = "BBBBBBBBBB";

    @Autowired
    private OurSightRepository ourSightRepository;

    @Autowired
    private OurSightService ourSightService;

    @Autowired
    private OurSightSearchRepository ourSightSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOurSightMockMvc;

    private OurSight ourSight;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OurSightResource ourSightResource = new OurSightResource(ourSightService);
        this.restOurSightMockMvc = MockMvcBuilders.standaloneSetup(ourSightResource)
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
    public static OurSight createEntity(EntityManager em) {
        OurSight ourSight = new OurSight()
            .title(DEFAULT_TITLE)
            .keyWord(DEFAULT_KEY_WORD)
            .imgFile(DEFAULT_IMG_FILE);
        return ourSight;
    }

    @Before
    public void initTest() {
        ourSightSearchRepository.deleteAll();
        ourSight = createEntity(em);
    }

    @Test
    @Transactional
    public void createOurSight() throws Exception {
        int databaseSizeBeforeCreate = ourSightRepository.findAll().size();

        // Create the OurSight
        restOurSightMockMvc.perform(post("/api/our-sights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ourSight)))
            .andExpect(status().isCreated());

        // Validate the OurSight in the database
        List<OurSight> ourSightList = ourSightRepository.findAll();
        assertThat(ourSightList).hasSize(databaseSizeBeforeCreate + 1);
        OurSight testOurSight = ourSightList.get(ourSightList.size() - 1);
        assertThat(testOurSight.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testOurSight.getKeyWord()).isEqualTo(DEFAULT_KEY_WORD);
        assertThat(testOurSight.getImgFile()).isEqualTo(DEFAULT_IMG_FILE);

        // Validate the OurSight in Elasticsearch
        OurSight ourSightEs = ourSightSearchRepository.findOne(testOurSight.getId());
        assertThat(ourSightEs).isEqualToIgnoringGivenFields(testOurSight);
    }

    @Test
    @Transactional
    public void createOurSightWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ourSightRepository.findAll().size();

        // Create the OurSight with an existing ID
        ourSight.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOurSightMockMvc.perform(post("/api/our-sights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ourSight)))
            .andExpect(status().isBadRequest());

        // Validate the OurSight in the database
        List<OurSight> ourSightList = ourSightRepository.findAll();
        assertThat(ourSightList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOurSights() throws Exception {
        // Initialize the database
        ourSightRepository.saveAndFlush(ourSight);

        // Get all the ourSightList
        restOurSightMockMvc.perform(get("/api/our-sights?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ourSight.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].keyWord").value(hasItem(DEFAULT_KEY_WORD.toString())))
            .andExpect(jsonPath("$.[*].imgFile").value(hasItem(DEFAULT_IMG_FILE.toString())));
    }

    @Test
    @Transactional
    public void getOurSight() throws Exception {
        // Initialize the database
        ourSightRepository.saveAndFlush(ourSight);

        // Get the ourSight
        restOurSightMockMvc.perform(get("/api/our-sights/{id}", ourSight.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ourSight.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.keyWord").value(DEFAULT_KEY_WORD.toString()))
            .andExpect(jsonPath("$.imgFile").value(DEFAULT_IMG_FILE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOurSight() throws Exception {
        // Get the ourSight
        restOurSightMockMvc.perform(get("/api/our-sights/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOurSight() throws Exception {
        // Initialize the database
        ourSightService.save(ourSight);

        int databaseSizeBeforeUpdate = ourSightRepository.findAll().size();

        // Update the ourSight
        OurSight updatedOurSight = ourSightRepository.findOne(ourSight.getId());
        // Disconnect from session so that the updates on updatedOurSight are not directly saved in db
        em.detach(updatedOurSight);
        updatedOurSight
            .title(UPDATED_TITLE)
            .keyWord(UPDATED_KEY_WORD)
            .imgFile(UPDATED_IMG_FILE);

        restOurSightMockMvc.perform(put("/api/our-sights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOurSight)))
            .andExpect(status().isOk());

        // Validate the OurSight in the database
        List<OurSight> ourSightList = ourSightRepository.findAll();
        assertThat(ourSightList).hasSize(databaseSizeBeforeUpdate);
        OurSight testOurSight = ourSightList.get(ourSightList.size() - 1);
        assertThat(testOurSight.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testOurSight.getKeyWord()).isEqualTo(UPDATED_KEY_WORD);
        assertThat(testOurSight.getImgFile()).isEqualTo(UPDATED_IMG_FILE);

        // Validate the OurSight in Elasticsearch
        OurSight ourSightEs = ourSightSearchRepository.findOne(testOurSight.getId());
        assertThat(ourSightEs).isEqualToIgnoringGivenFields(testOurSight);
    }

    @Test
    @Transactional
    public void updateNonExistingOurSight() throws Exception {
        int databaseSizeBeforeUpdate = ourSightRepository.findAll().size();

        // Create the OurSight

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOurSightMockMvc.perform(put("/api/our-sights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ourSight)))
            .andExpect(status().isCreated());

        // Validate the OurSight in the database
        List<OurSight> ourSightList = ourSightRepository.findAll();
        assertThat(ourSightList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOurSight() throws Exception {
        // Initialize the database
        ourSightService.save(ourSight);

        int databaseSizeBeforeDelete = ourSightRepository.findAll().size();

        // Get the ourSight
        restOurSightMockMvc.perform(delete("/api/our-sights/{id}", ourSight.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean ourSightExistsInEs = ourSightSearchRepository.exists(ourSight.getId());
        assertThat(ourSightExistsInEs).isFalse();

        // Validate the database is empty
        List<OurSight> ourSightList = ourSightRepository.findAll();
        assertThat(ourSightList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOurSight() throws Exception {
        // Initialize the database
        ourSightService.save(ourSight);

        // Search the ourSight
        restOurSightMockMvc.perform(get("/api/_search/our-sights?query=id:" + ourSight.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ourSight.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].keyWord").value(hasItem(DEFAULT_KEY_WORD.toString())))
            .andExpect(jsonPath("$.[*].imgFile").value(hasItem(DEFAULT_IMG_FILE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OurSight.class);
        OurSight ourSight1 = new OurSight();
        ourSight1.setId(1L);
        OurSight ourSight2 = new OurSight();
        ourSight2.setId(ourSight1.getId());
        assertThat(ourSight1).isEqualTo(ourSight2);
        ourSight2.setId(2L);
        assertThat(ourSight1).isNotEqualTo(ourSight2);
        ourSight1.setId(null);
        assertThat(ourSight1).isNotEqualTo(ourSight2);
    }
}
