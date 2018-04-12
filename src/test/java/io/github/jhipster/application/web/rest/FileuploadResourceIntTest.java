package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SenseBrandApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the Fileupload REST controller.
 *
 * @see FileuploadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SenseBrandApp.class)
public class FileuploadResourceIntTest {

    private MockMvc restMockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        FileuploadResource fileuploadResource = new FileuploadResource();
        restMockMvc = MockMvcBuilders
            .standaloneSetup(fileuploadResource)
            .build();
    }

    /**
    * Test findAll
    */
    @Test
    public void testFindAll() throws Exception {
        restMockMvc.perform(get("/api/fileupload/find-all"))
            .andExpect(status().isOk());
    }

}
