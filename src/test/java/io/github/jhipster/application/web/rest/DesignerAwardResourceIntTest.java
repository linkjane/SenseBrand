package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.DesignerAward;
import io.github.jhipster.application.repository.DesignerAwardRepository;
import io.github.jhipster.application.service.DesignerAwardService;
import io.github.jhipster.application.repository.search.DesignerAwardSearchRepository;
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
 * Test class for the DesignerAwardResource REST controller.
 *
 * @see DesignerAwardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class DesignerAwardResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCTION = "BBBBBBBBBB";

    private static final String DEFAULT_IMG_FILE = "AAAAAAAAAA";
    private static final String UPDATED_IMG_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL_LINK = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL_LINK = "BBBBBBBBBB";

    @Autowired
    private DesignerAwardRepository designerAwardRepository;

    @Autowired
    private DesignerAwardService designerAwardService;

    @Autowired
    private DesignerAwardSearchRepository designerAwardSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDesignerAwardMockMvc;

    private DesignerAward designerAward;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DesignerAwardResource designerAwardResource = new DesignerAwardResource(designerAwardService);
        this.restDesignerAwardMockMvc = MockMvcBuilders.standaloneSetup(designerAwardResource)
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
    public static DesignerAward createEntity(EntityManager em) {
        DesignerAward designerAward = new DesignerAward()
            .title(DEFAULT_TITLE)
            .introduction(DEFAULT_INTRODUCTION)
            .imgFile(DEFAULT_IMG_FILE)
            .detailLink(DEFAULT_DETAIL_LINK);
        return designerAward;
    }

    @Before
    public void initTest() {
        designerAwardSearchRepository.deleteAll();
        designerAward = createEntity(em);
    }

    @Test
    @Transactional
    public void createDesignerAward() throws Exception {
        int databaseSizeBeforeCreate = designerAwardRepository.findAll().size();

        // Create the DesignerAward
        restDesignerAwardMockMvc.perform(post("/api/designer-awards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerAward)))
            .andExpect(status().isCreated());

        // Validate the DesignerAward in the database
        List<DesignerAward> designerAwardList = designerAwardRepository.findAll();
        assertThat(designerAwardList).hasSize(databaseSizeBeforeCreate + 1);
        DesignerAward testDesignerAward = designerAwardList.get(designerAwardList.size() - 1);
        assertThat(testDesignerAward.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDesignerAward.getIntroduction()).isEqualTo(DEFAULT_INTRODUCTION);
        assertThat(testDesignerAward.getImgFile()).isEqualTo(DEFAULT_IMG_FILE);
        assertThat(testDesignerAward.getDetailLink()).isEqualTo(DEFAULT_DETAIL_LINK);

        // Validate the DesignerAward in Elasticsearch
        DesignerAward designerAwardEs = designerAwardSearchRepository.findOne(testDesignerAward.getId());
        assertThat(designerAwardEs).isEqualToIgnoringGivenFields(testDesignerAward);
    }

    @Test
    @Transactional
    public void createDesignerAwardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = designerAwardRepository.findAll().size();

        // Create the DesignerAward with an existing ID
        designerAward.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignerAwardMockMvc.perform(post("/api/designer-awards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerAward)))
            .andExpect(status().isBadRequest());

        // Validate the DesignerAward in the database
        List<DesignerAward> designerAwardList = designerAwardRepository.findAll();
        assertThat(designerAwardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = designerAwardRepository.findAll().size();
        // set the field null
        designerAward.setTitle(null);

        // Create the DesignerAward, which fails.

        restDesignerAwardMockMvc.perform(post("/api/designer-awards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerAward)))
            .andExpect(status().isBadRequest());

        List<DesignerAward> designerAwardList = designerAwardRepository.findAll();
        assertThat(designerAwardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkImgFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = designerAwardRepository.findAll().size();
        // set the field null
        designerAward.setImgFile(null);

        // Create the DesignerAward, which fails.

        restDesignerAwardMockMvc.perform(post("/api/designer-awards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerAward)))
            .andExpect(status().isBadRequest());

        List<DesignerAward> designerAwardList = designerAwardRepository.findAll();
        assertThat(designerAwardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDesignerAwards() throws Exception {
        // Initialize the database
        designerAwardRepository.saveAndFlush(designerAward);

        // Get all the designerAwardList
        restDesignerAwardMockMvc.perform(get("/api/designer-awards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designerAward.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].imgFile").value(hasItem(DEFAULT_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].detailLink").value(hasItem(DEFAULT_DETAIL_LINK.toString())));
    }

    @Test
    @Transactional
    public void getDesignerAward() throws Exception {
        // Initialize the database
        designerAwardRepository.saveAndFlush(designerAward);

        // Get the designerAward
        restDesignerAwardMockMvc.perform(get("/api/designer-awards/{id}", designerAward.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(designerAward.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.introduction").value(DEFAULT_INTRODUCTION.toString()))
            .andExpect(jsonPath("$.imgFile").value(DEFAULT_IMG_FILE.toString()))
            .andExpect(jsonPath("$.detailLink").value(DEFAULT_DETAIL_LINK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDesignerAward() throws Exception {
        // Get the designerAward
        restDesignerAwardMockMvc.perform(get("/api/designer-awards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDesignerAward() throws Exception {
        // Initialize the database
        designerAwardService.save(designerAward);

        int databaseSizeBeforeUpdate = designerAwardRepository.findAll().size();

        // Update the designerAward
        DesignerAward updatedDesignerAward = designerAwardRepository.findOne(designerAward.getId());
        // Disconnect from session so that the updates on updatedDesignerAward are not directly saved in db
        em.detach(updatedDesignerAward);
        updatedDesignerAward
            .title(UPDATED_TITLE)
            .introduction(UPDATED_INTRODUCTION)
            .imgFile(UPDATED_IMG_FILE)
            .detailLink(UPDATED_DETAIL_LINK);

        restDesignerAwardMockMvc.perform(put("/api/designer-awards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDesignerAward)))
            .andExpect(status().isOk());

        // Validate the DesignerAward in the database
        List<DesignerAward> designerAwardList = designerAwardRepository.findAll();
        assertThat(designerAwardList).hasSize(databaseSizeBeforeUpdate);
        DesignerAward testDesignerAward = designerAwardList.get(designerAwardList.size() - 1);
        assertThat(testDesignerAward.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDesignerAward.getIntroduction()).isEqualTo(UPDATED_INTRODUCTION);
        assertThat(testDesignerAward.getImgFile()).isEqualTo(UPDATED_IMG_FILE);
        assertThat(testDesignerAward.getDetailLink()).isEqualTo(UPDATED_DETAIL_LINK);

        // Validate the DesignerAward in Elasticsearch
        DesignerAward designerAwardEs = designerAwardSearchRepository.findOne(testDesignerAward.getId());
        assertThat(designerAwardEs).isEqualToIgnoringGivenFields(testDesignerAward);
    }

    @Test
    @Transactional
    public void updateNonExistingDesignerAward() throws Exception {
        int databaseSizeBeforeUpdate = designerAwardRepository.findAll().size();

        // Create the DesignerAward

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDesignerAwardMockMvc.perform(put("/api/designer-awards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerAward)))
            .andExpect(status().isCreated());

        // Validate the DesignerAward in the database
        List<DesignerAward> designerAwardList = designerAwardRepository.findAll();
        assertThat(designerAwardList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDesignerAward() throws Exception {
        // Initialize the database
        designerAwardService.save(designerAward);

        int databaseSizeBeforeDelete = designerAwardRepository.findAll().size();

        // Get the designerAward
        restDesignerAwardMockMvc.perform(delete("/api/designer-awards/{id}", designerAward.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean designerAwardExistsInEs = designerAwardSearchRepository.exists(designerAward.getId());
        assertThat(designerAwardExistsInEs).isFalse();

        // Validate the database is empty
        List<DesignerAward> designerAwardList = designerAwardRepository.findAll();
        assertThat(designerAwardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDesignerAward() throws Exception {
        // Initialize the database
        designerAwardService.save(designerAward);

        // Search the designerAward
        restDesignerAwardMockMvc.perform(get("/api/_search/designer-awards?query=id:" + designerAward.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designerAward.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].imgFile").value(hasItem(DEFAULT_IMG_FILE.toString())))
            .andExpect(jsonPath("$.[*].detailLink").value(hasItem(DEFAULT_DETAIL_LINK.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesignerAward.class);
        DesignerAward designerAward1 = new DesignerAward();
        designerAward1.setId(1L);
        DesignerAward designerAward2 = new DesignerAward();
        designerAward2.setId(designerAward1.getId());
        assertThat(designerAward1).isEqualTo(designerAward2);
        designerAward2.setId(2L);
        assertThat(designerAward1).isNotEqualTo(designerAward2);
        designerAward1.setId(null);
        assertThat(designerAward1).isNotEqualTo(designerAward2);
    }
}
