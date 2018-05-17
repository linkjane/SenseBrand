package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;

import io.github.jhipster.application.domain.DesignerIdeaMedia;
import io.github.jhipster.application.repository.DesignerIdeaMediaRepository;
import io.github.jhipster.application.service.DesignerIdeaMediaService;
import io.github.jhipster.application.repository.search.DesignerIdeaMediaSearchRepository;
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
 * Test class for the DesignerIdeaMediaResource REST controller.
 *
 * @see DesignerIdeaMediaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class DesignerIdeaMediaResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_SHARE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SHARE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_INTRODUCTION = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCTION = "BBBBBBBBBB";

    private static final String DEFAULT_MEDIA_FILE = "AAAAAAAAAA";
    private static final String UPDATED_MEDIA_FILE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_SHOW = false;
    private static final Boolean UPDATED_IS_SHOW = true;

    @Autowired
    private DesignerIdeaMediaRepository designerIdeaMediaRepository;

    @Autowired
    private DesignerIdeaMediaService designerIdeaMediaService;

    @Autowired
    private DesignerIdeaMediaSearchRepository designerIdeaMediaSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDesignerIdeaMediaMockMvc;

    private DesignerIdeaMedia designerIdeaMedia;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DesignerIdeaMediaResource designerIdeaMediaResource = new DesignerIdeaMediaResource(designerIdeaMediaService);
        this.restDesignerIdeaMediaMockMvc = MockMvcBuilders.standaloneSetup(designerIdeaMediaResource)
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
    public static DesignerIdeaMedia createEntity(EntityManager em) {
        DesignerIdeaMedia designerIdeaMedia = new DesignerIdeaMedia()
            .title(DEFAULT_TITLE)
            .shareTime(DEFAULT_SHARE_TIME)
            .introduction(DEFAULT_INTRODUCTION)
            .mediaFile(DEFAULT_MEDIA_FILE)
            .isShow(DEFAULT_IS_SHOW);
        return designerIdeaMedia;
    }

    @Before
    public void initTest() {
        designerIdeaMediaSearchRepository.deleteAll();
        designerIdeaMedia = createEntity(em);
    }

    @Test
    @Transactional
    public void createDesignerIdeaMedia() throws Exception {
        int databaseSizeBeforeCreate = designerIdeaMediaRepository.findAll().size();

        // Create the DesignerIdeaMedia
        restDesignerIdeaMediaMockMvc.perform(post("/api/designer-idea-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerIdeaMedia)))
            .andExpect(status().isCreated());

        // Validate the DesignerIdeaMedia in the database
        List<DesignerIdeaMedia> designerIdeaMediaList = designerIdeaMediaRepository.findAll();
        assertThat(designerIdeaMediaList).hasSize(databaseSizeBeforeCreate + 1);
        DesignerIdeaMedia testDesignerIdeaMedia = designerIdeaMediaList.get(designerIdeaMediaList.size() - 1);
        assertThat(testDesignerIdeaMedia.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDesignerIdeaMedia.getShareTime()).isEqualTo(DEFAULT_SHARE_TIME);
        assertThat(testDesignerIdeaMedia.getIntroduction()).isEqualTo(DEFAULT_INTRODUCTION);
        assertThat(testDesignerIdeaMedia.getMediaFile()).isEqualTo(DEFAULT_MEDIA_FILE);
        assertThat(testDesignerIdeaMedia.isIsShow()).isEqualTo(DEFAULT_IS_SHOW);

        // Validate the DesignerIdeaMedia in Elasticsearch
        DesignerIdeaMedia designerIdeaMediaEs = designerIdeaMediaSearchRepository.findOne(testDesignerIdeaMedia.getId());
        assertThat(designerIdeaMediaEs).isEqualToIgnoringGivenFields(testDesignerIdeaMedia);
    }

    @Test
    @Transactional
    public void createDesignerIdeaMediaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = designerIdeaMediaRepository.findAll().size();

        // Create the DesignerIdeaMedia with an existing ID
        designerIdeaMedia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDesignerIdeaMediaMockMvc.perform(post("/api/designer-idea-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerIdeaMedia)))
            .andExpect(status().isBadRequest());

        // Validate the DesignerIdeaMedia in the database
        List<DesignerIdeaMedia> designerIdeaMediaList = designerIdeaMediaRepository.findAll();
        assertThat(designerIdeaMediaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = designerIdeaMediaRepository.findAll().size();
        // set the field null
        designerIdeaMedia.setTitle(null);

        // Create the DesignerIdeaMedia, which fails.

        restDesignerIdeaMediaMockMvc.perform(post("/api/designer-idea-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerIdeaMedia)))
            .andExpect(status().isBadRequest());

        List<DesignerIdeaMedia> designerIdeaMediaList = designerIdeaMediaRepository.findAll();
        assertThat(designerIdeaMediaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShareTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = designerIdeaMediaRepository.findAll().size();
        // set the field null
        designerIdeaMedia.setShareTime(null);

        // Create the DesignerIdeaMedia, which fails.

        restDesignerIdeaMediaMockMvc.perform(post("/api/designer-idea-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerIdeaMedia)))
            .andExpect(status().isBadRequest());

        List<DesignerIdeaMedia> designerIdeaMediaList = designerIdeaMediaRepository.findAll();
        assertThat(designerIdeaMediaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMediaFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = designerIdeaMediaRepository.findAll().size();
        // set the field null
        designerIdeaMedia.setMediaFile(null);

        // Create the DesignerIdeaMedia, which fails.

        restDesignerIdeaMediaMockMvc.perform(post("/api/designer-idea-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerIdeaMedia)))
            .andExpect(status().isBadRequest());

        List<DesignerIdeaMedia> designerIdeaMediaList = designerIdeaMediaRepository.findAll();
        assertThat(designerIdeaMediaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDesignerIdeaMedias() throws Exception {
        // Initialize the database
        designerIdeaMediaRepository.saveAndFlush(designerIdeaMedia);

        // Get all the designerIdeaMediaList
        restDesignerIdeaMediaMockMvc.perform(get("/api/designer-idea-medias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designerIdeaMedia.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].shareTime").value(hasItem(DEFAULT_SHARE_TIME.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].mediaFile").value(hasItem(DEFAULT_MEDIA_FILE.toString())))
            .andExpect(jsonPath("$.[*].isShow").value(hasItem(DEFAULT_IS_SHOW.booleanValue())));
    }

    @Test
    @Transactional
    public void getDesignerIdeaMedia() throws Exception {
        // Initialize the database
        designerIdeaMediaRepository.saveAndFlush(designerIdeaMedia);

        // Get the designerIdeaMedia
        restDesignerIdeaMediaMockMvc.perform(get("/api/designer-idea-medias/{id}", designerIdeaMedia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(designerIdeaMedia.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.shareTime").value(DEFAULT_SHARE_TIME.toString()))
            .andExpect(jsonPath("$.introduction").value(DEFAULT_INTRODUCTION.toString()))
            .andExpect(jsonPath("$.mediaFile").value(DEFAULT_MEDIA_FILE.toString()))
            .andExpect(jsonPath("$.isShow").value(DEFAULT_IS_SHOW.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDesignerIdeaMedia() throws Exception {
        // Get the designerIdeaMedia
        restDesignerIdeaMediaMockMvc.perform(get("/api/designer-idea-medias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDesignerIdeaMedia() throws Exception {
        // Initialize the database
        designerIdeaMediaService.save(designerIdeaMedia);

        int databaseSizeBeforeUpdate = designerIdeaMediaRepository.findAll().size();

        // Update the designerIdeaMedia
        DesignerIdeaMedia updatedDesignerIdeaMedia = designerIdeaMediaRepository.findOne(designerIdeaMedia.getId());
        // Disconnect from session so that the updates on updatedDesignerIdeaMedia are not directly saved in db
        em.detach(updatedDesignerIdeaMedia);
        updatedDesignerIdeaMedia
            .title(UPDATED_TITLE)
            .shareTime(UPDATED_SHARE_TIME)
            .introduction(UPDATED_INTRODUCTION)
            .mediaFile(UPDATED_MEDIA_FILE)
            .isShow(UPDATED_IS_SHOW);

        restDesignerIdeaMediaMockMvc.perform(put("/api/designer-idea-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDesignerIdeaMedia)))
            .andExpect(status().isOk());

        // Validate the DesignerIdeaMedia in the database
        List<DesignerIdeaMedia> designerIdeaMediaList = designerIdeaMediaRepository.findAll();
        assertThat(designerIdeaMediaList).hasSize(databaseSizeBeforeUpdate);
        DesignerIdeaMedia testDesignerIdeaMedia = designerIdeaMediaList.get(designerIdeaMediaList.size() - 1);
        assertThat(testDesignerIdeaMedia.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDesignerIdeaMedia.getShareTime()).isEqualTo(UPDATED_SHARE_TIME);
        assertThat(testDesignerIdeaMedia.getIntroduction()).isEqualTo(UPDATED_INTRODUCTION);
        assertThat(testDesignerIdeaMedia.getMediaFile()).isEqualTo(UPDATED_MEDIA_FILE);
        assertThat(testDesignerIdeaMedia.isIsShow()).isEqualTo(UPDATED_IS_SHOW);

        // Validate the DesignerIdeaMedia in Elasticsearch
        DesignerIdeaMedia designerIdeaMediaEs = designerIdeaMediaSearchRepository.findOne(testDesignerIdeaMedia.getId());
        assertThat(designerIdeaMediaEs).isEqualToIgnoringGivenFields(testDesignerIdeaMedia);
    }

    @Test
    @Transactional
    public void updateNonExistingDesignerIdeaMedia() throws Exception {
        int databaseSizeBeforeUpdate = designerIdeaMediaRepository.findAll().size();

        // Create the DesignerIdeaMedia

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDesignerIdeaMediaMockMvc.perform(put("/api/designer-idea-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(designerIdeaMedia)))
            .andExpect(status().isCreated());

        // Validate the DesignerIdeaMedia in the database
        List<DesignerIdeaMedia> designerIdeaMediaList = designerIdeaMediaRepository.findAll();
        assertThat(designerIdeaMediaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDesignerIdeaMedia() throws Exception {
        // Initialize the database
        designerIdeaMediaService.save(designerIdeaMedia);

        int databaseSizeBeforeDelete = designerIdeaMediaRepository.findAll().size();

        // Get the designerIdeaMedia
        restDesignerIdeaMediaMockMvc.perform(delete("/api/designer-idea-medias/{id}", designerIdeaMedia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean designerIdeaMediaExistsInEs = designerIdeaMediaSearchRepository.exists(designerIdeaMedia.getId());
        assertThat(designerIdeaMediaExistsInEs).isFalse();

        // Validate the database is empty
        List<DesignerIdeaMedia> designerIdeaMediaList = designerIdeaMediaRepository.findAll();
        assertThat(designerIdeaMediaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDesignerIdeaMedia() throws Exception {
        // Initialize the database
        designerIdeaMediaService.save(designerIdeaMedia);

        // Search the designerIdeaMedia
        restDesignerIdeaMediaMockMvc.perform(get("/api/_search/designer-idea-medias?query=id:" + designerIdeaMedia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(designerIdeaMedia.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].shareTime").value(hasItem(DEFAULT_SHARE_TIME.toString())))
            .andExpect(jsonPath("$.[*].introduction").value(hasItem(DEFAULT_INTRODUCTION.toString())))
            .andExpect(jsonPath("$.[*].mediaFile").value(hasItem(DEFAULT_MEDIA_FILE.toString())))
            .andExpect(jsonPath("$.[*].isShow").value(hasItem(DEFAULT_IS_SHOW.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DesignerIdeaMedia.class);
        DesignerIdeaMedia designerIdeaMedia1 = new DesignerIdeaMedia();
        designerIdeaMedia1.setId(1L);
        DesignerIdeaMedia designerIdeaMedia2 = new DesignerIdeaMedia();
        designerIdeaMedia2.setId(designerIdeaMedia1.getId());
        assertThat(designerIdeaMedia1).isEqualTo(designerIdeaMedia2);
        designerIdeaMedia2.setId(2L);
        assertThat(designerIdeaMedia1).isNotEqualTo(designerIdeaMedia2);
        designerIdeaMedia1.setId(null);
        assertThat(designerIdeaMedia1).isNotEqualTo(designerIdeaMedia2);
    }
}
