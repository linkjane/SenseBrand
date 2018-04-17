package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.Test1;
import io.github.jhipster.application.repository.Test1Repository;
import io.github.jhipster.application.repository.search.Test1SearchRepository;
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
 * Test class for the Test1Resource REST controller.
 *
 * @see Test1Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class Test1ResourceIntTest {

    private static final String DEFAULT_TEST = "AAAAAAAAAA";
    private static final String UPDATED_TEST = "BBBBBBBBBB";

    private static final String DEFAULT_N = "AAAAAAAAAA";
    private static final String UPDATED_N = "BBBBBBBBBB";

    private static final String DEFAULT_SDF = "AAAAAAAAAA";
    private static final String UPDATED_SDF = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_FILE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_BLOB_FILE = "AAAAAAAAAA";
    private static final String UPDATED_BLOB_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_FILE = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_FILE_TEST = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_FILE_TEST = "BBBBBBBBBB";

    @Autowired
    private Test1Repository test1Repository;

    @Autowired
    private Test1SearchRepository test1SearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTest1MockMvc;

    private Test1 test1;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final Test1Resource test1Resource = new Test1Resource(test1Repository, test1SearchRepository);
        this.restTest1MockMvc = MockMvcBuilders.standaloneSetup(test1Resource)
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
    public static Test1 createEntity(EntityManager em) {
        Test1 test1 = new Test1()
            .test(DEFAULT_TEST)
            .n(DEFAULT_N)
            .sdf(DEFAULT_SDF)
            .imageFile(DEFAULT_IMAGE_FILE)
            .blobFile(DEFAULT_BLOB_FILE)
            .textFile(DEFAULT_TEXT_FILE)
            .textFileTest(DEFAULT_TEXT_FILE_TEST);
        return test1;
    }

    @Before
    public void initTest() {
        test1SearchRepository.deleteAll();
        test1 = createEntity(em);
    }

    @Test
    @Transactional
    public void createTest1() throws Exception {
        int databaseSizeBeforeCreate = test1Repository.findAll().size();

        // Create the Test1
        restTest1MockMvc.perform(post("/api/test-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(test1)))
            .andExpect(status().isCreated());

        // Validate the Test1 in the database
        List<Test1> test1List = test1Repository.findAll();
        assertThat(test1List).hasSize(databaseSizeBeforeCreate + 1);
        Test1 testTest1 = test1List.get(test1List.size() - 1);
        assertThat(testTest1.getTest()).isEqualTo(DEFAULT_TEST);
        assertThat(testTest1.getN()).isEqualTo(DEFAULT_N);
        assertThat(testTest1.getSdf()).isEqualTo(DEFAULT_SDF);
        assertThat(testTest1.getImageFile()).isEqualTo(DEFAULT_IMAGE_FILE);
        assertThat(testTest1.getBlobFile()).isEqualTo(DEFAULT_BLOB_FILE);
        assertThat(testTest1.getTextFile()).isEqualTo(DEFAULT_TEXT_FILE);
        assertThat(testTest1.getTextFileTest()).isEqualTo(DEFAULT_TEXT_FILE_TEST);

        // Validate the Test1 in Elasticsearch
        Test1 test1Es = test1SearchRepository.findOne(testTest1.getId());
        assertThat(test1Es).isEqualToIgnoringGivenFields(testTest1);
    }

    @Test
    @Transactional
    public void createTest1WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = test1Repository.findAll().size();

        // Create the Test1 with an existing ID
        test1.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTest1MockMvc.perform(post("/api/test-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(test1)))
            .andExpect(status().isBadRequest());

        // Validate the Test1 in the database
        List<Test1> test1List = test1Repository.findAll();
        assertThat(test1List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTest1S() throws Exception {
        // Initialize the database
        test1Repository.saveAndFlush(test1);

        // Get all the test1List
        restTest1MockMvc.perform(get("/api/test-1-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(test1.getId().intValue())))
            .andExpect(jsonPath("$.[*].test").value(hasItem(DEFAULT_TEST.toString())))
            .andExpect(jsonPath("$.[*].n").value(hasItem(DEFAULT_N.toString())))
            .andExpect(jsonPath("$.[*].sdf").value(hasItem(DEFAULT_SDF.toString())))
            .andExpect(jsonPath("$.[*].imageFile").value(hasItem(DEFAULT_IMAGE_FILE.toString())))
            .andExpect(jsonPath("$.[*].blobFile").value(hasItem(DEFAULT_BLOB_FILE.toString())))
            .andExpect(jsonPath("$.[*].textFile").value(hasItem(DEFAULT_TEXT_FILE.toString())))
            .andExpect(jsonPath("$.[*].textFileTest").value(hasItem(DEFAULT_TEXT_FILE_TEST.toString())));
    }

    @Test
    @Transactional
    public void getTest1() throws Exception {
        // Initialize the database
        test1Repository.saveAndFlush(test1);

        // Get the test1
        restTest1MockMvc.perform(get("/api/test-1-s/{id}", test1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(test1.getId().intValue()))
            .andExpect(jsonPath("$.test").value(DEFAULT_TEST.toString()))
            .andExpect(jsonPath("$.n").value(DEFAULT_N.toString()))
            .andExpect(jsonPath("$.sdf").value(DEFAULT_SDF.toString()))
            .andExpect(jsonPath("$.imageFile").value(DEFAULT_IMAGE_FILE.toString()))
            .andExpect(jsonPath("$.blobFile").value(DEFAULT_BLOB_FILE.toString()))
            .andExpect(jsonPath("$.textFile").value(DEFAULT_TEXT_FILE.toString()))
            .andExpect(jsonPath("$.textFileTest").value(DEFAULT_TEXT_FILE_TEST.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTest1() throws Exception {
        // Get the test1
        restTest1MockMvc.perform(get("/api/test-1-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTest1() throws Exception {
        // Initialize the database
        test1Repository.saveAndFlush(test1);
        test1SearchRepository.save(test1);
        int databaseSizeBeforeUpdate = test1Repository.findAll().size();

        // Update the test1
        Test1 updatedTest1 = test1Repository.findOne(test1.getId());
        // Disconnect from session so that the updates on updatedTest1 are not directly saved in db
        em.detach(updatedTest1);
        updatedTest1
            .test(UPDATED_TEST)
            .n(UPDATED_N)
            .sdf(UPDATED_SDF)
            .imageFile(UPDATED_IMAGE_FILE)
            .blobFile(UPDATED_BLOB_FILE)
            .textFile(UPDATED_TEXT_FILE)
            .textFileTest(UPDATED_TEXT_FILE_TEST);

        restTest1MockMvc.perform(put("/api/test-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTest1)))
            .andExpect(status().isOk());

        // Validate the Test1 in the database
        List<Test1> test1List = test1Repository.findAll();
        assertThat(test1List).hasSize(databaseSizeBeforeUpdate);
        Test1 testTest1 = test1List.get(test1List.size() - 1);
        assertThat(testTest1.getTest()).isEqualTo(UPDATED_TEST);
        assertThat(testTest1.getN()).isEqualTo(UPDATED_N);
        assertThat(testTest1.getSdf()).isEqualTo(UPDATED_SDF);
        assertThat(testTest1.getImageFile()).isEqualTo(UPDATED_IMAGE_FILE);
        assertThat(testTest1.getBlobFile()).isEqualTo(UPDATED_BLOB_FILE);
        assertThat(testTest1.getTextFile()).isEqualTo(UPDATED_TEXT_FILE);
        assertThat(testTest1.getTextFileTest()).isEqualTo(UPDATED_TEXT_FILE_TEST);

        // Validate the Test1 in Elasticsearch
        Test1 test1Es = test1SearchRepository.findOne(testTest1.getId());
        assertThat(test1Es).isEqualToIgnoringGivenFields(testTest1);
    }

    @Test
    @Transactional
    public void updateNonExistingTest1() throws Exception {
        int databaseSizeBeforeUpdate = test1Repository.findAll().size();

        // Create the Test1

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTest1MockMvc.perform(put("/api/test-1-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(test1)))
            .andExpect(status().isCreated());

        // Validate the Test1 in the database
        List<Test1> test1List = test1Repository.findAll();
        assertThat(test1List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTest1() throws Exception {
        // Initialize the database
        test1Repository.saveAndFlush(test1);
        test1SearchRepository.save(test1);
        int databaseSizeBeforeDelete = test1Repository.findAll().size();

        // Get the test1
        restTest1MockMvc.perform(delete("/api/test-1-s/{id}", test1.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean test1ExistsInEs = test1SearchRepository.exists(test1.getId());
        assertThat(test1ExistsInEs).isFalse();

        // Validate the database is empty
        List<Test1> test1List = test1Repository.findAll();
        assertThat(test1List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchTest1() throws Exception {
        // Initialize the database
        test1Repository.saveAndFlush(test1);
        test1SearchRepository.save(test1);

        // Search the test1
        restTest1MockMvc.perform(get("/api/_search/test-1-s?query=id:" + test1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(test1.getId().intValue())))
            .andExpect(jsonPath("$.[*].test").value(hasItem(DEFAULT_TEST.toString())))
            .andExpect(jsonPath("$.[*].n").value(hasItem(DEFAULT_N.toString())))
            .andExpect(jsonPath("$.[*].sdf").value(hasItem(DEFAULT_SDF.toString())))
            .andExpect(jsonPath("$.[*].imageFile").value(hasItem(DEFAULT_IMAGE_FILE.toString())))
            .andExpect(jsonPath("$.[*].blobFile").value(hasItem(DEFAULT_BLOB_FILE.toString())))
            .andExpect(jsonPath("$.[*].textFile").value(hasItem(DEFAULT_TEXT_FILE.toString())))
            .andExpect(jsonPath("$.[*].textFileTest").value(hasItem(DEFAULT_TEXT_FILE_TEST.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Test1.class);
        Test1 test11 = new Test1();
        test11.setId(1L);
        Test1 test12 = new Test1();
        test12.setId(test11.getId());
        assertThat(test11).isEqualTo(test12);
        test12.setId(2L);
        assertThat(test11).isNotEqualTo(test12);
        test11.setId(null);
        assertThat(test11).isNotEqualTo(test12);
    }
}
