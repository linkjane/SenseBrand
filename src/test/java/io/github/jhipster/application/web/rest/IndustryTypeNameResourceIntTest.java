package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.IndustryTypeName;
import io.github.jhipster.application.repository.IndustryTypeNameRepository;
import io.github.jhipster.application.service.IndustryTypeNameService;
import io.github.jhipster.application.repository.search.IndustryTypeNameSearchRepository;
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
 * Test class for the IndustryTypeNameResource REST controller.
 *
 * @see IndustryTypeNameResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class IndustryTypeNameResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCTION = "BBBBBBBBBB";

    private static final String DEFAULT_ICON_FILE = "AAAAAAAAAA";
    private static final String UPDATED_ICON_FILE = "BBBBBBBBBB";

    @Autowired
    private IndustryTypeNameRepository industryTypeNameRepository;

    @Autowired
    private IndustryTypeNameService industryTypeNameService;

    @Autowired
    private IndustryTypeNameSearchRepository industryTypeNameSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIndustryTypeNameMockMvc;

    private IndustryTypeName industryTypeName;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IndustryTypeNameResource industryTypeNameResource = new IndustryTypeNameResource(industryTypeNameService);
        this.restIndustryTypeNameMockMvc = MockMvcBuilders.standaloneSetup(industryTypeNameResource)
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
    public static IndustryTypeName createEntity(EntityManager em) {
        IndustryTypeName industryTypeName = new IndustryTypeName()
            .name(DEFAULT_NAME)
            .introduction(DEFAULT_INTRODUCTION)
            .iconFile(DEFAULT_ICON_FILE);
        return industryTypeName;
    }

    @Before
    public void initTest() {
        industryTypeNameSearchRepository.deleteAll();
        industryTypeName = createEntity(em);
    }

    @Test
    @Transactional
    public void createIndustryTypeName() throws Exception {
        int databaseSizeBeforeCreate = industryTypeNameRepository.findAll().size();

        // Create the IndustryTypeName
        restIndustryTypeNameMockMvc.perform(post("/api/industry-type-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industryTypeName)))
            .andExpect(status().isCreated());

        // Validate the IndustryTypeName in the database
        List<IndustryTypeName> industryTypeNameList = industryTypeNameRepository.findAll();
        assertThat(industryTypeNameList).hasSize(databaseSizeBeforeCreate + 1);
        IndustryTypeName testIndustryTypeName = industryTypeNameList.get(industryTypeNameList.size() - 1);
        assertThat(testIndustryTypeName.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIndustryTypeName.getIntroduction()).isEqualTo(DEFAULT_INTRODUCTION);
        assertThat(testIndustryTypeName.getIconFile()).isEqualTo(DEFAULT_ICON_FILE);

        // Validate the IndustryTypeName in Elasticsearch
        IndustryTypeName industryTypeNameEs = industryTypeNameSearchRepository.findOne(testIndustryTypeName.getId());
        assertThat(industryTypeNameEs).isEqualToIgnoringGivenFields(testIndustryTypeName);
    }

    @Test
    @Transactional
    public void createIndustryTypeNameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = industryTypeNameRepository.findAll().size();

        // Create the IndustryTypeName with an existing ID
        industryTypeName.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndustryTypeNameMockMvc.perform(post("/api/industry-type-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industryTypeName)))
            .andExpect(status().isBadRequest());

        // Validate the IndustryTypeName in the database
        List<IndustryTypeName> industryTypeNameList = industryTypeNameRepository.findAll();
        assertThat(industryTypeNameList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = industryTypeNameRepository.findAll().size();
        // set the field null
        industryTypeName.setName(null);

        // Create the IndustryTypeName, which fails.

        restIndustryTypeNameMockMvc.perform(post("/api/industry-type-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industryTypeName)))
            .andExpect(status().isBadRequest());

        List<IndustryTypeName> industryTypeNameList = industryTypeNameRepository.findAll();
        assertThat(industryTypeNameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIndustryTypeNames() throws Exception {
        // Initialize the database
        industryTypeNameRepository.saveAndFlush(industryTypeName);

        // Get all the industryTypeNameList
        restIndustryTypeNameMockMvc.perform(get("/api/industry-type-names?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industryTypeName.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].iconFile").value(hasItem(DEFAULT_ICON_FILE.toString())));
    }

    @Test
    @Transactional
    public void getIndustryTypeName() throws Exception {
        // Initialize the database
        industryTypeNameRepository.saveAndFlush(industryTypeName);

        // Get the industryTypeName
        restIndustryTypeNameMockMvc.perform(get("/api/industry-type-names/{id}", industryTypeName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(industryTypeName.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.introduction").value(DEFAULT_INTRODUCTION.toString()))
            .andExpect(jsonPath("$.iconFile").value(DEFAULT_ICON_FILE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIndustryTypeName() throws Exception {
        // Get the industryTypeName
        restIndustryTypeNameMockMvc.perform(get("/api/industry-type-names/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndustryTypeName() throws Exception {
        // Initialize the database
        industryTypeNameService.save(industryTypeName);

        int databaseSizeBeforeUpdate = industryTypeNameRepository.findAll().size();

        // Update the industryTypeName
        IndustryTypeName updatedIndustryTypeName = industryTypeNameRepository.findOne(industryTypeName.getId());
        // Disconnect from session so that the updates on updatedIndustryTypeName are not directly saved in db
        em.detach(updatedIndustryTypeName);
        updatedIndustryTypeName
            .name(UPDATED_NAME)
            .introduction(UPDATED_INTRODUCTION)
            .iconFile(UPDATED_ICON_FILE);

        restIndustryTypeNameMockMvc.perform(put("/api/industry-type-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIndustryTypeName)))
            .andExpect(status().isOk());

        // Validate the IndustryTypeName in the database
        List<IndustryTypeName> industryTypeNameList = industryTypeNameRepository.findAll();
        assertThat(industryTypeNameList).hasSize(databaseSizeBeforeUpdate);
        IndustryTypeName testIndustryTypeName = industryTypeNameList.get(industryTypeNameList.size() - 1);
        assertThat(testIndustryTypeName.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndustryTypeName.getIntroduction()).isEqualTo(UPDATED_INTRODUCTION);
        assertThat(testIndustryTypeName.getIconFile()).isEqualTo(UPDATED_ICON_FILE);

        // Validate the IndustryTypeName in Elasticsearch
        IndustryTypeName industryTypeNameEs = industryTypeNameSearchRepository.findOne(testIndustryTypeName.getId());
        assertThat(industryTypeNameEs).isEqualToIgnoringGivenFields(testIndustryTypeName);
    }

    @Test
    @Transactional
    public void updateNonExistingIndustryTypeName() throws Exception {
        int databaseSizeBeforeUpdate = industryTypeNameRepository.findAll().size();

        // Create the IndustryTypeName

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIndustryTypeNameMockMvc.perform(put("/api/industry-type-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industryTypeName)))
            .andExpect(status().isCreated());

        // Validate the IndustryTypeName in the database
        List<IndustryTypeName> industryTypeNameList = industryTypeNameRepository.findAll();
        assertThat(industryTypeNameList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIndustryTypeName() throws Exception {
        // Initialize the database
        industryTypeNameService.save(industryTypeName);

        int databaseSizeBeforeDelete = industryTypeNameRepository.findAll().size();

        // Get the industryTypeName
        restIndustryTypeNameMockMvc.perform(delete("/api/industry-type-names/{id}", industryTypeName.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean industryTypeNameExistsInEs = industryTypeNameSearchRepository.exists(industryTypeName.getId());
        assertThat(industryTypeNameExistsInEs).isFalse();

        // Validate the database is empty
        List<IndustryTypeName> industryTypeNameList = industryTypeNameRepository.findAll();
        assertThat(industryTypeNameList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchIndustryTypeName() throws Exception {
        // Initialize the database
        industryTypeNameService.save(industryTypeName);

        // Search the industryTypeName
        restIndustryTypeNameMockMvc.perform(get("/api/_search/industry-type-names?query=id:" + industryTypeName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industryTypeName.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].iconFile").value(hasItem(DEFAULT_ICON_FILE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndustryTypeName.class);
        IndustryTypeName industryTypeName1 = new IndustryTypeName();
        industryTypeName1.setId(1L);
        IndustryTypeName industryTypeName2 = new IndustryTypeName();
        industryTypeName2.setId(industryTypeName1.getId());
        assertThat(industryTypeName1).isEqualTo(industryTypeName2);
        industryTypeName2.setId(2L);
        assertThat(industryTypeName1).isNotEqualTo(industryTypeName2);
        industryTypeName1.setId(null);
        assertThat(industryTypeName1).isNotEqualTo(industryTypeName2);
    }
}
