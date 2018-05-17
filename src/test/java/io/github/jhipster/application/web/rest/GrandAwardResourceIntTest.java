package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.GrandAward;
import io.github.jhipster.application.repository.GrandAwardRepository;
import io.github.jhipster.application.service.GrandAwardService;
import io.github.jhipster.application.repository.search.GrandAwardSearchRepository;
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
 * Test class for the GrandAwardResource REST controller.
 *
 * @see GrandAwardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class GrandAwardResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_FILE = "AAAAAAAAAA";
    private static final String UPDATED_IMG_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCTION = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL_LINK = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL_LINK = "BBBBBBBBBB";

    @Autowired
    private GrandAwardRepository grandAwardRepository;

    @Autowired
    private GrandAwardService grandAwardService;

    @Autowired
    private GrandAwardSearchRepository grandAwardSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGrandAwardMockMvc;

    private GrandAward grandAward;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GrandAwardResource grandAwardResource = new GrandAwardResource(grandAwardService);
        this.restGrandAwardMockMvc = MockMvcBuilders.standaloneSetup(grandAwardResource)
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
    public static GrandAward createEntity(EntityManager em) {
        GrandAward grandAward = new GrandAward()
            .title(DEFAULT_TITLE)
            .imgFile(DEFAULT_IMG_FILE)
            .introduction(DEFAULT_INTRODUCTION)
            .detailLink(DEFAULT_DETAIL_LINK);
        return grandAward;
    }

    @Before
    public void initTest() {
        grandAwardSearchRepository.deleteAll();
        grandAward = createEntity(em);
    }

    @Test
    @Transactional
    public void createGrandAward() throws Exception {
        int databaseSizeBeforeCreate = grandAwardRepository.findAll().size();

        // Create the GrandAward
        restGrandAwardMockMvc.perform(post("/api/grand-awards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grandAward)))
            .andExpect(status().isCreated());

        // Validate the GrandAward in the database
        List<GrandAward> grandAwardList = grandAwardRepository.findAll();
        assertThat(grandAwardList).hasSize(databaseSizeBeforeCreate + 1);
        GrandAward testGrandAward = grandAwardList.get(grandAwardList.size() - 1);
        assertThat(testGrandAward.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testGrandAward.getImgFile()).isEqualTo(DEFAULT_IMG_FILE);
        assertThat(testGrandAward.getIntroduction()).isEqualTo(DEFAULT_INTRODUCTION);
        assertThat(testGrandAward.getDetailLink()).isEqualTo(DEFAULT_DETAIL_LINK);

        // Validate the GrandAward in Elasticsearch
        GrandAward grandAwardEs = grandAwardSearchRepository.findOne(testGrandAward.getId());
        assertThat(grandAwardEs).isEqualToIgnoringGivenFields(testGrandAward);
    }

    @Test
    @Transactional
    public void createGrandAwardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = grandAwardRepository.findAll().size();

        // Create the GrandAward with an existing ID
        grandAward.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGrandAwardMockMvc.perform(post("/api/grand-awards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grandAward)))
            .andExpect(status().isBadRequest());

        // Validate the GrandAward in the database
        List<GrandAward> grandAwardList = grandAwardRepository.findAll();
        assertThat(grandAwardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGrandAwards() throws Exception {
        // Initialize the database
        grandAwardRepository.saveAndFlush(grandAward);

        // Get all the grandAwardList
        restGrandAwardMockMvc.perform(get("/api/grand-awards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grandAward.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].imgFile").value(hasItem(DEFAULT_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].detailLink").value(hasItem(DEFAULT_DETAIL_LINK.toString())));
    }

    @Test
    @Transactional
    public void getGrandAward() throws Exception {
        // Initialize the database
        grandAwardRepository.saveAndFlush(grandAward);

        // Get the grandAward
        restGrandAwardMockMvc.perform(get("/api/grand-awards/{id}", grandAward.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(grandAward.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.imgFile").value(DEFAULT_IMG_FILE.toString()))
            .andExpect(jsonPath("$.introduction").value(DEFAULT_INTRODUCTION.toString()))
            .andExpect(jsonPath("$.detailLink").value(DEFAULT_DETAIL_LINK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGrandAward() throws Exception {
        // Get the grandAward
        restGrandAwardMockMvc.perform(get("/api/grand-awards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGrandAward() throws Exception {
        // Initialize the database
        grandAwardService.save(grandAward);

        int databaseSizeBeforeUpdate = grandAwardRepository.findAll().size();

        // Update the grandAward
        GrandAward updatedGrandAward = grandAwardRepository.findOne(grandAward.getId());
        // Disconnect from session so that the updates on updatedGrandAward are not directly saved in db
        em.detach(updatedGrandAward);
        updatedGrandAward
            .title(UPDATED_TITLE)
            .imgFile(UPDATED_IMG_FILE)
            .introduction(UPDATED_INTRODUCTION)
            .detailLink(UPDATED_DETAIL_LINK);

        restGrandAwardMockMvc.perform(put("/api/grand-awards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGrandAward)))
            .andExpect(status().isOk());

        // Validate the GrandAward in the database
        List<GrandAward> grandAwardList = grandAwardRepository.findAll();
        assertThat(grandAwardList).hasSize(databaseSizeBeforeUpdate);
        GrandAward testGrandAward = grandAwardList.get(grandAwardList.size() - 1);
        assertThat(testGrandAward.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testGrandAward.getImgFile()).isEqualTo(UPDATED_IMG_FILE);
        assertThat(testGrandAward.getIntroduction()).isEqualTo(UPDATED_INTRODUCTION);
        assertThat(testGrandAward.getDetailLink()).isEqualTo(UPDATED_DETAIL_LINK);

        // Validate the GrandAward in Elasticsearch
        GrandAward grandAwardEs = grandAwardSearchRepository.findOne(testGrandAward.getId());
        assertThat(grandAwardEs).isEqualToIgnoringGivenFields(testGrandAward);
    }

    @Test
    @Transactional
    public void updateNonExistingGrandAward() throws Exception {
        int databaseSizeBeforeUpdate = grandAwardRepository.findAll().size();

        // Create the GrandAward

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGrandAwardMockMvc.perform(put("/api/grand-awards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(grandAward)))
            .andExpect(status().isCreated());

        // Validate the GrandAward in the database
        List<GrandAward> grandAwardList = grandAwardRepository.findAll();
        assertThat(grandAwardList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGrandAward() throws Exception {
        // Initialize the database
        grandAwardService.save(grandAward);

        int databaseSizeBeforeDelete = grandAwardRepository.findAll().size();

        // Get the grandAward
        restGrandAwardMockMvc.perform(delete("/api/grand-awards/{id}", grandAward.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean grandAwardExistsInEs = grandAwardSearchRepository.exists(grandAward.getId());
        assertThat(grandAwardExistsInEs).isFalse();

        // Validate the database is empty
        List<GrandAward> grandAwardList = grandAwardRepository.findAll();
        assertThat(grandAwardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGrandAward() throws Exception {
        // Initialize the database
        grandAwardService.save(grandAward);

        // Search the grandAward
        restGrandAwardMockMvc.perform(get("/api/_search/grand-awards?query=id:" + grandAward.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grandAward.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].imgFile").value(hasItem(DEFAULT_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].detailLink").value(hasItem(DEFAULT_DETAIL_LINK.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrandAward.class);
        GrandAward grandAward1 = new GrandAward();
        grandAward1.setId(1L);
        GrandAward grandAward2 = new GrandAward();
        grandAward2.setId(grandAward1.getId());
        assertThat(grandAward1).isEqualTo(grandAward2);
        grandAward2.setId(2L);
        assertThat(grandAward1).isNotEqualTo(grandAward2);
        grandAward1.setId(null);
        assertThat(grandAward1).isNotEqualTo(grandAward2);
    }
}
