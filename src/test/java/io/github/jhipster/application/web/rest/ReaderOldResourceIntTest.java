package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.ReaderOld;
import io.github.jhipster.application.repository.ReaderOldRepository;
import io.github.jhipster.application.repository.search.ReaderOldSearchRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReaderOldResource REST controller.
 *
 * @see ReaderOldResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class ReaderOldResourceIntTest {

    private static final byte[] DEFAULT_IMAGE_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_FILE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TEXT_FILE = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_FILE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_BLOB_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BLOB_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_BLOB_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BLOB_FILE_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_LOCAL_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LOCAL_TIME = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ReaderOldRepository readerOldRepository;

    @Autowired
    private ReaderOldSearchRepository readerOldSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReaderOldMockMvc;

    private ReaderOld readerOld;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReaderOldResource readerOldResource = new ReaderOldResource(readerOldRepository, readerOldSearchRepository);
        this.restReaderOldMockMvc = MockMvcBuilders.standaloneSetup(readerOldResource)
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
    public static ReaderOld createEntity(EntityManager em) {
        ReaderOld readerOld = new ReaderOld()
            .imageFile(DEFAULT_IMAGE_FILE)
            .imageFileContentType(DEFAULT_IMAGE_FILE_CONTENT_TYPE)
            .textFile(DEFAULT_TEXT_FILE)
            .blobFile(DEFAULT_BLOB_FILE)
            .blobFileContentType(DEFAULT_BLOB_FILE_CONTENT_TYPE)
            .localTime(DEFAULT_LOCAL_TIME);
        return readerOld;
    }

    @Before
    public void initTest() {
        readerOldSearchRepository.deleteAll();
        readerOld = createEntity(em);
    }

    @Test
    @Transactional
    public void createReaderOld() throws Exception {
        int databaseSizeBeforeCreate = readerOldRepository.findAll().size();

        // Create the ReaderOld
        restReaderOldMockMvc.perform(post("/api/reader-olds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(readerOld)))
            .andExpect(status().isCreated());

        // Validate the ReaderOld in the database
        List<ReaderOld> readerOldList = readerOldRepository.findAll();
        assertThat(readerOldList).hasSize(databaseSizeBeforeCreate + 1);
        ReaderOld testReaderOld = readerOldList.get(readerOldList.size() - 1);
        assertThat(testReaderOld.getImageFile()).isEqualTo(DEFAULT_IMAGE_FILE);
        assertThat(testReaderOld.getImageFileContentType()).isEqualTo(DEFAULT_IMAGE_FILE_CONTENT_TYPE);
        assertThat(testReaderOld.getTextFile()).isEqualTo(DEFAULT_TEXT_FILE);
        assertThat(testReaderOld.getBlobFile()).isEqualTo(DEFAULT_BLOB_FILE);
        assertThat(testReaderOld.getBlobFileContentType()).isEqualTo(DEFAULT_BLOB_FILE_CONTENT_TYPE);
        assertThat(testReaderOld.getLocalTime()).isEqualTo(DEFAULT_LOCAL_TIME);

        // Validate the ReaderOld in Elasticsearch
        ReaderOld readerOldEs = readerOldSearchRepository.findOne(testReaderOld.getId());
        assertThat(readerOldEs).isEqualToIgnoringGivenFields(testReaderOld);
    }

    @Test
    @Transactional
    public void createReaderOldWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = readerOldRepository.findAll().size();

        // Create the ReaderOld with an existing ID
        readerOld.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReaderOldMockMvc.perform(post("/api/reader-olds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(readerOld)))
            .andExpect(status().isBadRequest());

        // Validate the ReaderOld in the database
        List<ReaderOld> readerOldList = readerOldRepository.findAll();
        assertThat(readerOldList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBlobFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = readerOldRepository.findAll().size();
        // set the field null
        readerOld.setBlobFile(null);

        // Create the ReaderOld, which fails.

        restReaderOldMockMvc.perform(post("/api/reader-olds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(readerOld)))
            .andExpect(status().isBadRequest());

        List<ReaderOld> readerOldList = readerOldRepository.findAll();
        assertThat(readerOldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReaderOlds() throws Exception {
        // Initialize the database
        readerOldRepository.saveAndFlush(readerOld);

        // Get all the readerOldList
        restReaderOldMockMvc.perform(get("/api/reader-olds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(readerOld.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageFileContentType").value(hasItem(DEFAULT_IMAGE_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_FILE))))
            .andExpect(jsonPath("$.[*].textFile").value(hasItem(DEFAULT_TEXT_FILE.toString())))
            .andExpect(jsonPath("$.[*].blobFileContentType").value(hasItem(DEFAULT_BLOB_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].blobFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_BLOB_FILE))))
            .andExpect(jsonPath("$.[*].localTime").value(hasItem(DEFAULT_LOCAL_TIME.toString())));
    }

    @Test
    @Transactional
    public void getReaderOld() throws Exception {
        // Initialize the database
        readerOldRepository.saveAndFlush(readerOld);

        // Get the readerOld
        restReaderOldMockMvc.perform(get("/api/reader-olds/{id}", readerOld.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(readerOld.getId().intValue()))
            .andExpect(jsonPath("$.imageFileContentType").value(DEFAULT_IMAGE_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.imageFile").value(Base64Utils.encodeToString(DEFAULT_IMAGE_FILE)))
            .andExpect(jsonPath("$.textFile").value(DEFAULT_TEXT_FILE.toString()))
            .andExpect(jsonPath("$.blobFileContentType").value(DEFAULT_BLOB_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.blobFile").value(Base64Utils.encodeToString(DEFAULT_BLOB_FILE)))
            .andExpect(jsonPath("$.localTime").value(DEFAULT_LOCAL_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReaderOld() throws Exception {
        // Get the readerOld
        restReaderOldMockMvc.perform(get("/api/reader-olds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReaderOld() throws Exception {
        // Initialize the database
        readerOldRepository.saveAndFlush(readerOld);
        readerOldSearchRepository.save(readerOld);
        int databaseSizeBeforeUpdate = readerOldRepository.findAll().size();

        // Update the readerOld
        ReaderOld updatedReaderOld = readerOldRepository.findOne(readerOld.getId());
        // Disconnect from session so that the updates on updatedReaderOld are not directly saved in db
        em.detach(updatedReaderOld);
        updatedReaderOld
            .imageFile(UPDATED_IMAGE_FILE)
            .imageFileContentType(UPDATED_IMAGE_FILE_CONTENT_TYPE)
            .textFile(UPDATED_TEXT_FILE)
            .blobFile(UPDATED_BLOB_FILE)
            .blobFileContentType(UPDATED_BLOB_FILE_CONTENT_TYPE)
            .localTime(UPDATED_LOCAL_TIME);

        restReaderOldMockMvc.perform(put("/api/reader-olds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReaderOld)))
            .andExpect(status().isOk());

        // Validate the ReaderOld in the database
        List<ReaderOld> readerOldList = readerOldRepository.findAll();
        assertThat(readerOldList).hasSize(databaseSizeBeforeUpdate);
        ReaderOld testReaderOld = readerOldList.get(readerOldList.size() - 1);
        assertThat(testReaderOld.getImageFile()).isEqualTo(UPDATED_IMAGE_FILE);
        assertThat(testReaderOld.getImageFileContentType()).isEqualTo(UPDATED_IMAGE_FILE_CONTENT_TYPE);
        assertThat(testReaderOld.getTextFile()).isEqualTo(UPDATED_TEXT_FILE);
        assertThat(testReaderOld.getBlobFile()).isEqualTo(UPDATED_BLOB_FILE);
        assertThat(testReaderOld.getBlobFileContentType()).isEqualTo(UPDATED_BLOB_FILE_CONTENT_TYPE);
        assertThat(testReaderOld.getLocalTime()).isEqualTo(UPDATED_LOCAL_TIME);

        // Validate the ReaderOld in Elasticsearch
        ReaderOld readerOldEs = readerOldSearchRepository.findOne(testReaderOld.getId());
        assertThat(readerOldEs).isEqualToIgnoringGivenFields(testReaderOld);
    }

    @Test
    @Transactional
    public void updateNonExistingReaderOld() throws Exception {
        int databaseSizeBeforeUpdate = readerOldRepository.findAll().size();

        // Create the ReaderOld

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReaderOldMockMvc.perform(put("/api/reader-olds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(readerOld)))
            .andExpect(status().isCreated());

        // Validate the ReaderOld in the database
        List<ReaderOld> readerOldList = readerOldRepository.findAll();
        assertThat(readerOldList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReaderOld() throws Exception {
        // Initialize the database
        readerOldRepository.saveAndFlush(readerOld);
        readerOldSearchRepository.save(readerOld);
        int databaseSizeBeforeDelete = readerOldRepository.findAll().size();

        // Get the readerOld
        restReaderOldMockMvc.perform(delete("/api/reader-olds/{id}", readerOld.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean readerOldExistsInEs = readerOldSearchRepository.exists(readerOld.getId());
        assertThat(readerOldExistsInEs).isFalse();

        // Validate the database is empty
        List<ReaderOld> readerOldList = readerOldRepository.findAll();
        assertThat(readerOldList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchReaderOld() throws Exception {
        // Initialize the database
        readerOldRepository.saveAndFlush(readerOld);
        readerOldSearchRepository.save(readerOld);

        // Search the readerOld
        restReaderOldMockMvc.perform(get("/api/_search/reader-olds?query=id:" + readerOld.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(readerOld.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageFileContentType").value(hasItem(DEFAULT_IMAGE_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imageFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_FILE))))
            .andExpect(jsonPath("$.[*].textFile").value(hasItem(DEFAULT_TEXT_FILE.toString())))
            .andExpect(jsonPath("$.[*].blobFileContentType").value(hasItem(DEFAULT_BLOB_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].blobFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_BLOB_FILE))))
            .andExpect(jsonPath("$.[*].localTime").value(hasItem(DEFAULT_LOCAL_TIME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReaderOld.class);
        ReaderOld readerOld1 = new ReaderOld();
        readerOld1.setId(1L);
        ReaderOld readerOld2 = new ReaderOld();
        readerOld2.setId(readerOld1.getId());
        assertThat(readerOld1).isEqualTo(readerOld2);
        readerOld2.setId(2L);
        assertThat(readerOld1).isNotEqualTo(readerOld2);
        readerOld1.setId(null);
        assertThat(readerOld1).isNotEqualTo(readerOld2);
    }
}
