package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.Person;
import io.github.jhipster.application.repository.PersonRepository;
import io.github.jhipster.application.repository.search.PersonSearchRepository;
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
 * Test class for the PersonResource REST controller.
 *
 * @see PersonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class PersonResourceIntTest {

    private static final byte[] DEFAULT_FILE_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE_1 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILE_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_FILE_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE_2 = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILE_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_2_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_FILE_3 = "AAAAAAAAAA";
    private static final String UPDATED_FILE_3 = "BBBBBBBBBB";

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonSearchRepository personSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPersonMockMvc;

    private Person person;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PersonResource personResource = new PersonResource(personRepository, personSearchRepository);
        this.restPersonMockMvc = MockMvcBuilders.standaloneSetup(personResource)
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
    public static Person createEntity(EntityManager em) {
        Person person = new Person()
            .file1(DEFAULT_FILE_1)
            .file1ContentType(DEFAULT_FILE_1_CONTENT_TYPE)
            .file2(DEFAULT_FILE_2)
            .file2ContentType(DEFAULT_FILE_2_CONTENT_TYPE)
            .file3(DEFAULT_FILE_3);
        return person;
    }

    @Before
    public void initTest() {
        personSearchRepository.deleteAll();
        person = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerson() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person
        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate + 1);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getFile1()).isEqualTo(DEFAULT_FILE_1);
        assertThat(testPerson.getFile1ContentType()).isEqualTo(DEFAULT_FILE_1_CONTENT_TYPE);
        assertThat(testPerson.getFile2()).isEqualTo(DEFAULT_FILE_2);
        assertThat(testPerson.getFile2ContentType()).isEqualTo(DEFAULT_FILE_2_CONTENT_TYPE);
        assertThat(testPerson.getFile3()).isEqualTo(DEFAULT_FILE_3);

        // Validate the Person in Elasticsearch
        Person personEs = personSearchRepository.findOne(testPerson.getId());
        assertThat(personEs).isEqualToIgnoringGivenFields(testPerson);
    }

    @Test
    @Transactional
    public void createPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person with an existing ID
        person.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPeople() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList
        restPersonMockMvc.perform(get("/api/people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].file1ContentType").value(hasItem(DEFAULT_FILE_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file1").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE_1))))
            .andExpect(jsonPath("$.[*].file2ContentType").value(hasItem(DEFAULT_FILE_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file2").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE_2))))
            .andExpect(jsonPath("$.[*].file3").value(hasItem(DEFAULT_FILE_3.toString())));
    }

    @Test
    @Transactional
    public void getPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.file1ContentType").value(DEFAULT_FILE_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.file1").value(Base64Utils.encodeToString(DEFAULT_FILE_1)))
            .andExpect(jsonPath("$.file2ContentType").value(DEFAULT_FILE_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.file2").value(Base64Utils.encodeToString(DEFAULT_FILE_2)))
            .andExpect(jsonPath("$.file3").value(DEFAULT_FILE_3.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);
        personSearchRepository.save(person);
        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person
        Person updatedPerson = personRepository.findOne(person.getId());
        // Disconnect from session so that the updates on updatedPerson are not directly saved in db
        em.detach(updatedPerson);
        updatedPerson
            .file1(UPDATED_FILE_1)
            .file1ContentType(UPDATED_FILE_1_CONTENT_TYPE)
            .file2(UPDATED_FILE_2)
            .file2ContentType(UPDATED_FILE_2_CONTENT_TYPE)
            .file3(UPDATED_FILE_3);

        restPersonMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPerson)))
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getFile1()).isEqualTo(UPDATED_FILE_1);
        assertThat(testPerson.getFile1ContentType()).isEqualTo(UPDATED_FILE_1_CONTENT_TYPE);
        assertThat(testPerson.getFile2()).isEqualTo(UPDATED_FILE_2);
        assertThat(testPerson.getFile2ContentType()).isEqualTo(UPDATED_FILE_2_CONTENT_TYPE);
        assertThat(testPerson.getFile3()).isEqualTo(UPDATED_FILE_3);

        // Validate the Person in Elasticsearch
        Person personEs = personSearchRepository.findOne(testPerson.getId());
        assertThat(personEs).isEqualToIgnoringGivenFields(testPerson);
    }

    @Test
    @Transactional
    public void updateNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Create the Person

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(person)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);
        personSearchRepository.save(person);
        int databaseSizeBeforeDelete = personRepository.findAll().size();

        // Get the person
        restPersonMockMvc.perform(delete("/api/people/{id}", person.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean personExistsInEs = personSearchRepository.exists(person.getId());
        assertThat(personExistsInEs).isFalse();

        // Validate the database is empty
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);
        personSearchRepository.save(person);

        // Search the person
        restPersonMockMvc.perform(get("/api/_search/people?query=id:" + person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].file1ContentType").value(hasItem(DEFAULT_FILE_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file1").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE_1))))
            .andExpect(jsonPath("$.[*].file2ContentType").value(hasItem(DEFAULT_FILE_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file2").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE_2))))
            .andExpect(jsonPath("$.[*].file3").value(hasItem(DEFAULT_FILE_3.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Person.class);
        Person person1 = new Person();
        person1.setId(1L);
        Person person2 = new Person();
        person2.setId(person1.getId());
        assertThat(person1).isEqualTo(person2);
        person2.setId(2L);
        assertThat(person1).isNotEqualTo(person2);
        person1.setId(null);
        assertThat(person1).isNotEqualTo(person2);
    }
}
