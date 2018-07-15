package com.sirtts.web.rest;

import com.sirtts.HcPortalApp;

import com.sirtts.domain.VsHeartRate;
import com.sirtts.repository.VsHeartRateRepository;
import com.sirtts.service.VsHeartRateService;
import com.sirtts.service.dto.VsHeartRateDTO;
import com.sirtts.service.mapper.VsHeartRateMapper;
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

import java.time.ZoneOffset;
import java.time.LocalDateTime;
import java.util.List;

import static com.sirtts.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VsHeartRateResource REST controller.
 *
 * @see VsHeartRateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcPortalApp.class)
public class VsHeartRateResourceIntTest {

    private static final String DEFAULT_USERID = "AAAAAAAAAA";
    private static final String UPDATED_USERID = "BBBBBBBBBB";

    private static final Double DEFAULT_BPM = 1D;
    private static final Double UPDATED_BPM = 2D;

    private static final LocalDateTime DEFAULT_MEASURMENTDATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);
    private static final LocalDateTime UPDATED_MEASURMENTDATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);

    @Autowired
    private VsHeartRateRepository vsHeartRateRepository;

    @Autowired
    private VsHeartRateMapper vsHeartRateMapper;

    @Autowired
    private VsHeartRateService vsHeartRateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restVsHeartRateMockMvc;

    private VsHeartRate vsHeartRate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VsHeartRateResource vsHeartRateResource = new VsHeartRateResource(vsHeartRateService);
        this.restVsHeartRateMockMvc = MockMvcBuilders.standaloneSetup(vsHeartRateResource)
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
    public static VsHeartRate createEntity() {
        VsHeartRate vsHeartRate = new VsHeartRate()
            .userid(DEFAULT_USERID)
            .bpm(DEFAULT_BPM)
            .measurmentdate(DEFAULT_MEASURMENTDATE);
        return vsHeartRate;
    }

    @Before
    public void initTest() {
        vsHeartRateRepository.deleteAll();
        vsHeartRate = createEntity();
    }

    @Test
    public void createVsHeartRate() throws Exception {
        int databaseSizeBeforeCreate = vsHeartRateRepository.findAll().size();

        // Create the VsHeartRate
        VsHeartRateDTO vsHeartRateDTO = vsHeartRateMapper.toDto(vsHeartRate);
        restVsHeartRateMockMvc.perform(post("/api/vs-heart-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsHeartRateDTO)))
            .andExpect(status().isCreated());

        // Validate the VsHeartRate in the database
        List<VsHeartRate> vsHeartRateList = vsHeartRateRepository.findAll();
        assertThat(vsHeartRateList).hasSize(databaseSizeBeforeCreate + 1);
        VsHeartRate testVsHeartRate = vsHeartRateList.get(vsHeartRateList.size() - 1);
        assertThat(testVsHeartRate.getUserid()).isEqualTo(DEFAULT_USERID);
        assertThat(testVsHeartRate.getBpm()).isEqualTo(DEFAULT_BPM);
        assertThat(testVsHeartRate.getMeasurmentdate()).isEqualTo(DEFAULT_MEASURMENTDATE);
    }

    @Test
    public void createVsHeartRateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vsHeartRateRepository.findAll().size();

        // Create the VsHeartRate with an existing ID
        vsHeartRate.setId("existing_id");
        VsHeartRateDTO vsHeartRateDTO = vsHeartRateMapper.toDto(vsHeartRate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVsHeartRateMockMvc.perform(post("/api/vs-heart-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsHeartRateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VsHeartRate in the database
        List<VsHeartRate> vsHeartRateList = vsHeartRateRepository.findAll();
        assertThat(vsHeartRateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkBpmIsRequired() throws Exception {
        int databaseSizeBeforeTest = vsHeartRateRepository.findAll().size();
        // set the field null
        vsHeartRate.setBpm(null);

        // Create the VsHeartRate, which fails.
        VsHeartRateDTO vsHeartRateDTO = vsHeartRateMapper.toDto(vsHeartRate);

        restVsHeartRateMockMvc.perform(post("/api/vs-heart-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsHeartRateDTO)))
            .andExpect(status().isBadRequest());

        List<VsHeartRate> vsHeartRateList = vsHeartRateRepository.findAll();
        assertThat(vsHeartRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkMeasurmentdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vsHeartRateRepository.findAll().size();
        // set the field null
        vsHeartRate.setMeasurmentdate(null);

        // Create the VsHeartRate, which fails.
        VsHeartRateDTO vsHeartRateDTO = vsHeartRateMapper.toDto(vsHeartRate);

        restVsHeartRateMockMvc.perform(post("/api/vs-heart-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsHeartRateDTO)))
            .andExpect(status().isBadRequest());

        List<VsHeartRate> vsHeartRateList = vsHeartRateRepository.findAll();
        assertThat(vsHeartRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllVsHeartRates() throws Exception {
        // Initialize the database
        vsHeartRateRepository.save(vsHeartRate);

        // Get all the vsHeartRateList
        restVsHeartRateMockMvc.perform(get("/api/vs-heart-rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vsHeartRate.getId())))
            .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID.toString())))
            .andExpect(jsonPath("$.[*].bpm").value(hasItem(DEFAULT_BPM.doubleValue())))
            .andExpect(jsonPath("$.[*].measurmentdate").value(hasItem(DEFAULT_MEASURMENTDATE.toString())));
    }

    @Test
    public void getVsHeartRate() throws Exception {
        // Initialize the database
        vsHeartRateRepository.save(vsHeartRate);

        // Get the vsHeartRate
        restVsHeartRateMockMvc.perform(get("/api/vs-heart-rates/{id}", vsHeartRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vsHeartRate.getId()))
            .andExpect(jsonPath("$.userid").value(DEFAULT_USERID.toString()))
            .andExpect(jsonPath("$.bpm").value(DEFAULT_BPM.doubleValue()))
            .andExpect(jsonPath("$.measurmentdate").value(DEFAULT_MEASURMENTDATE.toString()));
    }

    @Test
    public void getNonExistingVsHeartRate() throws Exception {
        // Get the vsHeartRate
        restVsHeartRateMockMvc.perform(get("/api/vs-heart-rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateVsHeartRate() throws Exception {
        // Initialize the database
        vsHeartRateRepository.save(vsHeartRate);
        int databaseSizeBeforeUpdate = vsHeartRateRepository.findAll().size();

        // Update the vsHeartRate
        VsHeartRate updatedVsHeartRate = vsHeartRateRepository.findOne(vsHeartRate.getId());
        updatedVsHeartRate
            .userid(UPDATED_USERID)
            .bpm(UPDATED_BPM)
            .measurmentdate(UPDATED_MEASURMENTDATE);
        VsHeartRateDTO vsHeartRateDTO = vsHeartRateMapper.toDto(updatedVsHeartRate);

        restVsHeartRateMockMvc.perform(put("/api/vs-heart-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsHeartRateDTO)))
            .andExpect(status().isOk());

        // Validate the VsHeartRate in the database
        List<VsHeartRate> vsHeartRateList = vsHeartRateRepository.findAll();
        assertThat(vsHeartRateList).hasSize(databaseSizeBeforeUpdate);
        VsHeartRate testVsHeartRate = vsHeartRateList.get(vsHeartRateList.size() - 1);
        assertThat(testVsHeartRate.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testVsHeartRate.getBpm()).isEqualTo(UPDATED_BPM);
        assertThat(testVsHeartRate.getMeasurmentdate()).isEqualTo(UPDATED_MEASURMENTDATE);
    }

    @Test
    public void updateNonExistingVsHeartRate() throws Exception {
        int databaseSizeBeforeUpdate = vsHeartRateRepository.findAll().size();

        // Create the VsHeartRate
        VsHeartRateDTO vsHeartRateDTO = vsHeartRateMapper.toDto(vsHeartRate);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVsHeartRateMockMvc.perform(put("/api/vs-heart-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsHeartRateDTO)))
            .andExpect(status().isCreated());

        // Validate the VsHeartRate in the database
        List<VsHeartRate> vsHeartRateList = vsHeartRateRepository.findAll();
        assertThat(vsHeartRateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteVsHeartRate() throws Exception {
        // Initialize the database
        vsHeartRateRepository.save(vsHeartRate);
        int databaseSizeBeforeDelete = vsHeartRateRepository.findAll().size();

        // Get the vsHeartRate
        restVsHeartRateMockMvc.perform(delete("/api/vs-heart-rates/{id}", vsHeartRate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VsHeartRate> vsHeartRateList = vsHeartRateRepository.findAll();
        assertThat(vsHeartRateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VsHeartRate.class);
        VsHeartRate vsHeartRate1 = new VsHeartRate();
        vsHeartRate1.setId("id1");
        VsHeartRate vsHeartRate2 = new VsHeartRate();
        vsHeartRate2.setId(vsHeartRate1.getId());
        assertThat(vsHeartRate1).isEqualTo(vsHeartRate2);
        vsHeartRate2.setId("id2");
        assertThat(vsHeartRate1).isNotEqualTo(vsHeartRate2);
        vsHeartRate1.setId(null);
        assertThat(vsHeartRate1).isNotEqualTo(vsHeartRate2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VsHeartRateDTO.class);
        VsHeartRateDTO vsHeartRateDTO1 = new VsHeartRateDTO();
        vsHeartRateDTO1.setId("id1");
        VsHeartRateDTO vsHeartRateDTO2 = new VsHeartRateDTO();
        assertThat(vsHeartRateDTO1).isNotEqualTo(vsHeartRateDTO2);
        vsHeartRateDTO2.setId(vsHeartRateDTO1.getId());
        assertThat(vsHeartRateDTO1).isEqualTo(vsHeartRateDTO2);
        vsHeartRateDTO2.setId("id2");
        assertThat(vsHeartRateDTO1).isNotEqualTo(vsHeartRateDTO2);
        vsHeartRateDTO1.setId(null);
        assertThat(vsHeartRateDTO1).isNotEqualTo(vsHeartRateDTO2);
    }
}
