package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.Reader;
import io.github.jhipster.application.repository.ReaderRepository;
import io.github.jhipster.application.repository.search.ReaderSearchRepository;
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
 * Test class for the ReaderResource REST controller.
 *
 * @see ReaderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class ReaderResourceIntTest {

    private static final String DEFAULT_IMAGE_FILE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_FILE = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_BLOB_FILE = "AAAAAAAAAA";
    private static final String UPDATED_BLOB_FILE = "BBBBBBBBBB";

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private ReaderSearchRepository readerSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReaderMockMvc;

    private Reader reader;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReaderResource readerResource = new ReaderResource(readerRepository, readerSearchRepository);
        this.restReaderMockMvc = MockMvcBuilders.standaloneSetup(readerResource)
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
    public static Reader createEntity(EntityManager em) {
        Reader reader = new Reader()
            .imageFile(DEFAULT_IMAGE_FILE)
            .textFile(DEFAULT_TEXT_FILE)
            .blobFile(DEFAULT_BLOB_FILE);
        return reader;
    }

    @Before
    public void initTest() {
        readerSearchRepository.deleteAll();
        reader = createEntity(em);
    }

    @Test
    @Transactional
    public void createReader() throws Exception {
        int databaseSizeBeforeCreate = readerRepository.findAll().size();

        // Create the Reader
        restReaderMockMvc.perform(post("/api/readers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reader)))
            .andExpect(status().isCreated());

        // Validate the Reader in the database
        List<Reader> readerList = readerRepository.findAll();
        assertThat(readerList).hasSize(databaseSizeBeforeCreate + 1);
        Reader testReader = readerList.get(readerList.size() - 1);
        assertThat(testReader.getImageFile()).isEqualTo(DEFAULT_IMAGE_FILE);
        assertThat(testReader.getTextFile()).isEqualTo(DEFAULT_TEXT_FILE);
        assertThat(testReader.getBlobFile()).isEqualTo(DEFAULT_BLOB_FILE);

        // Validate the Reader in Elasticsearch
        Reader readerEs = readerSearchRepository.findOne(testReader.getId());
        assertThat(readerEs).isEqualToIgnoringGivenFields(testReader);
    }

    @Test
    @Transactional
    public void createReaderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = readerRepository.findAll().size();

        // Create the Reader with an existing ID
        reader.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReaderMockMvc.perform(post("/api/readers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reader)))
            .andExpect(status().isBadRequest());

        // Validate the Reader in the database
        List<Reader> readerList = readerRepository.findAll();
        assertThat(readerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReaders() throws Exception {
        // Initialize the database
        readerRepository.saveAndFlush(reader);

        // Get all the readerList
        restReaderMockMvc.perform(get("/api/readers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reader.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageFile").value(hasItem(DEFAULT_IMAGE_FILE.toString())))
            .andExpect(jsonPath("$.[*].textFile").value(hasItem(DEFAULT_TEXT_FILE.toString())))
            .andExpect(jsonPath("$.[*].blobFile").value(hasItem(DEFAULT_BLOB_FILE.toString())));
    }

    @Test
    @Transactional
    public void getReader() throws Exception {
        // Initialize the database
        readerRepository.saveAndFlush(reader);

        // Get the reader
        restReaderMockMvc.perform(get("/api/readers/{id}", reader.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reader.getId().intValue()))
            .andExpect(jsonPath("$.imageFile").value(DEFAULT_IMAGE_FILE.toString()))
            .andExpect(jsonPath("$.textFile").value(DEFAULT_TEXT_FILE.toString()))
            .andExpect(jsonPath("$.blobFile").value(DEFAULT_BLOB_FILE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReader() throws Exception {
        // Get the reader
        restReaderMockMvc.perform(get("/api/readers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReader() throws Exception {
        // Initialize the database
        readerRepository.saveAndFlush(reader);
        readerSearchRepository.save(reader);
        int databaseSizeBeforeUpdate = readerRepository.findAll().size();

        // Update the reader
        Reader updatedReader = readerRepository.findOne(reader.getId());
        // Disconnect from session so that the updates on updatedReader are not directly saved in db
        em.detach(updatedReader);
        updatedReader
            .imageFile(UPDATED_IMAGE_FILE)
            .textFile(UPDATED_TEXT_FILE)
            .blobFile(UPDATED_BLOB_FILE);

        restReaderMockMvc.perform(put("/api/readers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReader)))
            .andExpect(status().isOk());

        // Validate the Reader in the database
        List<Reader> readerList = readerRepository.findAll();
        assertThat(readerList).hasSize(databaseSizeBeforeUpdate);
        Reader testReader = readerList.get(readerList.size() - 1);
        assertThat(testReader.getImageFile()).isEqualTo(UPDATED_IMAGE_FILE);
        assertThat(testReader.getTextFile()).isEqualTo(UPDATED_TEXT_FILE);
        assertThat(testReader.getBlobFile()).isEqualTo(UPDATED_BLOB_FILE);

        // Validate the Reader in Elasticsearch
        Reader readerEs = readerSearchRepository.findOne(testReader.getId());
        assertThat(readerEs).isEqualToIgnoringGivenFields(testReader);
    }

    @Test
    @Transactional
    public void updateNonExistingReader() throws Exception {
        int databaseSizeBeforeUpdate = readerRepository.findAll().size();

        // Create the Reader

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restReaderMockMvc.perform(put("/api/readers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reader)))
            .andExpect(status().isCreated());

        // Validate the Reader in the database
        List<Reader> readerList = readerRepository.findAll();
        assertThat(readerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteReader() throws Exception {
        // Initialize the database
        readerRepository.saveAndFlush(reader);
        readerSearchRepository.save(reader);
        int databaseSizeBeforeDelete = readerRepository.findAll().size();

        // Get the reader
        restReaderMockMvc.perform(delete("/api/readers/{id}", reader.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean readerExistsInEs = readerSearchRepository.exists(reader.getId());
        assertThat(readerExistsInEs).isFalse();

        // Validate the database is empty
        List<Reader> readerList = readerRepository.findAll();
        assertThat(readerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchReader() throws Exception {
        // Initialize the database
        readerRepository.saveAndFlush(reader);
        readerSearchRepository.save(reader);

        // Search the reader
        restReaderMockMvc.perform(get("/api/_search/readers?query=id:" + reader.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reader.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageFile").value(hasItem(DEFAULT_IMAGE_FILE.toString())))
            .andExpect(jsonPath("$.[*].textFile").value(hasItem(DEFAULT_TEXT_FILE.toString())))
            .andExpect(jsonPath("$.[*].blobFile").value(hasItem(DEFAULT_BLOB_FILE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reader.class);
        Reader reader1 = new Reader();
        reader1.setId(1L);
        Reader reader2 = new Reader();
        reader2.setId(reader1.getId());
        assertThat(reader1).isEqualTo(reader2);
        reader2.setId(2L);
        assertThat(reader1).isNotEqualTo(reader2);
        reader1.setId(null);
        assertThat(reader1).isNotEqualTo(reader2);
    }
}
