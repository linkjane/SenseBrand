package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.IndustrySecondClass;
import io.github.jhipster.application.repository.IndustrySecondClassRepository;
import io.github.jhipster.application.service.IndustrySecondClassService;
import io.github.jhipster.application.repository.search.IndustrySecondClassSearchRepository;
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
 * Test class for the IndustrySecondClassResource REST controller.
 *
 * @see IndustrySecondClassResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class IndustrySecondClassResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private IndustrySecondClassRepository industrySecondClassRepository;

    @Autowired
    private IndustrySecondClassService industrySecondClassService;

    @Autowired
    private IndustrySecondClassSearchRepository industrySecondClassSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIndustrySecondClassMockMvc;

    private IndustrySecondClass industrySecondClass;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IndustrySecondClassResource industrySecondClassResource = new IndustrySecondClassResource(industrySecondClassService);
        this.restIndustrySecondClassMockMvc = MockMvcBuilders.standaloneSetup(industrySecondClassResource)
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
    public static IndustrySecondClass createEntity(EntityManager em) {
        IndustrySecondClass industrySecondClass = new IndustrySecondClass()
            .name(DEFAULT_NAME);
        return industrySecondClass;
    }

    @Before
    public void initTest() {
        industrySecondClassSearchRepository.deleteAll();
        industrySecondClass = createEntity(em);
    }

    @Test
    @Transactional
    public void createIndustrySecondClass() throws Exception {
        int databaseSizeBeforeCreate = industrySecondClassRepository.findAll().size();

        // Create the IndustrySecondClass
        restIndustrySecondClassMockMvc.perform(post("/api/industry-second-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industrySecondClass)))
            .andExpect(status().isCreated());

        // Validate the IndustrySecondClass in the database
        List<IndustrySecondClass> industrySecondClassList = industrySecondClassRepository.findAll();
        assertThat(industrySecondClassList).hasSize(databaseSizeBeforeCreate + 1);
        IndustrySecondClass testIndustrySecondClass = industrySecondClassList.get(industrySecondClassList.size() - 1);
        assertThat(testIndustrySecondClass.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the IndustrySecondClass in Elasticsearch
        IndustrySecondClass industrySecondClassEs = industrySecondClassSearchRepository.findOne(testIndustrySecondClass.getId());
        assertThat(industrySecondClassEs).isEqualToIgnoringGivenFields(testIndustrySecondClass);
    }

    @Test
    @Transactional
    public void createIndustrySecondClassWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = industrySecondClassRepository.findAll().size();

        // Create the IndustrySecondClass with an existing ID
        industrySecondClass.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndustrySecondClassMockMvc.perform(post("/api/industry-second-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industrySecondClass)))
            .andExpect(status().isBadRequest());

        // Validate the IndustrySecondClass in the database
        List<IndustrySecondClass> industrySecondClassList = industrySecondClassRepository.findAll();
        assertThat(industrySecondClassList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = industrySecondClassRepository.findAll().size();
        // set the field null
        industrySecondClass.setName(null);

        // Create the IndustrySecondClass, which fails.

        restIndustrySecondClassMockMvc.perform(post("/api/industry-second-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industrySecondClass)))
            .andExpect(status().isBadRequest());

        List<IndustrySecondClass> industrySecondClassList = industrySecondClassRepository.findAll();
        assertThat(industrySecondClassList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIndustrySecondClasses() throws Exception {
        // Initialize the database
        industrySecondClassRepository.saveAndFlush(industrySecondClass);

        // Get all the industrySecondClassList
        restIndustrySecondClassMockMvc.perform(get("/api/industry-second-classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industrySecondClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getIndustrySecondClass() throws Exception {
        // Initialize the database
        industrySecondClassRepository.saveAndFlush(industrySecondClass);

        // Get the industrySecondClass
        restIndustrySecondClassMockMvc.perform(get("/api/industry-second-classes/{id}", industrySecondClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(industrySecondClass.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIndustrySecondClass() throws Exception {
        // Get the industrySecondClass
        restIndustrySecondClassMockMvc.perform(get("/api/industry-second-classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndustrySecondClass() throws Exception {
        // Initialize the database
        industrySecondClassService.save(industrySecondClass);

        int databaseSizeBeforeUpdate = industrySecondClassRepository.findAll().size();

        // Update the industrySecondClass
        IndustrySecondClass updatedIndustrySecondClass = industrySecondClassRepository.findOne(industrySecondClass.getId());
        // Disconnect from session so that the updates on updatedIndustrySecondClass are not directly saved in db
        em.detach(updatedIndustrySecondClass);
        updatedIndustrySecondClass
            .name(UPDATED_NAME);

        restIndustrySecondClassMockMvc.perform(put("/api/industry-second-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIndustrySecondClass)))
            .andExpect(status().isOk());

        // Validate the IndustrySecondClass in the database
        List<IndustrySecondClass> industrySecondClassList = industrySecondClassRepository.findAll();
        assertThat(industrySecondClassList).hasSize(databaseSizeBeforeUpdate);
        IndustrySecondClass testIndustrySecondClass = industrySecondClassList.get(industrySecondClassList.size() - 1);
        assertThat(testIndustrySecondClass.getName()).isEqualTo(UPDATED_NAME);

        // Validate the IndustrySecondClass in Elasticsearch
        IndustrySecondClass industrySecondClassEs = industrySecondClassSearchRepository.findOne(testIndustrySecondClass.getId());
        assertThat(industrySecondClassEs).isEqualToIgnoringGivenFields(testIndustrySecondClass);
    }

    @Test
    @Transactional
    public void updateNonExistingIndustrySecondClass() throws Exception {
        int databaseSizeBeforeUpdate = industrySecondClassRepository.findAll().size();

        // Create the IndustrySecondClass

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIndustrySecondClassMockMvc.perform(put("/api/industry-second-classes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industrySecondClass)))
            .andExpect(status().isCreated());

        // Validate the IndustrySecondClass in the database
        List<IndustrySecondClass> industrySecondClassList = industrySecondClassRepository.findAll();
        assertThat(industrySecondClassList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIndustrySecondClass() throws Exception {
        // Initialize the database
        industrySecondClassService.save(industrySecondClass);

        int databaseSizeBeforeDelete = industrySecondClassRepository.findAll().size();

        // Get the industrySecondClass
        restIndustrySecondClassMockMvc.perform(delete("/api/industry-second-classes/{id}", industrySecondClass.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean industrySecondClassExistsInEs = industrySecondClassSearchRepository.exists(industrySecondClass.getId());
        assertThat(industrySecondClassExistsInEs).isFalse();

        // Validate the database is empty
        List<IndustrySecondClass> industrySecondClassList = industrySecondClassRepository.findAll();
        assertThat(industrySecondClassList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchIndustrySecondClass() throws Exception {
        // Initialize the database
        industrySecondClassService.save(industrySecondClass);

        // Search the industrySecondClass
        restIndustrySecondClassMockMvc.perform(get("/api/_search/industry-second-classes?query=id:" + industrySecondClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industrySecondClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndustrySecondClass.class);
        IndustrySecondClass industrySecondClass1 = new IndustrySecondClass();
        industrySecondClass1.setId(1L);
        IndustrySecondClass industrySecondClass2 = new IndustrySecondClass();
        industrySecondClass2.setId(industrySecondClass1.getId());
        assertThat(industrySecondClass1).isEqualTo(industrySecondClass2);
        industrySecondClass2.setId(2L);
        assertThat(industrySecondClass1).isNotEqualTo(industrySecondClass2);
        industrySecondClass1.setId(null);
        assertThat(industrySecondClass1).isNotEqualTo(industrySecondClass2);
    }
}
