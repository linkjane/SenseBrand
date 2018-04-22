package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.Designer;
import io.github.jhipster.application.repository.DesignerRepository;
import io.github.jhipster.application.service.DesignerService;
import io.github.jhipster.application.repository.search.DesignerSearchRepository;
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
 * Test class for the DesignerResource REST controller.
 *
 * @see DesignerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class DesignerResourceIntTest {

    private static final String DEFAULT_ZN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ZN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROFILE_BACK_FILE = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_BACK_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_PROFILE_THUMBNAIL_FILE = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_THUMBNAIL_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_SHOW = false;
    private static final Boolean UPDATED_IS_SHOW = true;

    @Autowired
    private DesignerRepository designerRepository;

    @Autowired
    private DesignerService designerService;

    @Autowired
    private DesignerSearchRepository designerSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDesignerMockMvc;

    private Designer designer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DesignerResource designerResource = new DesignerResource(designerService);
        this.restDesignerMockMvc = MockMvcBuilders.standaloneSetup(designerResource)
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
    public static Designer createEntity(EntityManager em) {
        Designer designer = new Designer()
            .znName(DEFAULT_ZN_NAME)
            .enName(DEFAULT_EN_NAME)
            .profileBackFile(DEFAULT_PROFILE_BACK_FILE)
            .profileThumbnailFile(DEFAULT_PROFILE_THUMBNAIL_FILE)
            .position(DEFAULT_POSITION)
            .introduction(DEFAULT_INTRODUCTION)
            .isShow(DEFAULT_IS_SHOW);
        return designer;
    }

    @Before
    public void initTest() {
        designerSearchRepository.deleteAll();
        designer = createEntity(em);
    }

    @Test
    @Transactional
    public void createDesigner() throws Exception {
        int databaseSizeBeforeCreate = designerRepository.findAll().size();

        // Create the Designer
        restDesignerMockMvc.perform(post("/api/designers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designer)))
            .andExpect(status().isCreated());

        // Validate the Designer in the database
        List<Designer> designerList = designerRepository.findAll();
        assertThat(designerList).hasSize(databaseSizeBeforeCreate + 1);
        Designer testDesigner = designerList.get(designerList.size() - 1);
        assertThat(testDesigner.getZnName()).isEqualTo(DEFAULT_ZN_NAME);
        assertThat(testDesigner.getEnName()).isEqualTo(DEFAULT_EN_NAME);
        assertThat(testDesigner.getProfileBackFile()).isEqualTo(DEFAULT_PROFILE_BACK_FILE);
        assertThat(testDesigner.getProfileThumbnailFile()).isEqualTo(DEFAULT_PROFILE_THUMBNAIL_FILE);
        assertThat(testDesigner.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testDesigner.getIntroduction()).isEqualTo(DEFAULT_INTRODUCTION);
        assertThat(testDesigner.isIsShow()).isEqualTo(DEFAULT_IS_SHOW);

        // Validate the Designer in Elasticsearch
        Designer designerEs = designerSearchRepository.findOne(testDesigner.getId());
        assertThat(designerEs).isEqualToIgnoringGivenFields(testDesigner);
    }

    @Test
    @Transactional
    public void createDesignerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = designerRepository.findAll().size();

        // Create the Designer with an existing ID
        designer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignerMockMvc.perform(post("/api/designers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designer)))
            .andExpect(status().isBadRequest());

        // Validate the Designer in the database
        List<Designer> designerList = designerRepository.findAll();
        assertThat(designerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkZnNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = designerRepository.findAll().size();
        // set the field null
        designer.setZnName(null);

        // Create the Designer, which fails.

        restDesignerMockMvc.perform(post("/api/designers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designer)))
            .andExpect(status().isBadRequest());

        List<Designer> designerList = designerRepository.findAll();
        assertThat(designerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDesigners() throws Exception {
        // Initialize the database
        designerRepository.saveAndFlush(designer);

        // Get all the designerList
        restDesignerMockMvc.perform(get("/api/designers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designer.getId().intValue())))
            .andExpect(jsonPath("$.[*].znName").value(hasItem(DEFAULT_ZN_NAME.toString())))
            .andExpect(jsonPath("$.[*].enName").value(hasItem(DEFAULT_EN_NAME.toString())))
            .andExpect(jsonPath("$.[*].profileBackFile").value(hasItem(DEFAULT_PROFILE_BACK_FILE.toString())))
            .andExpect(jsonPath("$.[*].profileThumbnailFile").value(hasItem(DEFAULT_PROFILE_THUMBNAIL_FILE.toString())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].isShow").value(hasItem(DEFAULT_IS_SHOW.booleanValue())));
    }

    @Test
    @Transactional
    public void getDesigner() throws Exception {
        // Initialize the database
        designerRepository.saveAndFlush(designer);

        // Get the designer
        restDesignerMockMvc.perform(get("/api/designers/{id}", designer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(designer.getId().intValue()))
            .andExpect(jsonPath("$.znName").value(DEFAULT_ZN_NAME.toString()))
            .andExpect(jsonPath("$.enName").value(DEFAULT_EN_NAME.toString()))
            .andExpect(jsonPath("$.profileBackFile").value(DEFAULT_PROFILE_BACK_FILE.toString()))
            .andExpect(jsonPath("$.profileThumbnailFile").value(DEFAULT_PROFILE_THUMBNAIL_FILE.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()))
            .andExpect(jsonPath("$.introduction").value(DEFAULT_INTRODUCTION.toString()))
            .andExpect(jsonPath("$.isShow").value(DEFAULT_IS_SHOW.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDesigner() throws Exception {
        // Get the designer
        restDesignerMockMvc.perform(get("/api/designers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDesigner() throws Exception {
        // Initialize the database
        designerService.save(designer);

        int databaseSizeBeforeUpdate = designerRepository.findAll().size();

        // Update the designer
        Designer updatedDesigner = designerRepository.findOne(designer.getId());
        // Disconnect from session so that the updates on updatedDesigner are not directly saved in db
        em.detach(updatedDesigner);
        updatedDesigner
            .znName(UPDATED_ZN_NAME)
            .enName(UPDATED_EN_NAME)
            .profileBackFile(UPDATED_PROFILE_BACK_FILE)
            .profileThumbnailFile(UPDATED_PROFILE_THUMBNAIL_FILE)
            .position(UPDATED_POSITION)
            .introduction(UPDATED_INTRODUCTION)
            .isShow(UPDATED_IS_SHOW);

        restDesignerMockMvc.perform(put("/api/designers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDesigner)))
            .andExpect(status().isOk());

        // Validate the Designer in the database
        List<Designer> designerList = designerRepository.findAll();
        assertThat(designerList).hasSize(databaseSizeBeforeUpdate);
        Designer testDesigner = designerList.get(designerList.size() - 1);
        assertThat(testDesigner.getZnName()).isEqualTo(UPDATED_ZN_NAME);
        assertThat(testDesigner.getEnName()).isEqualTo(UPDATED_EN_NAME);
        assertThat(testDesigner.getProfileBackFile()).isEqualTo(UPDATED_PROFILE_BACK_FILE);
        assertThat(testDesigner.getProfileThumbnailFile()).isEqualTo(UPDATED_PROFILE_THUMBNAIL_FILE);
        assertThat(testDesigner.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testDesigner.getIntroduction()).isEqualTo(UPDATED_INTRODUCTION);
        assertThat(testDesigner.isIsShow()).isEqualTo(UPDATED_IS_SHOW);

        // Validate the Designer in Elasticsearch
        Designer designerEs = designerSearchRepository.findOne(testDesigner.getId());
        assertThat(designerEs).isEqualToIgnoringGivenFields(testDesigner);
    }

    @Test
    @Transactional
    public void updateNonExistingDesigner() throws Exception {
        int databaseSizeBeforeUpdate = designerRepository.findAll().size();

        // Create the Designer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDesignerMockMvc.perform(put("/api/designers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designer)))
            .andExpect(status().isCreated());

        // Validate the Designer in the database
        List<Designer> designerList = designerRepository.findAll();
        assertThat(designerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDesigner() throws Exception {
        // Initialize the database
        designerService.save(designer);

        int databaseSizeBeforeDelete = designerRepository.findAll().size();

        // Get the designer
        restDesignerMockMvc.perform(delete("/api/designers/{id}", designer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean designerExistsInEs = designerSearchRepository.exists(designer.getId());
        assertThat(designerExistsInEs).isFalse();

        // Validate the database is empty
        List<Designer> designerList = designerRepository.findAll();
        assertThat(designerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDesigner() throws Exception {
        // Initialize the database
        designerService.save(designer);

        // Search the designer
        restDesignerMockMvc.perform(get("/api/_search/designers?query=id:" + designer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designer.getId().intValue())))
            .andExpect(jsonPath("$.[*].znName").value(hasItem(DEFAULT_ZN_NAME.toString())))
            .andExpect(jsonPath("$.[*].enName").value(hasItem(DEFAULT_EN_NAME.toString())))
            .andExpect(jsonPath("$.[*].profileBackFile").value(hasItem(DEFAULT_PROFILE_BACK_FILE.toString())))
            .andExpect(jsonPath("$.[*].profileThumbnailFile").value(hasItem(DEFAULT_PROFILE_THUMBNAIL_FILE.toString())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].isShow").value(hasItem(DEFAULT_IS_SHOW.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Designer.class);
        Designer designer1 = new Designer();
        designer1.setId(1L);
        Designer designer2 = new Designer();
        designer2.setId(designer1.getId());
        assertThat(designer1).isEqualTo(designer2);
        designer2.setId(2L);
        assertThat(designer1).isNotEqualTo(designer2);
        designer1.setId(null);
        assertThat(designer1).isNotEqualTo(designer2);
    }
}
