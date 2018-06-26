package com.sirtts.web.rest;

import com.sirtts.HcPortalApp;

import com.sirtts.domain.MenstrualCycle;
import com.sirtts.repository.MenstrualCycleRepository;
import com.sirtts.service.MenstrualCycleService;
import com.sirtts.service.dto.MenstrualCycleDTO;
import com.sirtts.service.mapper.MenstrualCycleMapper;
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

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static com.sirtts.web.rest.TestUtil.createFormattingConversionService;
import static com.sirtts.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MenstrualCycleResource REST controller.
 *
 * @see MenstrualCycleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcPortalApp.class)
public class MenstrualCycleResourceIntTest {

    private static final String DEFAULT_USERID = "AAAAAAAAAA";
    private static final String UPDATED_USERID = "BBBBBBBBBB";

    private static final LocalDateTime DEFAULT_START_DATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);
    private static final LocalDateTime UPDATED_START_DATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);

    private static final LocalDateTime DEFAULT_END_DATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);
    private static final LocalDateTime UPDATED_END_DATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);

    @Autowired
    private MenstrualCycleRepository menstrualCycleRepository;

    @Autowired
    private MenstrualCycleMapper menstrualCycleMapper;

    @Autowired
    private MenstrualCycleService menstrualCycleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restMenstrualCycleMockMvc;

    private MenstrualCycle menstrualCycle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MenstrualCycleResource menstrualCycleResource = new MenstrualCycleResource(menstrualCycleService);
        this.restMenstrualCycleMockMvc = MockMvcBuilders.standaloneSetup(menstrualCycleResource)
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
    public static MenstrualCycle createEntity() {
        MenstrualCycle menstrualCycle = new MenstrualCycle()
            .userid(DEFAULT_USERID)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return menstrualCycle;
    }

    @Before
    public void initTest() {
        menstrualCycleRepository.deleteAll();
        menstrualCycle = createEntity();
    }

    @Test
    public void createMenstrualCycle() throws Exception {
        int databaseSizeBeforeCreate = menstrualCycleRepository.findAll().size();

        // Create the MenstrualCycle
        MenstrualCycleDTO menstrualCycleDTO = menstrualCycleMapper.toDto(menstrualCycle);
        restMenstrualCycleMockMvc.perform(post("/api/menstrual-cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menstrualCycleDTO)))
            .andExpect(status().isCreated());

        // Validate the MenstrualCycle in the database
        List<MenstrualCycle> menstrualCycleList = menstrualCycleRepository.findAll();
        assertThat(menstrualCycleList).hasSize(databaseSizeBeforeCreate + 1);
        MenstrualCycle testMenstrualCycle = menstrualCycleList.get(menstrualCycleList.size() - 1);
        assertThat(testMenstrualCycle.getUserid()).isEqualTo(DEFAULT_USERID);
        assertThat(testMenstrualCycle.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testMenstrualCycle.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    public void createMenstrualCycleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = menstrualCycleRepository.findAll().size();

        // Create the MenstrualCycle with an existing ID
        menstrualCycle.setId("existing_id");
        MenstrualCycleDTO menstrualCycleDTO = menstrualCycleMapper.toDto(menstrualCycle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenstrualCycleMockMvc.perform(post("/api/menstrual-cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menstrualCycleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MenstrualCycle in the database
        List<MenstrualCycle> menstrualCycleList = menstrualCycleRepository.findAll();
        assertThat(menstrualCycleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = menstrualCycleRepository.findAll().size();
        // set the field null
        menstrualCycle.setStartDate(null);

        // Create the MenstrualCycle, which fails.
        MenstrualCycleDTO menstrualCycleDTO = menstrualCycleMapper.toDto(menstrualCycle);

        restMenstrualCycleMockMvc.perform(post("/api/menstrual-cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menstrualCycleDTO)))
            .andExpect(status().isBadRequest());

        List<MenstrualCycle> menstrualCycleList = menstrualCycleRepository.findAll();
        assertThat(menstrualCycleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = menstrualCycleRepository.findAll().size();
        // set the field null
        menstrualCycle.setEndDate(null);

        // Create the MenstrualCycle, which fails.
        MenstrualCycleDTO menstrualCycleDTO = menstrualCycleMapper.toDto(menstrualCycle);

        restMenstrualCycleMockMvc.perform(post("/api/menstrual-cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menstrualCycleDTO)))
            .andExpect(status().isBadRequest());

        List<MenstrualCycle> menstrualCycleList = menstrualCycleRepository.findAll();
        assertThat(menstrualCycleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllMenstrualCycles() throws Exception {
        // Initialize the database
        menstrualCycleRepository.save(menstrualCycle);

        // Get all the menstrualCycleList
        restMenstrualCycleMockMvc.perform(get("/api/menstrual-cycles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menstrualCycle.getId())))
            .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    public void getMenstrualCycle() throws Exception {
        // Initialize the database
        menstrualCycleRepository.save(menstrualCycle);

        // Get the menstrualCycle
        restMenstrualCycleMockMvc.perform(get("/api/menstrual-cycles/{id}", menstrualCycle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(menstrualCycle.getId()))
            .andExpect(jsonPath("$.userid").value(DEFAULT_USERID.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    public void getNonExistingMenstrualCycle() throws Exception {
        // Get the menstrualCycle
        restMenstrualCycleMockMvc.perform(get("/api/menstrual-cycles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateMenstrualCycle() throws Exception {
        // Initialize the database
        menstrualCycleRepository.save(menstrualCycle);
        int databaseSizeBeforeUpdate = menstrualCycleRepository.findAll().size();

        // Update the menstrualCycle
        MenstrualCycle updatedMenstrualCycle = menstrualCycleRepository.findOne(menstrualCycle.getId());
        updatedMenstrualCycle
            .userid(UPDATED_USERID)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        MenstrualCycleDTO menstrualCycleDTO = menstrualCycleMapper.toDto(updatedMenstrualCycle);

        restMenstrualCycleMockMvc.perform(put("/api/menstrual-cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menstrualCycleDTO)))
            .andExpect(status().isOk());

        // Validate the MenstrualCycle in the database
        List<MenstrualCycle> menstrualCycleList = menstrualCycleRepository.findAll();
        assertThat(menstrualCycleList).hasSize(databaseSizeBeforeUpdate);
        MenstrualCycle testMenstrualCycle = menstrualCycleList.get(menstrualCycleList.size() - 1);
        assertThat(testMenstrualCycle.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testMenstrualCycle.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testMenstrualCycle.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    public void updateNonExistingMenstrualCycle() throws Exception {
        int databaseSizeBeforeUpdate = menstrualCycleRepository.findAll().size();

        // Create the MenstrualCycle
        MenstrualCycleDTO menstrualCycleDTO = menstrualCycleMapper.toDto(menstrualCycle);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMenstrualCycleMockMvc.perform(put("/api/menstrual-cycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menstrualCycleDTO)))
            .andExpect(status().isCreated());

        // Validate the MenstrualCycle in the database
        List<MenstrualCycle> menstrualCycleList = menstrualCycleRepository.findAll();
        assertThat(menstrualCycleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteMenstrualCycle() throws Exception {
        // Initialize the database
        menstrualCycleRepository.save(menstrualCycle);
        int databaseSizeBeforeDelete = menstrualCycleRepository.findAll().size();

        // Get the menstrualCycle
        restMenstrualCycleMockMvc.perform(delete("/api/menstrual-cycles/{id}", menstrualCycle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MenstrualCycle> menstrualCycleList = menstrualCycleRepository.findAll();
        assertThat(menstrualCycleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenstrualCycle.class);
        MenstrualCycle menstrualCycle1 = new MenstrualCycle();
        menstrualCycle1.setId("id1");
        MenstrualCycle menstrualCycle2 = new MenstrualCycle();
        menstrualCycle2.setId(menstrualCycle1.getId());
        assertThat(menstrualCycle1).isEqualTo(menstrualCycle2);
        menstrualCycle2.setId("id2");
        assertThat(menstrualCycle1).isNotEqualTo(menstrualCycle2);
        menstrualCycle1.setId(null);
        assertThat(menstrualCycle1).isNotEqualTo(menstrualCycle2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenstrualCycleDTO.class);
        MenstrualCycleDTO menstrualCycleDTO1 = new MenstrualCycleDTO();
        menstrualCycleDTO1.setId("id1");
        MenstrualCycleDTO menstrualCycleDTO2 = new MenstrualCycleDTO();
        assertThat(menstrualCycleDTO1).isNotEqualTo(menstrualCycleDTO2);
        menstrualCycleDTO2.setId(menstrualCycleDTO1.getId());
        assertThat(menstrualCycleDTO1).isEqualTo(menstrualCycleDTO2);
        menstrualCycleDTO2.setId("id2");
        assertThat(menstrualCycleDTO1).isNotEqualTo(menstrualCycleDTO2);
        menstrualCycleDTO1.setId(null);
        assertThat(menstrualCycleDTO1).isNotEqualTo(menstrualCycleDTO2);
    }
}
