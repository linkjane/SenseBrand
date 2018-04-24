package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.IndustryFirstClass;
import io.github.jhipster.application.domain.IndustryAll;
import io.github.jhipster.application.repository.IndustryFirstClassRepository;
import io.github.jhipster.application.service.IndustryFirstClassService;
import io.github.jhipster.application.repository.search.IndustryFirstClassSearchRepository;
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
 * Test class for the IndustryFirstClassResource REST controller.
 *
 * @see IndustryFirstClassResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class IndustryFirstClassResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private IndustryFirstClassRepository industryFirstClassRepository;

    @Autowired
    private IndustryFirstClassService industryFirstClassService;

    @Autowired
    private IndustryFirstClassSearchRepository industryFirstClassSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIndustryFirstClassMockMvc;

    private IndustryFirstClass industryFirstClass;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IndustryFirstClassResource industryFirstClassResource = new IndustryFirstClassResource(industryFirstClassService);
        this.restIndustryFirstClassMockMvc = MockMvcBuilders.standaloneSetup(industryFirstClassResource)
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
    public static IndustryFirstClass createEntity(EntityManager em) {
        IndustryFirstClass industryFirstClass = new IndustryFirstClass()
            .name(DEFAULT_NAME);
        // Add required entity
        IndustryAll industryAll = IndustryAllResourceIntTest.createEntity(em);
        em.persist(industryAll);
        em.flush();
        industryFirstClass.setIndustryAll(industryAll);
        return industryFirstClass;
    }

    @Before
    public void initTest() {
        industryFirstClassSearchRepository.deleteAll();
        industryFirstClass = createEntity(em);
    }

    @Test
    @Transactional
    public void createIndustryFirstClass() throws Exception {
        int databaseSizeBeforeCreate = industryFirstClassRepository.findAll().size();

        // Create the IndustryFirstClass
        restIndustryFirstClassMockMvc.perform(post("/api/industry-first-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industryFirstClass)))
            .andExpect(status().isCreated());

        // Validate the IndustryFirstClass in the database
        List<IndustryFirstClass> industryFirstClassList = industryFirstClassRepository.findAll();
        assertThat(industryFirstClassList).hasSize(databaseSizeBeforeCreate + 1);
        IndustryFirstClass testIndustryFirstClass = industryFirstClassList.get(industryFirstClassList.size() - 1);
        assertThat(testIndustryFirstClass.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the IndustryFirstClass in Elasticsearch
        IndustryFirstClass industryFirstClassEs = industryFirstClassSearchRepository.findOne(testIndustryFirstClass.getId());
        assertThat(industryFirstClassEs).isEqualToIgnoringGivenFields(testIndustryFirstClass);
    }

    @Test
    @Transactional
    public void createIndustryFirstClassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = industryFirstClassRepository.findAll().size();

        // Create the IndustryFirstClass with an existing ID
        industryFirstClass.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndustryFirstClassMockMvc.perform(post("/api/industry-first-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industryFirstClass)))
            .andExpect(status().isBadRequest());

        // Validate the IndustryFirstClass in the database
        List<IndustryFirstClass> industryFirstClassList = industryFirstClassRepository.findAll();
        assertThat(industryFirstClassList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = industryFirstClassRepository.findAll().size();
        // set the field null
        industryFirstClass.setName(null);

        // Create the IndustryFirstClass, which fails.

        restIndustryFirstClassMockMvc.perform(post("/api/industry-first-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industryFirstClass)))
            .andExpect(status().isBadRequest());

        List<IndustryFirstClass> industryFirstClassList = industryFirstClassRepository.findAll();
        assertThat(industryFirstClassList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIndustryFirstClasses() throws Exception {
        // Initialize the database
        industryFirstClassRepository.saveAndFlush(industryFirstClass);

        // Get all the industryFirstClassList
        restIndustryFirstClassMockMvc.perform(get("/api/industry-first-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industryFirstClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getIndustryFirstClass() throws Exception {
        // Initialize the database
        industryFirstClassRepository.saveAndFlush(industryFirstClass);

        // Get the industryFirstClass
        restIndustryFirstClassMockMvc.perform(get("/api/industry-first-classes/{id}", industryFirstClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(industryFirstClass.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIndustryFirstClass() throws Exception {
        // Get the industryFirstClass
        restIndustryFirstClassMockMvc.perform(get("/api/industry-first-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndustryFirstClass() throws Exception {
        // Initialize the database
        industryFirstClassService.save(industryFirstClass);

        int databaseSizeBeforeUpdate = industryFirstClassRepository.findAll().size();

        // Update the industryFirstClass
        IndustryFirstClass updatedIndustryFirstClass = industryFirstClassRepository.findOne(industryFirstClass.getId());
        // Disconnect from session so that the updates on updatedIndustryFirstClass are not directly saved in db
        em.detach(updatedIndustryFirstClass);
        updatedIndustryFirstClass
            .name(UPDATED_NAME);

        restIndustryFirstClassMockMvc.perform(put("/api/industry-first-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIndustryFirstClass)))
            .andExpect(status().isOk());

        // Validate the IndustryFirstClass in the database
        List<IndustryFirstClass> industryFirstClassList = industryFirstClassRepository.findAll();
        assertThat(industryFirstClassList).hasSize(databaseSizeBeforeUpdate);
        IndustryFirstClass testIndustryFirstClass = industryFirstClassList.get(industryFirstClassList.size() - 1);
        assertThat(testIndustryFirstClass.getName()).isEqualTo(UPDATED_NAME);

        // Validate the IndustryFirstClass in Elasticsearch
        IndustryFirstClass industryFirstClassEs = industryFirstClassSearchRepository.findOne(testIndustryFirstClass.getId());
        assertThat(industryFirstClassEs).isEqualToIgnoringGivenFields(testIndustryFirstClass);
    }

    @Test
    @Transactional
    public void updateNonExistingIndustryFirstClass() throws Exception {
        int databaseSizeBeforeUpdate = industryFirstClassRepository.findAll().size();

        // Create the IndustryFirstClass

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIndustryFirstClassMockMvc.perform(put("/api/industry-first-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industryFirstClass)))
            .andExpect(status().isCreated());

        // Validate the IndustryFirstClass in the database
        List<IndustryFirstClass> industryFirstClassList = industryFirstClassRepository.findAll();
        assertThat(industryFirstClassList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIndustryFirstClass() throws Exception {
        // Initialize the database
        industryFirstClassService.save(industryFirstClass);

        int databaseSizeBeforeDelete = industryFirstClassRepository.findAll().size();

        // Get the industryFirstClass
        restIndustryFirstClassMockMvc.perform(delete("/api/industry-first-classes/{id}", industryFirstClass.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean industryFirstClassExistsInEs = industryFirstClassSearchRepository.exists(industryFirstClass.getId());
        assertThat(industryFirstClassExistsInEs).isFalse();

        // Validate the database is empty
        List<IndustryFirstClass> industryFirstClassList = industryFirstClassRepository.findAll();
        assertThat(industryFirstClassList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchIndustryFirstClass() throws Exception {
        // Initialize the database
        industryFirstClassService.save(industryFirstClass);

        // Search the industryFirstClass
        restIndustryFirstClassMockMvc.perform(get("/api/_search/industry-first-classes?query=id:" + industryFirstClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industryFirstClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndustryFirstClass.class);
        IndustryFirstClass industryFirstClass1 = new IndustryFirstClass();
        industryFirstClass1.setId(1L);
        IndustryFirstClass industryFirstClass2 = new IndustryFirstClass();
        industryFirstClass2.setId(industryFirstClass1.getId());
        assertThat(industryFirstClass1).isEqualTo(industryFirstClass2);
        industryFirstClass2.setId(2L);
        assertThat(industryFirstClass1).isNotEqualTo(industryFirstClass2);
        industryFirstClass1.setId(null);
        assertThat(industryFirstClass1).isNotEqualTo(industryFirstClass2);
    }
}
