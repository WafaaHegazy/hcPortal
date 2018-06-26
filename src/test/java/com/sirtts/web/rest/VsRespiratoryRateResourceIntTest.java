package com.sirtts.web.rest;

import com.sirtts.HcPortalApp;

import com.sirtts.domain.VsRespiratoryRate;
import com.sirtts.repository.VsRespiratoryRateRepository;
import com.sirtts.service.VsRespiratoryRateService;
import com.sirtts.service.dto.VsRespiratoryRateDTO;
import com.sirtts.service.mapper.VsRespiratoryRateMapper;
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
 * Test class for the VsRespiratoryRateResource REST controller.
 *
 * @see VsRespiratoryRateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcPortalApp.class)
public class VsRespiratoryRateResourceIntTest {

    private static final String DEFAULT_USERID = "AAAAAAAAAA";
    private static final String UPDATED_USERID = "BBBBBBBBBB";

    private static final Double DEFAULT_BPM = 1D;
    private static final Double UPDATED_BPM = 2D;

    private static final LocalDateTime DEFAULT_MEASURMENTDATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);
    private static final LocalDateTime UPDATED_MEASURMENTDATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);

    @Autowired
    private VsRespiratoryRateRepository vsRespiratoryRateRepository;

    @Autowired
    private VsRespiratoryRateMapper vsRespiratoryRateMapper;

    @Autowired
    private VsRespiratoryRateService vsRespiratoryRateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restVsRespiratoryRateMockMvc;

    private VsRespiratoryRate vsRespiratoryRate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VsRespiratoryRateResource vsRespiratoryRateResource = new VsRespiratoryRateResource(vsRespiratoryRateService);
        this.restVsRespiratoryRateMockMvc = MockMvcBuilders.standaloneSetup(vsRespiratoryRateResource)
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
    public static VsRespiratoryRate createEntity() {
        VsRespiratoryRate vsRespiratoryRate = new VsRespiratoryRate()
            .userid(DEFAULT_USERID)
            .bpm(DEFAULT_BPM)
            .measurmentdate(DEFAULT_MEASURMENTDATE);
        return vsRespiratoryRate;
    }

    @Before
    public void initTest() {
        vsRespiratoryRateRepository.deleteAll();
        vsRespiratoryRate = createEntity();
    }

    @Test
    public void createVsRespiratoryRate() throws Exception {
        int databaseSizeBeforeCreate = vsRespiratoryRateRepository.findAll().size();

        // Create the VsRespiratoryRate
        VsRespiratoryRateDTO vsRespiratoryRateDTO = vsRespiratoryRateMapper.toDto(vsRespiratoryRate);
        restVsRespiratoryRateMockMvc.perform(post("/api/vs-respiratory-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsRespiratoryRateDTO)))
            .andExpect(status().isCreated());

        // Validate the VsRespiratoryRate in the database
        List<VsRespiratoryRate> vsRespiratoryRateList = vsRespiratoryRateRepository.findAll();
        assertThat(vsRespiratoryRateList).hasSize(databaseSizeBeforeCreate + 1);
        VsRespiratoryRate testVsRespiratoryRate = vsRespiratoryRateList.get(vsRespiratoryRateList.size() - 1);
        assertThat(testVsRespiratoryRate.getUserid()).isEqualTo(DEFAULT_USERID);
        assertThat(testVsRespiratoryRate.getBpm()).isEqualTo(DEFAULT_BPM);
        assertThat(testVsRespiratoryRate.getMeasurmentdate()).isEqualTo(DEFAULT_MEASURMENTDATE);
    }

    @Test
    public void createVsRespiratoryRateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vsRespiratoryRateRepository.findAll().size();

        // Create the VsRespiratoryRate with an existing ID
        vsRespiratoryRate.setId("existing_id");
        VsRespiratoryRateDTO vsRespiratoryRateDTO = vsRespiratoryRateMapper.toDto(vsRespiratoryRate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVsRespiratoryRateMockMvc.perform(post("/api/vs-respiratory-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsRespiratoryRateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VsRespiratoryRate in the database
        List<VsRespiratoryRate> vsRespiratoryRateList = vsRespiratoryRateRepository.findAll();
        assertThat(vsRespiratoryRateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkBpmIsRequired() throws Exception {
        int databaseSizeBeforeTest = vsRespiratoryRateRepository.findAll().size();
        // set the field null
        vsRespiratoryRate.setBpm(null);

        // Create the VsRespiratoryRate, which fails.
        VsRespiratoryRateDTO vsRespiratoryRateDTO = vsRespiratoryRateMapper.toDto(vsRespiratoryRate);

        restVsRespiratoryRateMockMvc.perform(post("/api/vs-respiratory-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsRespiratoryRateDTO)))
            .andExpect(status().isBadRequest());

        List<VsRespiratoryRate> vsRespiratoryRateList = vsRespiratoryRateRepository.findAll();
        assertThat(vsRespiratoryRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkMeasurmentdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vsRespiratoryRateRepository.findAll().size();
        // set the field null
        vsRespiratoryRate.setMeasurmentdate(null);

        // Create the VsRespiratoryRate, which fails.
        VsRespiratoryRateDTO vsRespiratoryRateDTO = vsRespiratoryRateMapper.toDto(vsRespiratoryRate);

        restVsRespiratoryRateMockMvc.perform(post("/api/vs-respiratory-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsRespiratoryRateDTO)))
            .andExpect(status().isBadRequest());

        List<VsRespiratoryRate> vsRespiratoryRateList = vsRespiratoryRateRepository.findAll();
        assertThat(vsRespiratoryRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllVsRespiratoryRates() throws Exception {
        // Initialize the database
        vsRespiratoryRateRepository.save(vsRespiratoryRate);

        // Get all the vsRespiratoryRateList
        restVsRespiratoryRateMockMvc.perform(get("/api/vs-respiratory-rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vsRespiratoryRate.getId())))
            .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID.toString())))
            .andExpect(jsonPath("$.[*].bpm").value(hasItem(DEFAULT_BPM.doubleValue())))
            .andExpect(jsonPath("$.[*].measurmentdate").value(hasItem(DEFAULT_MEASURMENTDATE.toString())));
    }

    @Test
    public void getVsRespiratoryRate() throws Exception {
        // Initialize the database
        vsRespiratoryRateRepository.save(vsRespiratoryRate);

        // Get the vsRespiratoryRate
        restVsRespiratoryRateMockMvc.perform(get("/api/vs-respiratory-rates/{id}", vsRespiratoryRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vsRespiratoryRate.getId()))
            .andExpect(jsonPath("$.userid").value(DEFAULT_USERID.toString()))
            .andExpect(jsonPath("$.bpm").value(DEFAULT_BPM.doubleValue()))
            .andExpect(jsonPath("$.measurmentdate").value(DEFAULT_MEASURMENTDATE.toString()));
    }

    @Test
    public void getNonExistingVsRespiratoryRate() throws Exception {
        // Get the vsRespiratoryRate
        restVsRespiratoryRateMockMvc.perform(get("/api/vs-respiratory-rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateVsRespiratoryRate() throws Exception {
        // Initialize the database
        vsRespiratoryRateRepository.save(vsRespiratoryRate);
        int databaseSizeBeforeUpdate = vsRespiratoryRateRepository.findAll().size();

        // Update the vsRespiratoryRate
        VsRespiratoryRate updatedVsRespiratoryRate = vsRespiratoryRateRepository.findOne(vsRespiratoryRate.getId());
        updatedVsRespiratoryRate
            .userid(UPDATED_USERID)
            .bpm(UPDATED_BPM)
            .measurmentdate(UPDATED_MEASURMENTDATE);
        VsRespiratoryRateDTO vsRespiratoryRateDTO = vsRespiratoryRateMapper.toDto(updatedVsRespiratoryRate);

        restVsRespiratoryRateMockMvc.perform(put("/api/vs-respiratory-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsRespiratoryRateDTO)))
            .andExpect(status().isOk());

        // Validate the VsRespiratoryRate in the database
        List<VsRespiratoryRate> vsRespiratoryRateList = vsRespiratoryRateRepository.findAll();
        assertThat(vsRespiratoryRateList).hasSize(databaseSizeBeforeUpdate);
        VsRespiratoryRate testVsRespiratoryRate = vsRespiratoryRateList.get(vsRespiratoryRateList.size() - 1);
        assertThat(testVsRespiratoryRate.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testVsRespiratoryRate.getBpm()).isEqualTo(UPDATED_BPM);
        assertThat(testVsRespiratoryRate.getMeasurmentdate()).isEqualTo(UPDATED_MEASURMENTDATE);
    }

    @Test
    public void updateNonExistingVsRespiratoryRate() throws Exception {
        int databaseSizeBeforeUpdate = vsRespiratoryRateRepository.findAll().size();

        // Create the VsRespiratoryRate
        VsRespiratoryRateDTO vsRespiratoryRateDTO = vsRespiratoryRateMapper.toDto(vsRespiratoryRate);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVsRespiratoryRateMockMvc.perform(put("/api/vs-respiratory-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsRespiratoryRateDTO)))
            .andExpect(status().isCreated());

        // Validate the VsRespiratoryRate in the database
        List<VsRespiratoryRate> vsRespiratoryRateList = vsRespiratoryRateRepository.findAll();
        assertThat(vsRespiratoryRateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteVsRespiratoryRate() throws Exception {
        // Initialize the database
        vsRespiratoryRateRepository.save(vsRespiratoryRate);
        int databaseSizeBeforeDelete = vsRespiratoryRateRepository.findAll().size();

        // Get the vsRespiratoryRate
        restVsRespiratoryRateMockMvc.perform(delete("/api/vs-respiratory-rates/{id}", vsRespiratoryRate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VsRespiratoryRate> vsRespiratoryRateList = vsRespiratoryRateRepository.findAll();
        assertThat(vsRespiratoryRateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VsRespiratoryRate.class);
        VsRespiratoryRate vsRespiratoryRate1 = new VsRespiratoryRate();
        vsRespiratoryRate1.setId("id1");
        VsRespiratoryRate vsRespiratoryRate2 = new VsRespiratoryRate();
        vsRespiratoryRate2.setId(vsRespiratoryRate1.getId());
        assertThat(vsRespiratoryRate1).isEqualTo(vsRespiratoryRate2);
        vsRespiratoryRate2.setId("id2");
        assertThat(vsRespiratoryRate1).isNotEqualTo(vsRespiratoryRate2);
        vsRespiratoryRate1.setId(null);
        assertThat(vsRespiratoryRate1).isNotEqualTo(vsRespiratoryRate2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VsRespiratoryRateDTO.class);
        VsRespiratoryRateDTO vsRespiratoryRateDTO1 = new VsRespiratoryRateDTO();
        vsRespiratoryRateDTO1.setId("id1");
        VsRespiratoryRateDTO vsRespiratoryRateDTO2 = new VsRespiratoryRateDTO();
        assertThat(vsRespiratoryRateDTO1).isNotEqualTo(vsRespiratoryRateDTO2);
        vsRespiratoryRateDTO2.setId(vsRespiratoryRateDTO1.getId());
        assertThat(vsRespiratoryRateDTO1).isEqualTo(vsRespiratoryRateDTO2);
        vsRespiratoryRateDTO2.setId("id2");
        assertThat(vsRespiratoryRateDTO1).isNotEqualTo(vsRespiratoryRateDTO2);
        vsRespiratoryRateDTO1.setId(null);
        assertThat(vsRespiratoryRateDTO1).isNotEqualTo(vsRespiratoryRateDTO2);
    }
}
