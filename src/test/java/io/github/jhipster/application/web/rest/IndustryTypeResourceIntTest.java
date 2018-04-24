package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.IndustryType;
import io.github.jhipster.application.repository.IndustryTypeRepository;
import io.github.jhipster.application.service.IndustryTypeService;
import io.github.jhipster.application.repository.search.IndustryTypeSearchRepository;
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
 * Test class for the IndustryTypeResource REST controller.
 *
 * @see IndustryTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class IndustryTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SMALL_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SMALL_TITLE = "BBBBBBBBBB";

    @Autowired
    private IndustryTypeRepository industryTypeRepository;

    @Autowired
    private IndustryTypeService industryTypeService;

    @Autowired
    private IndustryTypeSearchRepository industryTypeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIndustryTypeMockMvc;

    private IndustryType industryType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IndustryTypeResource industryTypeResource = new IndustryTypeResource(industryTypeService);
        this.restIndustryTypeMockMvc = MockMvcBuilders.standaloneSetup(industryTypeResource)
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
    public static IndustryType createEntity(EntityManager em) {
        IndustryType industryType = new IndustryType()
            .name(DEFAULT_NAME)
            .smallTitle(DEFAULT_SMALL_TITLE);
        return industryType;
    }

    @Before
    public void initTest() {
        industryTypeSearchRepository.deleteAll();
        industryType = createEntity(em);
    }

    @Test
    @Transactional
    public void createIndustryType() throws Exception {
        int databaseSizeBeforeCreate = industryTypeRepository.findAll().size();

        // Create the IndustryType
        restIndustryTypeMockMvc.perform(post("/api/industry-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industryType)))
            .andExpect(status().isCreated());

        // Validate the IndustryType in the database
        List<IndustryType> industryTypeList = industryTypeRepository.findAll();
        assertThat(industryTypeList).hasSize(databaseSizeBeforeCreate + 1);
        IndustryType testIndustryType = industryTypeList.get(industryTypeList.size() - 1);
        assertThat(testIndustryType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIndustryType.getSmallTitle()).isEqualTo(DEFAULT_SMALL_TITLE);

        // Validate the IndustryType in Elasticsearch
        IndustryType industryTypeEs = industryTypeSearchRepository.findOne(testIndustryType.getId());
        assertThat(industryTypeEs).isEqualToIgnoringGivenFields(testIndustryType);
    }

    @Test
    @Transactional
    public void createIndustryTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = industryTypeRepository.findAll().size();

        // Create the IndustryType with an existing ID
        industryType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndustryTypeMockMvc.perform(post("/api/industry-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industryType)))
            .andExpect(status().isBadRequest());

        // Validate the IndustryType in the database
        List<IndustryType> industryTypeList = industryTypeRepository.findAll();
        assertThat(industryTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = industryTypeRepository.findAll().size();
        // set the field null
        industryType.setName(null);

        // Create the IndustryType, which fails.

        restIndustryTypeMockMvc.perform(post("/api/industry-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industryType)))
            .andExpect(status().isBadRequest());

        List<IndustryType> industryTypeList = industryTypeRepository.findAll();
        assertThat(industryTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIndustryTypes() throws Exception {
        // Initialize the database
        industryTypeRepository.saveAndFlush(industryType);

        // Get all the industryTypeList
        restIndustryTypeMockMvc.perform(get("/api/industry-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industryType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].smallTitle").value(hasItem(DEFAULT_SMALL_TITLE.toString())));
    }

    @Test
    @Transactional
    public void getIndustryType() throws Exception {
        // Initialize the database
        industryTypeRepository.saveAndFlush(industryType);

        // Get the industryType
        restIndustryTypeMockMvc.perform(get("/api/industry-types/{id}", industryType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(industryType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.smallTitle").value(DEFAULT_SMALL_TITLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIndustryType() throws Exception {
        // Get the industryType
        restIndustryTypeMockMvc.perform(get("/api/industry-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndustryType() throws Exception {
        // Initialize the database
        industryTypeService.save(industryType);

        int databaseSizeBeforeUpdate = industryTypeRepository.findAll().size();

        // Update the industryType
        IndustryType updatedIndustryType = industryTypeRepository.findOne(industryType.getId());
        // Disconnect from session so that the updates on updatedIndustryType are not directly saved in db
        em.detach(updatedIndustryType);
        updatedIndustryType
            .name(UPDATED_NAME)
            .smallTitle(UPDATED_SMALL_TITLE);

        restIndustryTypeMockMvc.perform(put("/api/industry-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIndustryType)))
            .andExpect(status().isOk());

        // Validate the IndustryType in the database
        List<IndustryType> industryTypeList = industryTypeRepository.findAll();
        assertThat(industryTypeList).hasSize(databaseSizeBeforeUpdate);
        IndustryType testIndustryType = industryTypeList.get(industryTypeList.size() - 1);
        assertThat(testIndustryType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndustryType.getSmallTitle()).isEqualTo(UPDATED_SMALL_TITLE);

        // Validate the IndustryType in Elasticsearch
        IndustryType industryTypeEs = industryTypeSearchRepository.findOne(testIndustryType.getId());
        assertThat(industryTypeEs).isEqualToIgnoringGivenFields(testIndustryType);
    }

    @Test
    @Transactional
    public void updateNonExistingIndustryType() throws Exception {
        int databaseSizeBeforeUpdate = industryTypeRepository.findAll().size();

        // Create the IndustryType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIndustryTypeMockMvc.perform(put("/api/industry-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industryType)))
            .andExpect(status().isCreated());

        // Validate the IndustryType in the database
        List<IndustryType> industryTypeList = industryTypeRepository.findAll();
        assertThat(industryTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIndustryType() throws Exception {
        // Initialize the database
        industryTypeService.save(industryType);

        int databaseSizeBeforeDelete = industryTypeRepository.findAll().size();

        // Get the industryType
        restIndustryTypeMockMvc.perform(delete("/api/industry-types/{id}", industryType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean industryTypeExistsInEs = industryTypeSearchRepository.exists(industryType.getId());
        assertThat(industryTypeExistsInEs).isFalse();

        // Validate the database is empty
        List<IndustryType> industryTypeList = industryTypeRepository.findAll();
        assertThat(industryTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchIndustryType() throws Exception {
        // Initialize the database
        industryTypeService.save(industryType);

        // Search the industryType
        restIndustryTypeMockMvc.perform(get("/api/_search/industry-types?query=id:" + industryType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industryType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].smallTitle").value(hasItem(DEFAULT_SMALL_TITLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndustryType.class);
        IndustryType industryType1 = new IndustryType();
        industryType1.setId(1L);
        IndustryType industryType2 = new IndustryType();
        industryType2.setId(industryType1.getId());
        assertThat(industryType1).isEqualTo(industryType2);
        industryType2.setId(2L);
        assertThat(industryType1).isNotEqualTo(industryType2);
        industryType1.setId(null);
        assertThat(industryType1).isNotEqualTo(industryType2);
    }
}
