package com.sirtts.web.rest;

import com.sirtts.HcPortalApp;

import com.sirtts.domain.DiabetesSugarTest;
import com.sirtts.repository.DiabetesSugarTestRepository;
import com.sirtts.service.DiabetesSugarTestService;
import com.sirtts.service.dto.DiabetesSugarTestDTO;
import com.sirtts.service.mapper.DiabetesSugarTestMapper;
import com.sirtts.web.rest.errors.ExceptionTranslator;

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

import java.time.*;
import java.util.List;

import static com.sirtts.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DiabetesSugarTestResource REST controller.
 *
 * @see DiabetesSugarTestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcPortalApp.class)
public class DiabetesSugarTestResourceIntTest {

    private static final String DEFAULT_USERID = "AAAAAAAAAA";
    private static final String UPDATED_USERID = "BBBBBBBBBB";

    private static final Double DEFAULT_RESULT = 1D;
    private static final Double UPDATED_RESULT = 2D;

    private static final LocalDateTime DEFAULT_MEASURMENTDATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);
    private static final LocalDateTime UPDATED_MEASURMENTDATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);

    @Autowired
    private DiabetesSugarTestRepository diabetesSugarTestRepository;

    @Autowired
    private DiabetesSugarTestMapper diabetesSugarTestMapper;

    @Autowired
    private DiabetesSugarTestService diabetesSugarTestService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restDiabetesSugarTestMockMvc;

    private DiabetesSugarTest diabetesSugarTest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiabetesSugarTestResource diabetesSugarTestResource = new DiabetesSugarTestResource(diabetesSugarTestService);
        this.restDiabetesSugarTestMockMvc = MockMvcBuilders.standaloneSetup(diabetesSugarTestResource)
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
    public static DiabetesSugarTest createEntity() {
        DiabetesSugarTest diabetesSugarTest = new DiabetesSugarTest()
            .userid(DEFAULT_USERID)
            .result(DEFAULT_RESULT)
            .measurmentdate(DEFAULT_MEASURMENTDATE);
        return diabetesSugarTest;
    }

    @Before
    public void initTest() {
        diabetesSugarTestRepository.deleteAll();
        diabetesSugarTest = createEntity();
    }

    @Test
    public void createDiabetesSugarTest() throws Exception {
        int databaseSizeBeforeCreate = diabetesSugarTestRepository.findAll().size();

        // Create the DiabetesSugarTest
        DiabetesSugarTestDTO diabetesSugarTestDTO = diabetesSugarTestMapper.toDto(diabetesSugarTest);
        restDiabetesSugarTestMockMvc.perform(post("/api/diabetes-sugar-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diabetesSugarTestDTO)))
            .andExpect(status().isCreated());

        // Validate the DiabetesSugarTest in the database
        List<DiabetesSugarTest> diabetesSugarTestList = diabetesSugarTestRepository.findAll();
        assertThat(diabetesSugarTestList).hasSize(databaseSizeBeforeCreate + 1);
        DiabetesSugarTest testDiabetesSugarTest = diabetesSugarTestList.get(diabetesSugarTestList.size() - 1);
        assertThat(testDiabetesSugarTest.getUserid()).isEqualTo(DEFAULT_USERID);
        assertThat(testDiabetesSugarTest.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testDiabetesSugarTest.getMeasurmentdate()).isEqualTo(DEFAULT_MEASURMENTDATE);
    }

    @Test
    public void createDiabetesSugarTestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diabetesSugarTestRepository.findAll().size();

        // Create the DiabetesSugarTest with an existing ID
        diabetesSugarTest.setId("existing_id");
        DiabetesSugarTestDTO diabetesSugarTestDTO = diabetesSugarTestMapper.toDto(diabetesSugarTest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiabetesSugarTestMockMvc.perform(post("/api/diabetes-sugar-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diabetesSugarTestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DiabetesSugarTest in the database
        List<DiabetesSugarTest> diabetesSugarTestList = diabetesSugarTestRepository.findAll();
        assertThat(diabetesSugarTestList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkResultIsRequired() throws Exception {
        int databaseSizeBeforeTest = diabetesSugarTestRepository.findAll().size();
        // set the field null
        diabetesSugarTest.setResult(null);

        // Create the DiabetesSugarTest, which fails.
        DiabetesSugarTestDTO diabetesSugarTestDTO = diabetesSugarTestMapper.toDto(diabetesSugarTest);

        restDiabetesSugarTestMockMvc.perform(post("/api/diabetes-sugar-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diabetesSugarTestDTO)))
            .andExpect(status().isBadRequest());

        List<DiabetesSugarTest> diabetesSugarTestList = diabetesSugarTestRepository.findAll();
        assertThat(diabetesSugarTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkMeasurmentdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = diabetesSugarTestRepository.findAll().size();
        // set the field null
        diabetesSugarTest.setMeasurmentdate(null);

        // Create the DiabetesSugarTest, which fails.
        DiabetesSugarTestDTO diabetesSugarTestDTO = diabetesSugarTestMapper.toDto(diabetesSugarTest);

        restDiabetesSugarTestMockMvc.perform(post("/api/diabetes-sugar-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diabetesSugarTestDTO)))
            .andExpect(status().isBadRequest());

        List<DiabetesSugarTest> diabetesSugarTestList = diabetesSugarTestRepository.findAll();
        assertThat(diabetesSugarTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllDiabetesSugarTests() throws Exception {
        // Initialize the database
        diabetesSugarTestRepository.save(diabetesSugarTest);

        // Get all the diabetesSugarTestList
        restDiabetesSugarTestMockMvc.perform(get("/api/diabetes-sugar-tests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diabetesSugarTest.getId())))
            .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID.toString())))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT.doubleValue())))
            .andExpect(jsonPath("$.[*].measurmentdate").value(hasItem(DEFAULT_MEASURMENTDATE.toString())));
    }

    @Test
    public void getDiabetesSugarTest() throws Exception {
        // Initialize the database
        diabetesSugarTestRepository.save(diabetesSugarTest);

        // Get the diabetesSugarTest
        restDiabetesSugarTestMockMvc.perform(get("/api/diabetes-sugar-tests/{id}", diabetesSugarTest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(diabetesSugarTest.getId()))
            .andExpect(jsonPath("$.userid").value(DEFAULT_USERID.toString()))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT.doubleValue()))
            .andExpect(jsonPath("$.measurmentdate").value(DEFAULT_MEASURMENTDATE.toString()));
    }

    @Test
    public void getNonExistingDiabetesSugarTest() throws Exception {
        // Get the diabetesSugarTest
        restDiabetesSugarTestMockMvc.perform(get("/api/diabetes-sugar-tests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDiabetesSugarTest() throws Exception {
        // Initialize the database
        diabetesSugarTestRepository.save(diabetesSugarTest);
        int databaseSizeBeforeUpdate = diabetesSugarTestRepository.findAll().size();

        // Update the diabetesSugarTest
        DiabetesSugarTest updatedDiabetesSugarTest = diabetesSugarTestRepository.findOne(diabetesSugarTest.getId());
        updatedDiabetesSugarTest
            .userid(UPDATED_USERID)
            .result(UPDATED_RESULT)
            .measurmentdate(UPDATED_MEASURMENTDATE);
        DiabetesSugarTestDTO diabetesSugarTestDTO = diabetesSugarTestMapper.toDto(updatedDiabetesSugarTest);

        restDiabetesSugarTestMockMvc.perform(put("/api/diabetes-sugar-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diabetesSugarTestDTO)))
            .andExpect(status().isOk());

        // Validate the DiabetesSugarTest in the database
        List<DiabetesSugarTest> diabetesSugarTestList = diabetesSugarTestRepository.findAll();
        assertThat(diabetesSugarTestList).hasSize(databaseSizeBeforeUpdate);
        DiabetesSugarTest testDiabetesSugarTest = diabetesSugarTestList.get(diabetesSugarTestList.size() - 1);
        assertThat(testDiabetesSugarTest.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testDiabetesSugarTest.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testDiabetesSugarTest.getMeasurmentdate()).isEqualTo(UPDATED_MEASURMENTDATE);
    }

    @Test
    public void updateNonExistingDiabetesSugarTest() throws Exception {
        int databaseSizeBeforeUpdate = diabetesSugarTestRepository.findAll().size();

        // Create the DiabetesSugarTest
        DiabetesSugarTestDTO diabetesSugarTestDTO = diabetesSugarTestMapper.toDto(diabetesSugarTest);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDiabetesSugarTestMockMvc.perform(put("/api/diabetes-sugar-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diabetesSugarTestDTO)))
            .andExpect(status().isCreated());

        // Validate the DiabetesSugarTest in the database
        List<DiabetesSugarTest> diabetesSugarTestList = diabetesSugarTestRepository.findAll();
        assertThat(diabetesSugarTestList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteDiabetesSugarTest() throws Exception {
        // Initialize the database
        diabetesSugarTestRepository.save(diabetesSugarTest);
        int databaseSizeBeforeDelete = diabetesSugarTestRepository.findAll().size();

        // Get the diabetesSugarTest
        restDiabetesSugarTestMockMvc.perform(delete("/api/diabetes-sugar-tests/{id}", diabetesSugarTest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DiabetesSugarTest> diabetesSugarTestList = diabetesSugarTestRepository.findAll();
        assertThat(diabetesSugarTestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiabetesSugarTest.class);
        DiabetesSugarTest diabetesSugarTest1 = new DiabetesSugarTest();
        diabetesSugarTest1.setId("id1");
        DiabetesSugarTest diabetesSugarTest2 = new DiabetesSugarTest();
        diabetesSugarTest2.setId(diabetesSugarTest1.getId());
        assertThat(diabetesSugarTest1).isEqualTo(diabetesSugarTest2);
        diabetesSugarTest2.setId("id2");
        assertThat(diabetesSugarTest1).isNotEqualTo(diabetesSugarTest2);
        diabetesSugarTest1.setId(null);
        assertThat(diabetesSugarTest1).isNotEqualTo(diabetesSugarTest2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiabetesSugarTestDTO.class);
        DiabetesSugarTestDTO diabetesSugarTestDTO1 = new DiabetesSugarTestDTO();
        diabetesSugarTestDTO1.setId("id1");
        DiabetesSugarTestDTO diabetesSugarTestDTO2 = new DiabetesSugarTestDTO();
        assertThat(diabetesSugarTestDTO1).isNotEqualTo(diabetesSugarTestDTO2);
        diabetesSugarTestDTO2.setId(diabetesSugarTestDTO1.getId());
        assertThat(diabetesSugarTestDTO1).isEqualTo(diabetesSugarTestDTO2);
        diabetesSugarTestDTO2.setId("id2");
        assertThat(diabetesSugarTestDTO1).isNotEqualTo(diabetesSugarTestDTO2);
        diabetesSugarTestDTO1.setId(null);
        assertThat(diabetesSugarTestDTO1).isNotEqualTo(diabetesSugarTestDTO2);
    }
}
