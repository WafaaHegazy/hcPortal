package com.sirtts.web.rest;

import com.sirtts.HcPortalApp;

import com.sirtts.domain.VsBloodPressure;
import com.sirtts.repository.VsBloodPressureRepository;
import com.sirtts.service.VsBloodPressureService;
import com.sirtts.service.dto.VsBloodPressureDTO;
import com.sirtts.service.mapper.VsBloodPressureMapper;
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
 * Test class for the VsBloodPressureResource REST controller.
 *
 * @see VsBloodPressureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcPortalApp.class)
public class VsBloodPressureResourceIntTest {

    private static final String DEFAULT_USERID = "AAAAAAAAAA";
    private static final String UPDATED_USERID = "BBBBBBBBBB";

    private static final Double DEFAULT_SYSTOLIC = 1D;
    private static final Double UPDATED_SYSTOLIC = 2D;

    private static final Double DEFAULT_DIASTOLIC = 1D;
    private static final Double UPDATED_DIASTOLIC = 2D;

    private static final LocalDateTime DEFAULT_MEASURMENTDATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);
    private static final LocalDateTime UPDATED_MEASURMENTDATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);

    @Autowired
    private VsBloodPressureRepository vsBloodPressureRepository;

    @Autowired
    private VsBloodPressureMapper vsBloodPressureMapper;

    @Autowired
    private VsBloodPressureService vsBloodPressureService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restVsBloodPressureMockMvc;

    private VsBloodPressure vsBloodPressure;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VsBloodPressureResource vsBloodPressureResource = new VsBloodPressureResource(vsBloodPressureService);
        this.restVsBloodPressureMockMvc = MockMvcBuilders.standaloneSetup(vsBloodPressureResource)
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
    public static VsBloodPressure createEntity() {
        VsBloodPressure vsBloodPressure = new VsBloodPressure()
            .userid(DEFAULT_USERID)
            .systolic(DEFAULT_SYSTOLIC)
            .diastolic(DEFAULT_DIASTOLIC)
            .measurmentdate(DEFAULT_MEASURMENTDATE);
        return vsBloodPressure;
    }

    @Before
    public void initTest() {
        vsBloodPressureRepository.deleteAll();
        vsBloodPressure = createEntity();
    }

    @Test
    public void createVsBloodPressure() throws Exception {
        int databaseSizeBeforeCreate = vsBloodPressureRepository.findAll().size();

        // Create the VsBloodPressure
        VsBloodPressureDTO vsBloodPressureDTO = vsBloodPressureMapper.toDto(vsBloodPressure);
        restVsBloodPressureMockMvc.perform(post("/api/vs-blood-pressures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsBloodPressureDTO)))
            .andExpect(status().isCreated());

        // Validate the VsBloodPressure in the database
        List<VsBloodPressure> vsBloodPressureList = vsBloodPressureRepository.findAll();
        assertThat(vsBloodPressureList).hasSize(databaseSizeBeforeCreate + 1);
        VsBloodPressure testVsBloodPressure = vsBloodPressureList.get(vsBloodPressureList.size() - 1);
        assertThat(testVsBloodPressure.getUserid()).isEqualTo(DEFAULT_USERID);
        assertThat(testVsBloodPressure.getSystolic()).isEqualTo(DEFAULT_SYSTOLIC);
        assertThat(testVsBloodPressure.getDiastolic()).isEqualTo(DEFAULT_DIASTOLIC);
        assertThat(testVsBloodPressure.getMeasurmentdate()).isEqualTo(DEFAULT_MEASURMENTDATE);
    }

    @Test
    public void createVsBloodPressureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vsBloodPressureRepository.findAll().size();

        // Create the VsBloodPressure with an existing ID
        vsBloodPressure.setId("existing_id");
        VsBloodPressureDTO vsBloodPressureDTO = vsBloodPressureMapper.toDto(vsBloodPressure);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVsBloodPressureMockMvc.perform(post("/api/vs-blood-pressures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsBloodPressureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VsBloodPressure in the database
        List<VsBloodPressure> vsBloodPressureList = vsBloodPressureRepository.findAll();
        assertThat(vsBloodPressureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkSystolicIsRequired() throws Exception {
        int databaseSizeBeforeTest = vsBloodPressureRepository.findAll().size();
        // set the field null
        vsBloodPressure.setSystolic(null);

        // Create the VsBloodPressure, which fails.
        VsBloodPressureDTO vsBloodPressureDTO = vsBloodPressureMapper.toDto(vsBloodPressure);

        restVsBloodPressureMockMvc.perform(post("/api/vs-blood-pressures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsBloodPressureDTO)))
            .andExpect(status().isBadRequest());

        List<VsBloodPressure> vsBloodPressureList = vsBloodPressureRepository.findAll();
        assertThat(vsBloodPressureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDiastolicIsRequired() throws Exception {
        int databaseSizeBeforeTest = vsBloodPressureRepository.findAll().size();
        // set the field null
        vsBloodPressure.setDiastolic(null);

        // Create the VsBloodPressure, which fails.
        VsBloodPressureDTO vsBloodPressureDTO = vsBloodPressureMapper.toDto(vsBloodPressure);

        restVsBloodPressureMockMvc.perform(post("/api/vs-blood-pressures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsBloodPressureDTO)))
            .andExpect(status().isBadRequest());

        List<VsBloodPressure> vsBloodPressureList = vsBloodPressureRepository.findAll();
        assertThat(vsBloodPressureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkMeasurmentdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vsBloodPressureRepository.findAll().size();
        // set the field null
        vsBloodPressure.setMeasurmentdate(null);

        // Create the VsBloodPressure, which fails.
        VsBloodPressureDTO vsBloodPressureDTO = vsBloodPressureMapper.toDto(vsBloodPressure);

        restVsBloodPressureMockMvc.perform(post("/api/vs-blood-pressures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsBloodPressureDTO)))
            .andExpect(status().isBadRequest());

        List<VsBloodPressure> vsBloodPressureList = vsBloodPressureRepository.findAll();
        assertThat(vsBloodPressureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllVsBloodPressures() throws Exception {
        // Initialize the database
        vsBloodPressureRepository.save(vsBloodPressure);

        // Get all the vsBloodPressureList
        restVsBloodPressureMockMvc.perform(get("/api/vs-blood-pressures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vsBloodPressure.getId())))
            .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID.toString())))
            .andExpect(jsonPath("$.[*].systolic").value(hasItem(DEFAULT_SYSTOLIC.doubleValue())))
            .andExpect(jsonPath("$.[*].diastolic").value(hasItem(DEFAULT_DIASTOLIC.doubleValue())))
            .andExpect(jsonPath("$.[*].measurmentdate").value(hasItem(DEFAULT_MEASURMENTDATE.toString())));
    }

    @Test
    public void getVsBloodPressure() throws Exception {
        // Initialize the database
        vsBloodPressureRepository.save(vsBloodPressure);

        // Get the vsBloodPressure
        restVsBloodPressureMockMvc.perform(get("/api/vs-blood-pressures/{id}", vsBloodPressure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vsBloodPressure.getId()))
            .andExpect(jsonPath("$.userid").value(DEFAULT_USERID.toString()))
            .andExpect(jsonPath("$.systolic").value(DEFAULT_SYSTOLIC.doubleValue()))
            .andExpect(jsonPath("$.diastolic").value(DEFAULT_DIASTOLIC.doubleValue()))
            .andExpect(jsonPath("$.measurmentdate").value(DEFAULT_MEASURMENTDATE.toString()));
    }

    @Test
    public void getNonExistingVsBloodPressure() throws Exception {
        // Get the vsBloodPressure
        restVsBloodPressureMockMvc.perform(get("/api/vs-blood-pressures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateVsBloodPressure() throws Exception {
        // Initialize the database
        vsBloodPressureRepository.save(vsBloodPressure);
        int databaseSizeBeforeUpdate = vsBloodPressureRepository.findAll().size();

        // Update the vsBloodPressure
        VsBloodPressure updatedVsBloodPressure = vsBloodPressureRepository.findOne(vsBloodPressure.getId());
        updatedVsBloodPressure
            .userid(UPDATED_USERID)
            .systolic(UPDATED_SYSTOLIC)
            .diastolic(UPDATED_DIASTOLIC)
            .measurmentdate(UPDATED_MEASURMENTDATE);
        VsBloodPressureDTO vsBloodPressureDTO = vsBloodPressureMapper.toDto(updatedVsBloodPressure);

        restVsBloodPressureMockMvc.perform(put("/api/vs-blood-pressures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsBloodPressureDTO)))
            .andExpect(status().isOk());

        // Validate the VsBloodPressure in the database
        List<VsBloodPressure> vsBloodPressureList = vsBloodPressureRepository.findAll();
        assertThat(vsBloodPressureList).hasSize(databaseSizeBeforeUpdate);
        VsBloodPressure testVsBloodPressure = vsBloodPressureList.get(vsBloodPressureList.size() - 1);
        assertThat(testVsBloodPressure.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testVsBloodPressure.getSystolic()).isEqualTo(UPDATED_SYSTOLIC);
        assertThat(testVsBloodPressure.getDiastolic()).isEqualTo(UPDATED_DIASTOLIC);
        assertThat(testVsBloodPressure.getMeasurmentdate()).isEqualTo(UPDATED_MEASURMENTDATE);
    }

    @Test
    public void updateNonExistingVsBloodPressure() throws Exception {
        int databaseSizeBeforeUpdate = vsBloodPressureRepository.findAll().size();

        // Create the VsBloodPressure
        VsBloodPressureDTO vsBloodPressureDTO = vsBloodPressureMapper.toDto(vsBloodPressure);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVsBloodPressureMockMvc.perform(put("/api/vs-blood-pressures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsBloodPressureDTO)))
            .andExpect(status().isCreated());

        // Validate the VsBloodPressure in the database
        List<VsBloodPressure> vsBloodPressureList = vsBloodPressureRepository.findAll();
        assertThat(vsBloodPressureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteVsBloodPressure() throws Exception {
        // Initialize the database
        vsBloodPressureRepository.save(vsBloodPressure);
        int databaseSizeBeforeDelete = vsBloodPressureRepository.findAll().size();

        // Get the vsBloodPressure
        restVsBloodPressureMockMvc.perform(delete("/api/vs-blood-pressures/{id}", vsBloodPressure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VsBloodPressure> vsBloodPressureList = vsBloodPressureRepository.findAll();
        assertThat(vsBloodPressureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VsBloodPressure.class);
        VsBloodPressure vsBloodPressure1 = new VsBloodPressure();
        vsBloodPressure1.setId("id1");
        VsBloodPressure vsBloodPressure2 = new VsBloodPressure();
        vsBloodPressure2.setId(vsBloodPressure1.getId());
        assertThat(vsBloodPressure1).isEqualTo(vsBloodPressure2);
        vsBloodPressure2.setId("id2");
        assertThat(vsBloodPressure1).isNotEqualTo(vsBloodPressure2);
        vsBloodPressure1.setId(null);
        assertThat(vsBloodPressure1).isNotEqualTo(vsBloodPressure2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VsBloodPressureDTO.class);
        VsBloodPressureDTO vsBloodPressureDTO1 = new VsBloodPressureDTO();
        vsBloodPressureDTO1.setId("id1");
        VsBloodPressureDTO vsBloodPressureDTO2 = new VsBloodPressureDTO();
        assertThat(vsBloodPressureDTO1).isNotEqualTo(vsBloodPressureDTO2);
        vsBloodPressureDTO2.setId(vsBloodPressureDTO1.getId());
        assertThat(vsBloodPressureDTO1).isEqualTo(vsBloodPressureDTO2);
        vsBloodPressureDTO2.setId("id2");
        assertThat(vsBloodPressureDTO1).isNotEqualTo(vsBloodPressureDTO2);
        vsBloodPressureDTO1.setId(null);
        assertThat(vsBloodPressureDTO1).isNotEqualTo(vsBloodPressureDTO2);
    }
}
