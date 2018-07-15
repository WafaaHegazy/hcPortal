package com.sirtts.web.rest;

import com.sirtts.HcPortalApp;

import com.sirtts.domain.VsBodyTemperature;
import com.sirtts.repository.VsBodyTemperatureRepository;
import com.sirtts.service.VsBodyTemperatureService;
import com.sirtts.service.dto.VsBodyTemperatureDTO;
import com.sirtts.service.mapper.VsBodyTemperatureMapper;
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
 * Test class for the VsBodyTemperatureResource REST controller.
 *
 * @see VsBodyTemperatureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcPortalApp.class)
public class VsBodyTemperatureResourceIntTest {

    private static final String DEFAULT_USERID = "AAAAAAAAAA";
    private static final String UPDATED_USERID = "BBBBBBBBBB";

    private static final Double DEFAULT_CELSIUS = 1D;
    private static final Double UPDATED_CELSIUS = 2D;

    private static final LocalDateTime DEFAULT_MEASURMENTDATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);
    private static final LocalDateTime UPDATED_MEASURMENTDATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);

    @Autowired
    private VsBodyTemperatureRepository vsBodyTemperatureRepository;

    @Autowired
    private VsBodyTemperatureMapper vsBodyTemperatureMapper;

    @Autowired
    private VsBodyTemperatureService vsBodyTemperatureService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restVsBodyTemperatureMockMvc;

    private VsBodyTemperature vsBodyTemperature;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VsBodyTemperatureResource vsBodyTemperatureResource = new VsBodyTemperatureResource(vsBodyTemperatureService);
        this.restVsBodyTemperatureMockMvc = MockMvcBuilders.standaloneSetup(vsBodyTemperatureResource)
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
    public static VsBodyTemperature createEntity() {
        VsBodyTemperature vsBodyTemperature = new VsBodyTemperature()
            .userid(DEFAULT_USERID)
            .celsius(DEFAULT_CELSIUS)
            .measurmentdate(DEFAULT_MEASURMENTDATE);
        return vsBodyTemperature;
    }

    @Before
    public void initTest() {
        vsBodyTemperatureRepository.deleteAll();
        vsBodyTemperature = createEntity();
    }

    @Test
    public void createVsBodyTemperature() throws Exception {
        int databaseSizeBeforeCreate = vsBodyTemperatureRepository.findAll().size();

        // Create the VsBodyTemperature
        VsBodyTemperatureDTO vsBodyTemperatureDTO = vsBodyTemperatureMapper.toDto(vsBodyTemperature);
        restVsBodyTemperatureMockMvc.perform(post("/api/vs-body-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsBodyTemperatureDTO)))
            .andExpect(status().isCreated());

        // Validate the VsBodyTemperature in the database
        List<VsBodyTemperature> vsBodyTemperatureList = vsBodyTemperatureRepository.findAll();
        assertThat(vsBodyTemperatureList).hasSize(databaseSizeBeforeCreate + 1);
        VsBodyTemperature testVsBodyTemperature = vsBodyTemperatureList.get(vsBodyTemperatureList.size() - 1);
        assertThat(testVsBodyTemperature.getUserid()).isEqualTo(DEFAULT_USERID);
        assertThat(testVsBodyTemperature.getCelsius()).isEqualTo(DEFAULT_CELSIUS);
        assertThat(testVsBodyTemperature.getMeasurmentdate()).isEqualTo(DEFAULT_MEASURMENTDATE);
    }

    @Test
    public void createVsBodyTemperatureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vsBodyTemperatureRepository.findAll().size();

        // Create the VsBodyTemperature with an existing ID
        vsBodyTemperature.setId("existing_id");
        VsBodyTemperatureDTO vsBodyTemperatureDTO = vsBodyTemperatureMapper.toDto(vsBodyTemperature);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVsBodyTemperatureMockMvc.perform(post("/api/vs-body-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsBodyTemperatureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VsBodyTemperature in the database
        List<VsBodyTemperature> vsBodyTemperatureList = vsBodyTemperatureRepository.findAll();
        assertThat(vsBodyTemperatureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkCelsiusIsRequired() throws Exception {
        int databaseSizeBeforeTest = vsBodyTemperatureRepository.findAll().size();
        // set the field null
        vsBodyTemperature.setCelsius(null);

        // Create the VsBodyTemperature, which fails.
        VsBodyTemperatureDTO vsBodyTemperatureDTO = vsBodyTemperatureMapper.toDto(vsBodyTemperature);

        restVsBodyTemperatureMockMvc.perform(post("/api/vs-body-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsBodyTemperatureDTO)))
            .andExpect(status().isBadRequest());

        List<VsBodyTemperature> vsBodyTemperatureList = vsBodyTemperatureRepository.findAll();
        assertThat(vsBodyTemperatureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkMeasurmentdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vsBodyTemperatureRepository.findAll().size();
        // set the field null
        vsBodyTemperature.setMeasurmentdate(null);

        // Create the VsBodyTemperature, which fails.
        VsBodyTemperatureDTO vsBodyTemperatureDTO = vsBodyTemperatureMapper.toDto(vsBodyTemperature);

        restVsBodyTemperatureMockMvc.perform(post("/api/vs-body-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsBodyTemperatureDTO)))
            .andExpect(status().isBadRequest());

        List<VsBodyTemperature> vsBodyTemperatureList = vsBodyTemperatureRepository.findAll();
        assertThat(vsBodyTemperatureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllVsBodyTemperatures() throws Exception {
        // Initialize the database
        vsBodyTemperatureRepository.save(vsBodyTemperature);

        // Get all the vsBodyTemperatureList
        restVsBodyTemperatureMockMvc.perform(get("/api/vs-body-temperatures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vsBodyTemperature.getId())))
            .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID.toString())))
            .andExpect(jsonPath("$.[*].celsius").value(hasItem(DEFAULT_CELSIUS.doubleValue())))
            .andExpect(jsonPath("$.[*].measurmentdate").value(hasItem(DEFAULT_MEASURMENTDATE.toString())));
    }

    @Test
    public void getVsBodyTemperature() throws Exception {
        // Initialize the database
        vsBodyTemperatureRepository.save(vsBodyTemperature);

        // Get the vsBodyTemperature
        restVsBodyTemperatureMockMvc.perform(get("/api/vs-body-temperatures/{id}", vsBodyTemperature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vsBodyTemperature.getId()))
            .andExpect(jsonPath("$.userid").value(DEFAULT_USERID.toString()))
            .andExpect(jsonPath("$.celsius").value(DEFAULT_CELSIUS.doubleValue()))
            .andExpect(jsonPath("$.measurmentdate").value(DEFAULT_MEASURMENTDATE.toString()));
    }

    @Test
    public void getNonExistingVsBodyTemperature() throws Exception {
        // Get the vsBodyTemperature
        restVsBodyTemperatureMockMvc.perform(get("/api/vs-body-temperatures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateVsBodyTemperature() throws Exception {
        // Initialize the database
        vsBodyTemperatureRepository.save(vsBodyTemperature);
        int databaseSizeBeforeUpdate = vsBodyTemperatureRepository.findAll().size();

        // Update the vsBodyTemperature
        VsBodyTemperature updatedVsBodyTemperature = vsBodyTemperatureRepository.findOne(vsBodyTemperature.getId());
        updatedVsBodyTemperature
            .userid(UPDATED_USERID)
            .celsius(UPDATED_CELSIUS)
            .measurmentdate(UPDATED_MEASURMENTDATE);
        VsBodyTemperatureDTO vsBodyTemperatureDTO = vsBodyTemperatureMapper.toDto(updatedVsBodyTemperature);

        restVsBodyTemperatureMockMvc.perform(put("/api/vs-body-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsBodyTemperatureDTO)))
            .andExpect(status().isOk());

        // Validate the VsBodyTemperature in the database
        List<VsBodyTemperature> vsBodyTemperatureList = vsBodyTemperatureRepository.findAll();
        assertThat(vsBodyTemperatureList).hasSize(databaseSizeBeforeUpdate);
        VsBodyTemperature testVsBodyTemperature = vsBodyTemperatureList.get(vsBodyTemperatureList.size() - 1);
        assertThat(testVsBodyTemperature.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testVsBodyTemperature.getCelsius()).isEqualTo(UPDATED_CELSIUS);
        assertThat(testVsBodyTemperature.getMeasurmentdate()).isEqualTo(UPDATED_MEASURMENTDATE);
    }

    @Test
    public void updateNonExistingVsBodyTemperature() throws Exception {
        int databaseSizeBeforeUpdate = vsBodyTemperatureRepository.findAll().size();

        // Create the VsBodyTemperature
        VsBodyTemperatureDTO vsBodyTemperatureDTO = vsBodyTemperatureMapper.toDto(vsBodyTemperature);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVsBodyTemperatureMockMvc.perform(put("/api/vs-body-temperatures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsBodyTemperatureDTO)))
            .andExpect(status().isCreated());

        // Validate the VsBodyTemperature in the database
        List<VsBodyTemperature> vsBodyTemperatureList = vsBodyTemperatureRepository.findAll();
        assertThat(vsBodyTemperatureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteVsBodyTemperature() throws Exception {
        // Initialize the database
        vsBodyTemperatureRepository.save(vsBodyTemperature);
        int databaseSizeBeforeDelete = vsBodyTemperatureRepository.findAll().size();

        // Get the vsBodyTemperature
        restVsBodyTemperatureMockMvc.perform(delete("/api/vs-body-temperatures/{id}", vsBodyTemperature.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VsBodyTemperature> vsBodyTemperatureList = vsBodyTemperatureRepository.findAll();
        assertThat(vsBodyTemperatureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VsBodyTemperature.class);
        VsBodyTemperature vsBodyTemperature1 = new VsBodyTemperature();
        vsBodyTemperature1.setId("id1");
        VsBodyTemperature vsBodyTemperature2 = new VsBodyTemperature();
        vsBodyTemperature2.setId(vsBodyTemperature1.getId());
        assertThat(vsBodyTemperature1).isEqualTo(vsBodyTemperature2);
        vsBodyTemperature2.setId("id2");
        assertThat(vsBodyTemperature1).isNotEqualTo(vsBodyTemperature2);
        vsBodyTemperature1.setId(null);
        assertThat(vsBodyTemperature1).isNotEqualTo(vsBodyTemperature2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VsBodyTemperatureDTO.class);
        VsBodyTemperatureDTO vsBodyTemperatureDTO1 = new VsBodyTemperatureDTO();
        vsBodyTemperatureDTO1.setId("id1");
        VsBodyTemperatureDTO vsBodyTemperatureDTO2 = new VsBodyTemperatureDTO();
        assertThat(vsBodyTemperatureDTO1).isNotEqualTo(vsBodyTemperatureDTO2);
        vsBodyTemperatureDTO2.setId(vsBodyTemperatureDTO1.getId());
        assertThat(vsBodyTemperatureDTO1).isEqualTo(vsBodyTemperatureDTO2);
        vsBodyTemperatureDTO2.setId("id2");
        assertThat(vsBodyTemperatureDTO1).isNotEqualTo(vsBodyTemperatureDTO2);
        vsBodyTemperatureDTO1.setId(null);
        assertThat(vsBodyTemperatureDTO1).isNotEqualTo(vsBodyTemperatureDTO2);
    }
}
