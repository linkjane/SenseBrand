package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.Designer;
import io.github.jhipster.application.repository.DesignerRepository;
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

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENGLISH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENGLISH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    @Autowired
    private DesignerRepository designerRepository;

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
        final DesignerResource designerResource = new DesignerResource(designerRepository, designerSearchRepository);
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
            .name(DEFAULT_NAME)
            .englishName(DEFAULT_ENGLISH_NAME)
            .position(DEFAULT_POSITION);
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
        assertThat(testDesigner.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDesigner.getEnglishName()).isEqualTo(DEFAULT_ENGLISH_NAME);
        assertThat(testDesigner.getPosition()).isEqualTo(DEFAULT_POSITION);

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
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = designerRepository.findAll().size();
        // set the field null
        designer.setName(null);

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
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].englishName").value(hasItem(DEFAULT_ENGLISH_NAME.toString())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())));
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
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.englishName").value(DEFAULT_ENGLISH_NAME.toString()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()));
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
        designerRepository.saveAndFlush(designer);
        designerSearchRepository.save(designer);
        int databaseSizeBeforeUpdate = designerRepository.findAll().size();

        // Update the designer
        Designer updatedDesigner = designerRepository.findOne(designer.getId());
        // Disconnect from session so that the updates on updatedDesigner are not directly saved in db
        em.detach(updatedDesigner);
        updatedDesigner
            .name(UPDATED_NAME)
            .englishName(UPDATED_ENGLISH_NAME)
            .position(UPDATED_POSITION);

        restDesignerMockMvc.perform(put("/api/designers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDesigner)))
            .andExpect(status().isOk());

        // Validate the Designer in the database
        List<Designer> designerList = designerRepository.findAll();
        assertThat(designerList).hasSize(databaseSizeBeforeUpdate);
        Designer testDesigner = designerList.get(designerList.size() - 1);
        assertThat(testDesigner.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDesigner.getEnglishName()).isEqualTo(UPDATED_ENGLISH_NAME);
        assertThat(testDesigner.getPosition()).isEqualTo(UPDATED_POSITION);

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
        designerRepository.saveAndFlush(designer);
        designerSearchRepository.save(designer);
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
        designerRepository.saveAndFlush(designer);
        designerSearchRepository.save(designer);

        // Search the designer
        restDesignerMockMvc.perform(get("/api/_search/designers?query=id:" + designer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].englishName").value(hasItem(DEFAULT_ENGLISH_NAME.toString())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())));
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
