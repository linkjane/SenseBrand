package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.IndustryTop;
import io.github.jhipster.application.repository.IndustryTopRepository;
import io.github.jhipster.application.service.IndustryTopService;
import io.github.jhipster.application.repository.search.IndustryTopSearchRepository;
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
 * Test class for the IndustryTopResource REST controller.
 *
 * @see IndustryTopResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class IndustryTopResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GO_LINK = "AAAAAAAAAA";
    private static final String UPDATED_GO_LINK = "BBBBBBBBBB";

    @Autowired
    private IndustryTopRepository industryTopRepository;

    @Autowired
    private IndustryTopService industryTopService;

    @Autowired
    private IndustryTopSearchRepository industryTopSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIndustryTopMockMvc;

    private IndustryTop industryTop;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IndustryTopResource industryTopResource = new IndustryTopResource(industryTopService);
        this.restIndustryTopMockMvc = MockMvcBuilders.standaloneSetup(industryTopResource)
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
    public static IndustryTop createEntity(EntityManager em) {
        IndustryTop industryTop = new IndustryTop()
            .name(DEFAULT_NAME)
            .goLink(DEFAULT_GO_LINK);
        return industryTop;
    }

    @Before
    public void initTest() {
        industryTopSearchRepository.deleteAll();
        industryTop = createEntity(em);
    }

    @Test
    @Transactional
    public void createIndustryTop() throws Exception {
        int databaseSizeBeforeCreate = industryTopRepository.findAll().size();

        // Create the IndustryTop
        restIndustryTopMockMvc.perform(post("/api/industry-tops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industryTop)))
            .andExpect(status().isCreated());

        // Validate the IndustryTop in the database
        List<IndustryTop> industryTopList = industryTopRepository.findAll();
        assertThat(industryTopList).hasSize(databaseSizeBeforeCreate + 1);
        IndustryTop testIndustryTop = industryTopList.get(industryTopList.size() - 1);
        assertThat(testIndustryTop.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testIndustryTop.getGoLink()).isEqualTo(DEFAULT_GO_LINK);

        // Validate the IndustryTop in Elasticsearch
        IndustryTop industryTopEs = industryTopSearchRepository.findOne(testIndustryTop.getId());
        assertThat(industryTopEs).isEqualToIgnoringGivenFields(testIndustryTop);
    }

    @Test
    @Transactional
    public void createIndustryTopWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = industryTopRepository.findAll().size();

        // Create the IndustryTop with an existing ID
        industryTop.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIndustryTopMockMvc.perform(post("/api/industry-tops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industryTop)))
            .andExpect(status().isBadRequest());

        // Validate the IndustryTop in the database
        List<IndustryTop> industryTopList = industryTopRepository.findAll();
        assertThat(industryTopList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = industryTopRepository.findAll().size();
        // set the field null
        industryTop.setName(null);

        // Create the IndustryTop, which fails.

        restIndustryTopMockMvc.perform(post("/api/industry-tops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industryTop)))
            .andExpect(status().isBadRequest());

        List<IndustryTop> industryTopList = industryTopRepository.findAll();
        assertThat(industryTopList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIndustryTops() throws Exception {
        // Initialize the database
        industryTopRepository.saveAndFlush(industryTop);

        // Get all the industryTopList
        restIndustryTopMockMvc.perform(get("/api/industry-tops?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industryTop.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].goLink").value(hasItem(DEFAULT_GO_LINK.toString())));
    }

    @Test
    @Transactional
    public void getIndustryTop() throws Exception {
        // Initialize the database
        industryTopRepository.saveAndFlush(industryTop);

        // Get the industryTop
        restIndustryTopMockMvc.perform(get("/api/industry-tops/{id}", industryTop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(industryTop.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.goLink").value(DEFAULT_GO_LINK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingIndustryTop() throws Exception {
        // Get the industryTop
        restIndustryTopMockMvc.perform(get("/api/industry-tops/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIndustryTop() throws Exception {
        // Initialize the database
        industryTopService.save(industryTop);

        int databaseSizeBeforeUpdate = industryTopRepository.findAll().size();

        // Update the industryTop
        IndustryTop updatedIndustryTop = industryTopRepository.findOne(industryTop.getId());
        // Disconnect from session so that the updates on updatedIndustryTop are not directly saved in db
        em.detach(updatedIndustryTop);
        updatedIndustryTop
            .name(UPDATED_NAME)
            .goLink(UPDATED_GO_LINK);

        restIndustryTopMockMvc.perform(put("/api/industry-tops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIndustryTop)))
            .andExpect(status().isOk());

        // Validate the IndustryTop in the database
        List<IndustryTop> industryTopList = industryTopRepository.findAll();
        assertThat(industryTopList).hasSize(databaseSizeBeforeUpdate);
        IndustryTop testIndustryTop = industryTopList.get(industryTopList.size() - 1);
        assertThat(testIndustryTop.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testIndustryTop.getGoLink()).isEqualTo(UPDATED_GO_LINK);

        // Validate the IndustryTop in Elasticsearch
        IndustryTop industryTopEs = industryTopSearchRepository.findOne(testIndustryTop.getId());
        assertThat(industryTopEs).isEqualToIgnoringGivenFields(testIndustryTop);
    }

    @Test
    @Transactional
    public void updateNonExistingIndustryTop() throws Exception {
        int databaseSizeBeforeUpdate = industryTopRepository.findAll().size();

        // Create the IndustryTop

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIndustryTopMockMvc.perform(put("/api/industry-tops")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(industryTop)))
            .andExpect(status().isCreated());

        // Validate the IndustryTop in the database
        List<IndustryTop> industryTopList = industryTopRepository.findAll();
        assertThat(industryTopList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIndustryTop() throws Exception {
        // Initialize the database
        industryTopService.save(industryTop);

        int databaseSizeBeforeDelete = industryTopRepository.findAll().size();

        // Get the industryTop
        restIndustryTopMockMvc.perform(delete("/api/industry-tops/{id}", industryTop.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean industryTopExistsInEs = industryTopSearchRepository.exists(industryTop.getId());
        assertThat(industryTopExistsInEs).isFalse();

        // Validate the database is empty
        List<IndustryTop> industryTopList = industryTopRepository.findAll();
        assertThat(industryTopList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchIndustryTop() throws Exception {
        // Initialize the database
        industryTopService.save(industryTop);

        // Search the industryTop
        restIndustryTopMockMvc.perform(get("/api/_search/industry-tops?query=id:" + industryTop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(industryTop.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].goLink").value(hasItem(DEFAULT_GO_LINK.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IndustryTop.class);
        IndustryTop industryTop1 = new IndustryTop();
        industryTop1.setId(1L);
        IndustryTop industryTop2 = new IndustryTop();
        industryTop2.setId(industryTop1.getId());
        assertThat(industryTop1).isEqualTo(industryTop2);
        industryTop2.setId(2L);
        assertThat(industryTop1).isNotEqualTo(industryTop2);
        industryTop1.setId(null);
        assertThat(industryTop1).isNotEqualTo(industryTop2);
    }
}
