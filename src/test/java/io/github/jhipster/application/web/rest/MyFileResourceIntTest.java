package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.MyFile;
import io.github.jhipster.application.repository.MyFileRepository;
import io.github.jhipster.application.repository.search.MyFileSearchRepository;
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
 * Test class for the MyFileResource REST controller.
 *
 * @see MyFileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class MyFileResourceIntTest {

    private static final String DEFAULT_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_FILENAME = "BBBBBBBBBB";

    private static final String DEFAULT_MY_FILE = "AAAAAAAAAA";
    private static final String UPDATED_MY_FILE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE_EXAMPLE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_EXAMPLE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_EXAMPLE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_EXAMPLE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TEST_EXAMPLE = "AAAAAAAAAA";
    private static final String UPDATED_TEST_EXAMPLE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_FILE = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_FILE = "BBBBBBBBBB";

    @Autowired
    private MyFileRepository myFileRepository;

    @Autowired
    private MyFileSearchRepository myFileSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMyFileMockMvc;

    private MyFile myFile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MyFileResource myFileResource = new MyFileResource(myFileRepository, myFileSearchRepository);
        this.restMyFileMockMvc = MockMvcBuilders.standaloneSetup(myFileResource)
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
    public static MyFile createEntity(EntityManager em) {
        MyFile myFile = new MyFile()
            .filename(DEFAULT_FILENAME)
            .myFile(DEFAULT_MY_FILE)
            .imageExample(DEFAULT_IMAGE_EXAMPLE)
            .imageExampleContentType(DEFAULT_IMAGE_EXAMPLE_CONTENT_TYPE)
            .testExample(DEFAULT_TEST_EXAMPLE)
            .textFile(DEFAULT_TEXT_FILE);
        return myFile;
    }

    @Before
    public void initTest() {
        myFileSearchRepository.deleteAll();
        myFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyFile() throws Exception {
        int databaseSizeBeforeCreate = myFileRepository.findAll().size();

        // Create the MyFile
        restMyFileMockMvc.perform(post("/api/my-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myFile)))
            .andExpect(status().isCreated());

        // Validate the MyFile in the database
        List<MyFile> myFileList = myFileRepository.findAll();
        assertThat(myFileList).hasSize(databaseSizeBeforeCreate + 1);
        MyFile testMyFile = myFileList.get(myFileList.size() - 1);
        assertThat(testMyFile.getFilename()).isEqualTo(DEFAULT_FILENAME);
        assertThat(testMyFile.getMyFile()).isEqualTo(DEFAULT_MY_FILE);
        assertThat(testMyFile.getImageExample()).isEqualTo(DEFAULT_IMAGE_EXAMPLE);
        assertThat(testMyFile.getImageExampleContentType()).isEqualTo(DEFAULT_IMAGE_EXAMPLE_CONTENT_TYPE);
        assertThat(testMyFile.getTestExample()).isEqualTo(DEFAULT_TEST_EXAMPLE);
        assertThat(testMyFile.getTextFile()).isEqualTo(DEFAULT_TEXT_FILE);

        // Validate the MyFile in Elasticsearch
        MyFile myFileEs = myFileSearchRepository.findOne(testMyFile.getId());
        assertThat(myFileEs).isEqualToIgnoringGivenFields(testMyFile);
    }

    @Test
    @Transactional
    public void createMyFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = myFileRepository.findAll().size();

        // Create the MyFile with an existing ID
        myFile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyFileMockMvc.perform(post("/api/my-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myFile)))
            .andExpect(status().isBadRequest());

        // Validate the MyFile in the database
        List<MyFile> myFileList = myFileRepository.findAll();
        assertThat(myFileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFilenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = myFileRepository.findAll().size();
        // set the field null
        myFile.setFilename(null);

        // Create the MyFile, which fails.

        restMyFileMockMvc.perform(post("/api/my-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myFile)))
            .andExpect(status().isBadRequest());

        List<MyFile> myFileList = myFileRepository.findAll();
        assertThat(myFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = myFileRepository.findAll().size();
        // set the field null
        myFile.setTextFile(null);

        // Create the MyFile, which fails.

        restMyFileMockMvc.perform(post("/api/my-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myFile)))
            .andExpect(status().isBadRequest());

        List<MyFile> myFileList = myFileRepository.findAll();
        assertThat(myFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMyFiles() throws Exception {
        // Initialize the database
        myFileRepository.saveAndFlush(myFile);

        // Get all the myFileList
        restMyFileMockMvc.perform(get("/api/my-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME.toString())))
            .andExpect(jsonPath("$.[*].myFile").value(hasItem(DEFAULT_MY_FILE.toString())))
            .andExpect(jsonPath("$.[*].imageExampleContentType").value(hasItem(DEFAULT_IMAGE_EXAMPLE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageExample").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_EXAMPLE))))
            .andExpect(jsonPath("$.[*].testExample").value(hasItem(DEFAULT_TEST_EXAMPLE.toString())))
            .andExpect(jsonPath("$.[*].textFile").value(hasItem(DEFAULT_TEXT_FILE.toString())));
    }

    @Test
    @Transactional
    public void getMyFile() throws Exception {
        // Initialize the database
        myFileRepository.saveAndFlush(myFile);

        // Get the myFile
        restMyFileMockMvc.perform(get("/api/my-files/{id}", myFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(myFile.getId().intValue()))
            .andExpect(jsonPath("$.filename").value(DEFAULT_FILENAME.toString()))
            .andExpect(jsonPath("$.myFile").value(DEFAULT_MY_FILE.toString()))
            .andExpect(jsonPath("$.imageExampleContentType").value(DEFAULT_IMAGE_EXAMPLE_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageExample").value(Base64Utils.encodeToString(DEFAULT_IMAGE_EXAMPLE)))
            .andExpect(jsonPath("$.testExample").value(DEFAULT_TEST_EXAMPLE.toString()))
            .andExpect(jsonPath("$.textFile").value(DEFAULT_TEXT_FILE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMyFile() throws Exception {
        // Get the myFile
        restMyFileMockMvc.perform(get("/api/my-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyFile() throws Exception {
        // Initialize the database
        myFileRepository.saveAndFlush(myFile);
        myFileSearchRepository.save(myFile);
        int databaseSizeBeforeUpdate = myFileRepository.findAll().size();

        // Update the myFile
        MyFile updatedMyFile = myFileRepository.findOne(myFile.getId());
        // Disconnect from session so that the updates on updatedMyFile are not directly saved in db
        em.detach(updatedMyFile);
        updatedMyFile
            .filename(UPDATED_FILENAME)
            .myFile(UPDATED_MY_FILE)
            .imageExample(UPDATED_IMAGE_EXAMPLE)
            .imageExampleContentType(UPDATED_IMAGE_EXAMPLE_CONTENT_TYPE)
            .testExample(UPDATED_TEST_EXAMPLE)
            .textFile(UPDATED_TEXT_FILE);

        restMyFileMockMvc.perform(put("/api/my-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMyFile)))
            .andExpect(status().isOk());

        // Validate the MyFile in the database
        List<MyFile> myFileList = myFileRepository.findAll();
        assertThat(myFileList).hasSize(databaseSizeBeforeUpdate);
        MyFile testMyFile = myFileList.get(myFileList.size() - 1);
        assertThat(testMyFile.getFilename()).isEqualTo(UPDATED_FILENAME);
        assertThat(testMyFile.getMyFile()).isEqualTo(UPDATED_MY_FILE);
        assertThat(testMyFile.getImageExample()).isEqualTo(UPDATED_IMAGE_EXAMPLE);
        assertThat(testMyFile.getImageExampleContentType()).isEqualTo(UPDATED_IMAGE_EXAMPLE_CONTENT_TYPE);
        assertThat(testMyFile.getTestExample()).isEqualTo(UPDATED_TEST_EXAMPLE);
        assertThat(testMyFile.getTextFile()).isEqualTo(UPDATED_TEXT_FILE);

        // Validate the MyFile in Elasticsearch
        MyFile myFileEs = myFileSearchRepository.findOne(testMyFile.getId());
        assertThat(myFileEs).isEqualToIgnoringGivenFields(testMyFile);
    }

    @Test
    @Transactional
    public void updateNonExistingMyFile() throws Exception {
        int databaseSizeBeforeUpdate = myFileRepository.findAll().size();

        // Create the MyFile

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMyFileMockMvc.perform(put("/api/my-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(myFile)))
            .andExpect(status().isCreated());

        // Validate the MyFile in the database
        List<MyFile> myFileList = myFileRepository.findAll();
        assertThat(myFileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMyFile() throws Exception {
        // Initialize the database
        myFileRepository.saveAndFlush(myFile);
        myFileSearchRepository.save(myFile);
        int databaseSizeBeforeDelete = myFileRepository.findAll().size();

        // Get the myFile
        restMyFileMockMvc.perform(delete("/api/my-files/{id}", myFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean myFileExistsInEs = myFileSearchRepository.exists(myFile.getId());
        assertThat(myFileExistsInEs).isFalse();

        // Validate the database is empty
        List<MyFile> myFileList = myFileRepository.findAll();
        assertThat(myFileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMyFile() throws Exception {
        // Initialize the database
        myFileRepository.saveAndFlush(myFile);
        myFileSearchRepository.save(myFile);

        // Search the myFile
        restMyFileMockMvc.perform(get("/api/_search/my-files?query=id:" + myFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME.toString())))
            .andExpect(jsonPath("$.[*].myFile").value(hasItem(DEFAULT_MY_FILE.toString())))
            .andExpect(jsonPath("$.[*].imageExampleContentType").value(hasItem(DEFAULT_IMAGE_EXAMPLE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageExample").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_EXAMPLE))))
            .andExpect(jsonPath("$.[*].testExample").value(hasItem(DEFAULT_TEST_EXAMPLE.toString())))
            .andExpect(jsonPath("$.[*].textFile").value(hasItem(DEFAULT_TEXT_FILE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MyFile.class);
        MyFile myFile1 = new MyFile();
        myFile1.setId(1L);
        MyFile myFile2 = new MyFile();
        myFile2.setId(myFile1.getId());
        assertThat(myFile1).isEqualTo(myFile2);
        myFile2.setId(2L);
        assertThat(myFile1).isNotEqualTo(myFile2);
        myFile1.setId(null);
        assertThat(myFile1).isNotEqualTo(myFile2);
    }
}
