package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.DesignerShow;
import io.github.jhipster.application.repository.DesignerShowRepository;
import io.github.jhipster.application.service.DesignerShowService;
import io.github.jhipster.application.repository.search.DesignerShowSearchRepository;
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
 * Test class for the DesignerShowResource REST controller.
 *
 * @see DesignerShowResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class DesignerShowResourceIntTest {

    private static final String DEFAULT_FIRST_LEVEL_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_LEVEL_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_LEVEL_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_LEVEL_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCTION = "BBBBBBBBBB";

    @Autowired
    private DesignerShowRepository designerShowRepository;

    @Autowired
    private DesignerShowService designerShowService;

    @Autowired
    private DesignerShowSearchRepository designerShowSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDesignerShowMockMvc;

    private DesignerShow designerShow;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DesignerShowResource designerShowResource = new DesignerShowResource(designerShowService);
        this.restDesignerShowMockMvc = MockMvcBuilders.standaloneSetup(designerShowResource)
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
    public static DesignerShow createEntity(EntityManager em) {
        DesignerShow designerShow = new DesignerShow()
            .firstLevelTitle(DEFAULT_FIRST_LEVEL_TITLE)
            .secondLevelTitle(DEFAULT_SECOND_LEVEL_TITLE)
            .introduction(DEFAULT_INTRODUCTION);
        return designerShow;
    }

    @Before
    public void initTest() {
        designerShowSearchRepository.deleteAll();
        designerShow = createEntity(em);
    }

    @Test
    @Transactional
    public void createDesignerShow() throws Exception {
        int databaseSizeBeforeCreate = designerShowRepository.findAll().size();

        // Create the DesignerShow
        restDesignerShowMockMvc.perform(post("/api/designer-shows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerShow)))
            .andExpect(status().isCreated());

        // Validate the DesignerShow in the database
        List<DesignerShow> designerShowList = designerShowRepository.findAll();
        assertThat(designerShowList).hasSize(databaseSizeBeforeCreate + 1);
        DesignerShow testDesignerShow = designerShowList.get(designerShowList.size() - 1);
        assertThat(testDesignerShow.getFirstLevelTitle()).isEqualTo(DEFAULT_FIRST_LEVEL_TITLE);
        assertThat(testDesignerShow.getSecondLevelTitle()).isEqualTo(DEFAULT_SECOND_LEVEL_TITLE);
        assertThat(testDesignerShow.getIntroduction()).isEqualTo(DEFAULT_INTRODUCTION);

        // Validate the DesignerShow in Elasticsearch
        DesignerShow designerShowEs = designerShowSearchRepository.findOne(testDesignerShow.getId());
        assertThat(designerShowEs).isEqualToIgnoringGivenFields(testDesignerShow);
    }

    @Test
    @Transactional
    public void createDesignerShowWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = designerShowRepository.findAll().size();

        // Create the DesignerShow with an existing ID
        designerShow.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignerShowMockMvc.perform(post("/api/designer-shows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerShow)))
            .andExpect(status().isBadRequest());

        // Validate the DesignerShow in the database
        List<DesignerShow> designerShowList = designerShowRepository.findAll();
        assertThat(designerShowList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFirstLevelTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = designerShowRepository.findAll().size();
        // set the field null
        designerShow.setFirstLevelTitle(null);

        // Create the DesignerShow, which fails.

        restDesignerShowMockMvc.perform(post("/api/designer-shows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerShow)))
            .andExpect(status().isBadRequest());

        List<DesignerShow> designerShowList = designerShowRepository.findAll();
        assertThat(designerShowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDesignerShows() throws Exception {
        // Initialize the database
        designerShowRepository.saveAndFlush(designerShow);

        // Get all the designerShowList
        restDesignerShowMockMvc.perform(get("/api/designer-shows?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designerShow.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstLevelTitle").value(hasItem(DEFAULT_FIRST_LEVEL_TITLE.toString())))
            .andExpect(jsonPath("$.[*].secondLevelTitle").value(hasItem(DEFAULT_SECOND_LEVEL_TITLE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())));
    }

    @Test
    @Transactional
    public void getDesignerShow() throws Exception {
        // Initialize the database
        designerShowRepository.saveAndFlush(designerShow);

        // Get the designerShow
        restDesignerShowMockMvc.perform(get("/api/designer-shows/{id}", designerShow.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(designerShow.getId().intValue()))
            .andExpect(jsonPath("$.firstLevelTitle").value(DEFAULT_FIRST_LEVEL_TITLE.toString()))
            .andExpect(jsonPath("$.secondLevelTitle").value(DEFAULT_SECOND_LEVEL_TITLE.toString()))
            .andExpect(jsonPath("$.introduction").value(DEFAULT_INTRODUCTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDesignerShow() throws Exception {
        // Get the designerShow
        restDesignerShowMockMvc.perform(get("/api/designer-shows/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDesignerShow() throws Exception {
        // Initialize the database
        designerShowService.save(designerShow);

        int databaseSizeBeforeUpdate = designerShowRepository.findAll().size();

        // Update the designerShow
        DesignerShow updatedDesignerShow = designerShowRepository.findOne(designerShow.getId());
        // Disconnect from session so that the updates on updatedDesignerShow are not directly saved in db
        em.detach(updatedDesignerShow);
        updatedDesignerShow
            .firstLevelTitle(UPDATED_FIRST_LEVEL_TITLE)
            .secondLevelTitle(UPDATED_SECOND_LEVEL_TITLE)
            .introduction(UPDATED_INTRODUCTION);

        restDesignerShowMockMvc.perform(put("/api/designer-shows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDesignerShow)))
            .andExpect(status().isOk());

        // Validate the DesignerShow in the database
        List<DesignerShow> designerShowList = designerShowRepository.findAll();
        assertThat(designerShowList).hasSize(databaseSizeBeforeUpdate);
        DesignerShow testDesignerShow = designerShowList.get(designerShowList.size() - 1);
        assertThat(testDesignerShow.getFirstLevelTitle()).isEqualTo(UPDATED_FIRST_LEVEL_TITLE);
        assertThat(testDesignerShow.getSecondLevelTitle()).isEqualTo(UPDATED_SECOND_LEVEL_TITLE);
        assertThat(testDesignerShow.getIntroduction()).isEqualTo(UPDATED_INTRODUCTION);

        // Validate the DesignerShow in Elasticsearch
        DesignerShow designerShowEs = designerShowSearchRepository.findOne(testDesignerShow.getId());
        assertThat(designerShowEs).isEqualToIgnoringGivenFields(testDesignerShow);
    }

    @Test
    @Transactional
    public void updateNonExistingDesignerShow() throws Exception {
        int databaseSizeBeforeUpdate = designerShowRepository.findAll().size();

        // Create the DesignerShow

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDesignerShowMockMvc.perform(put("/api/designer-shows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerShow)))
            .andExpect(status().isCreated());

        // Validate the DesignerShow in the database
        List<DesignerShow> designerShowList = designerShowRepository.findAll();
        assertThat(designerShowList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDesignerShow() throws Exception {
        // Initialize the database
        designerShowService.save(designerShow);

        int databaseSizeBeforeDelete = designerShowRepository.findAll().size();

        // Get the designerShow
        restDesignerShowMockMvc.perform(delete("/api/designer-shows/{id}", designerShow.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean designerShowExistsInEs = designerShowSearchRepository.exists(designerShow.getId());
        assertThat(designerShowExistsInEs).isFalse();

        // Validate the database is empty
        List<DesignerShow> designerShowList = designerShowRepository.findAll();
        assertThat(designerShowList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDesignerShow() throws Exception {
        // Initialize the database
        designerShowService.save(designerShow);

        // Search the designerShow
        restDesignerShowMockMvc.perform(get("/api/_search/designer-shows?query=id:" + designerShow.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designerShow.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstLevelTitle").value(hasItem(DEFAULT_FIRST_LEVEL_TITLE.toString())))
            .andExpect(jsonPath("$.[*].secondLevelTitle").value(hasItem(DEFAULT_SECOND_LEVEL_TITLE.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesignerShow.class);
        DesignerShow designerShow1 = new DesignerShow();
        designerShow1.setId(1L);
        DesignerShow designerShow2 = new DesignerShow();
        designerShow2.setId(designerShow1.getId());
        assertThat(designerShow1).isEqualTo(designerShow2);
        designerShow2.setId(2L);
        assertThat(designerShow1).isNotEqualTo(designerShow2);
        designerShow1.setId(null);
        assertThat(designerShow1).isNotEqualTo(designerShow2);
    }
}
