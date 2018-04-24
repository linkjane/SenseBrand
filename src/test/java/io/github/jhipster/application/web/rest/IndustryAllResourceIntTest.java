package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.IndustryAll;
import io.github.jhipster.application.repository.IndustryAllRepository;
import io.github.jhipster.application.service.IndustryAllService;
import io.github.jhipster.application.repository.search.IndustryAllSearchRepository;
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
 * Test class for the IndustryAllResource REST controller.
 *
 * @see IndustryAllResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class IndustryAllResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private IndustryAllRepository industryAllRepository;

    @Autowired
    private IndustryAllService industryAllService;

    @Autowired
    private IndustryAllSearchRepository industryAllSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIndustryAllMockMvc;

    private IndustryAll industryAll;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IndustryAllResource industryAllResource = new IndustryAllResource(industryAllService);
        this.restIndustryAllMockMvc = MockMvcBuilders.standaloneSetup(industryAllResource)
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
    public static IndustryAll createEntity(EntityManager em) {
        IndustryAll industryAll = new IndustryAll()
            .name(DEFAULT_NAME);
        return industryAll;
    }

    @Before
    public void initTest() {
        industryAllSearchRepository.deleteAll();
        industryAll = createEntity(em);
    }

    @Test
    @Transactional
    public void createIndustryAll() throws Exception {
        int databaseSizeBeforeCreate = industryAllRepository.findAll().size();

        // Create the IndustryAll
        restIndustryAllMockMvc.perform(post("/api/industry-alls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industryAll)))
            .andExpect(status().isCreated());

        // Validate the IndustryAll in the database
        List<IndustryAll> industryAllList = industryAllRepository.findAll();
        assertThat(industryAllList).hasSize(databaseSizeBeforeCreate + 1);
        IndustryAll testIndustryAll = industryAllList.get(industryAllList.size() - 1);
        assertThat(testIndustryAll.getName()).isEqualTo(DEFAULT_NAME);

        // Validate the IndustryAll in Elasticsearch
        IndustryAll industryAllEs = industryAllSearchRepository.findOne(testIndustryAll.getId());
        assertThat(industryAllEs).isEqualToIgnoringGivenFields(testIndustryAll);
    }

    @Test
    @Transactional
    public void createIndustryAllWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = industryAllRepository.findAll().size();

        // Create the IndustryAll with an existing ID
        industryAll.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndustryAllMockMvc.perform(post("/api/industry-alls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industryAll)))
            .andExpect(status().isBadRequest());

        // Validate the IndustryAll in the database
        List<IndustryAll> industryAllList = industryAllRepository.findAll();
        assertThat(industryAllList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = industryAllRepository.findAll().size();
        // set the field null
        industryAll.setName(null);

        // Create the IndustryAll, which fails.

        restIndustryAllMockMvc.perform(post("/api/industry-alls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industryAll)))
            .andExpect(status().isBadRequest());

        List<IndustryAll> industryAllList = industryAllRepository.findAll();
        assertThat(industryAllList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIndustryAlls() throws Exception {
        // Initialize the database
        industryAllRepository.saveAndFlush(industryAll);

        // Get all the industryAllList
        restIndustryAllMockMvc.perform(get("/api/industry-alls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industryAll.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getIndustryAll() throws Exception {
        // Initialize the database
        industryAllRepository.saveAndFlush(industryAll);

        // Get the industryAll
        restIndustryAllMockMvc.perform(get("/api/industry-alls/{id}", industryAll.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(industryAll.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIndustryAll() throws Exception {
        // Get the industryAll
        restIndustryAllMockMvc.perform(get("/api/industry-alls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndustryAll() throws Exception {
        // Initialize the database
        industryAllService.save(industryAll);

        int databaseSizeBeforeUpdate = industryAllRepository.findAll().size();

        // Update the industryAll
        IndustryAll updatedIndustryAll = industryAllRepository.findOne(industryAll.getId());
        // Disconnect from session so that the updates on updatedIndustryAll are not directly saved in db
        em.detach(updatedIndustryAll);
        updatedIndustryAll
            .name(UPDATED_NAME);

        restIndustryAllMockMvc.perform(put("/api/industry-alls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIndustryAll)))
            .andExpect(status().isOk());

        // Validate the IndustryAll in the database
        List<IndustryAll> industryAllList = industryAllRepository.findAll();
        assertThat(industryAllList).hasSize(databaseSizeBeforeUpdate);
        IndustryAll testIndustryAll = industryAllList.get(industryAllList.size() - 1);
        assertThat(testIndustryAll.getName()).isEqualTo(UPDATED_NAME);

        // Validate the IndustryAll in Elasticsearch
        IndustryAll industryAllEs = industryAllSearchRepository.findOne(testIndustryAll.getId());
        assertThat(industryAllEs).isEqualToIgnoringGivenFields(testIndustryAll);
    }

    @Test
    @Transactional
    public void updateNonExistingIndustryAll() throws Exception {
        int databaseSizeBeforeUpdate = industryAllRepository.findAll().size();

        // Create the IndustryAll

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIndustryAllMockMvc.perform(put("/api/industry-alls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industryAll)))
            .andExpect(status().isCreated());

        // Validate the IndustryAll in the database
        List<IndustryAll> industryAllList = industryAllRepository.findAll();
        assertThat(industryAllList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIndustryAll() throws Exception {
        // Initialize the database
        industryAllService.save(industryAll);

        int databaseSizeBeforeDelete = industryAllRepository.findAll().size();

        // Get the industryAll
        restIndustryAllMockMvc.perform(delete("/api/industry-alls/{id}", industryAll.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean industryAllExistsInEs = industryAllSearchRepository.exists(industryAll.getId());
        assertThat(industryAllExistsInEs).isFalse();

        // Validate the database is empty
        List<IndustryAll> industryAllList = industryAllRepository.findAll();
        assertThat(industryAllList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchIndustryAll() throws Exception {
        // Initialize the database
        industryAllService.save(industryAll);

        // Search the industryAll
        restIndustryAllMockMvc.perform(get("/api/_search/industry-alls?query=id:" + industryAll.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industryAll.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndustryAll.class);
        IndustryAll industryAll1 = new IndustryAll();
        industryAll1.setId(1L);
        IndustryAll industryAll2 = new IndustryAll();
        industryAll2.setId(industryAll1.getId());
        assertThat(industryAll1).isEqualTo(industryAll2);
        industryAll2.setId(2L);
        assertThat(industryAll1).isNotEqualTo(industryAll2);
        industryAll1.setId(null);
        assertThat(industryAll1).isNotEqualTo(industryAll2);
    }
}
