package com.sirtts.web.rest;

import com.sirtts.HcPortalApp;

import com.sirtts.domain.VsSpo2;
import com.sirtts.repository.VsSpo2Repository;
import com.sirtts.service.VsSpo2Service;
import com.sirtts.service.dto.VsSpo2DTO;
import com.sirtts.service.mapper.VsSpo2Mapper;
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
 * Test class for the VsSpo2Resource REST controller.
 *
 * @see VsSpo2Resource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcPortalApp.class)
public class VsSpo2ResourceIntTest {

    private static final String DEFAULT_USERID = "AAAAAAAAAA";
    private static final String UPDATED_USERID = "BBBBBBBBBB";

    private static final Double DEFAULT_PERCENT = 1D;
    private static final Double UPDATED_PERCENT = 2D;

    private static final LocalDateTime DEFAULT_MEASURMENTDATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);
    private static final LocalDateTime UPDATED_MEASURMENTDATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);

    @Autowired
    private VsSpo2Repository vsSpo2Repository;

    @Autowired
    private VsSpo2Mapper vsSpo2Mapper;

    @Autowired
    private VsSpo2Service vsSpo2Service;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restVsSpo2MockMvc;

    private VsSpo2 vsSpo2;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VsSpo2Resource vsSpo2Resource = new VsSpo2Resource(vsSpo2Service);
        this.restVsSpo2MockMvc = MockMvcBuilders.standaloneSetup(vsSpo2Resource)
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
    public static VsSpo2 createEntity() {
        VsSpo2 vsSpo2 = new VsSpo2()
            .userid(DEFAULT_USERID)
            .percent(DEFAULT_PERCENT)
            .measurmentdate(DEFAULT_MEASURMENTDATE);
        return vsSpo2;
    }

    @Before
    public void initTest() {
        vsSpo2Repository.deleteAll();
        vsSpo2 = createEntity();
    }

    @Test
    public void createVsSpo2() throws Exception {
        int databaseSizeBeforeCreate = vsSpo2Repository.findAll().size();

        // Create the VsSpo2
        VsSpo2DTO vsSpo2DTO = vsSpo2Mapper.toDto(vsSpo2);
        restVsSpo2MockMvc.perform(post("/api/vs-spo-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsSpo2DTO)))
            .andExpect(status().isCreated());

        // Validate the VsSpo2 in the database
        List<VsSpo2> vsSpo2List = vsSpo2Repository.findAll();
        assertThat(vsSpo2List).hasSize(databaseSizeBeforeCreate + 1);
        VsSpo2 testVsSpo2 = vsSpo2List.get(vsSpo2List.size() - 1);
        assertThat(testVsSpo2.getUserid()).isEqualTo(DEFAULT_USERID);
        assertThat(testVsSpo2.getPercent()).isEqualTo(DEFAULT_PERCENT);
        assertThat(testVsSpo2.getMeasurmentdate()).isEqualTo(DEFAULT_MEASURMENTDATE);
    }

    @Test
    public void createVsSpo2WithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vsSpo2Repository.findAll().size();

        // Create the VsSpo2 with an existing ID
        vsSpo2.setId("existing_id");
        VsSpo2DTO vsSpo2DTO = vsSpo2Mapper.toDto(vsSpo2);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVsSpo2MockMvc.perform(post("/api/vs-spo-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsSpo2DTO)))
            .andExpect(status().isBadRequest());

        // Validate the VsSpo2 in the database
        List<VsSpo2> vsSpo2List = vsSpo2Repository.findAll();
        assertThat(vsSpo2List).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkPercentIsRequired() throws Exception {
        int databaseSizeBeforeTest = vsSpo2Repository.findAll().size();
        // set the field null
        vsSpo2.setPercent(null);

        // Create the VsSpo2, which fails.
        VsSpo2DTO vsSpo2DTO = vsSpo2Mapper.toDto(vsSpo2);

        restVsSpo2MockMvc.perform(post("/api/vs-spo-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsSpo2DTO)))
            .andExpect(status().isBadRequest());

        List<VsSpo2> vsSpo2List = vsSpo2Repository.findAll();
        assertThat(vsSpo2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkMeasurmentdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = vsSpo2Repository.findAll().size();
        // set the field null
        vsSpo2.setMeasurmentdate(null);

        // Create the VsSpo2, which fails.
        VsSpo2DTO vsSpo2DTO = vsSpo2Mapper.toDto(vsSpo2);

        restVsSpo2MockMvc.perform(post("/api/vs-spo-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsSpo2DTO)))
            .andExpect(status().isBadRequest());

        List<VsSpo2> vsSpo2List = vsSpo2Repository.findAll();
        assertThat(vsSpo2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllVsSpo2S() throws Exception {
        // Initialize the database
        vsSpo2Repository.save(vsSpo2);

        // Get all the vsSpo2List
        restVsSpo2MockMvc.perform(get("/api/vs-spo-2-s?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vsSpo2.getId())))
            .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID.toString())))
            .andExpect(jsonPath("$.[*].percent").value(hasItem(DEFAULT_PERCENT.doubleValue())))
            .andExpect(jsonPath("$.[*].measurmentdate").value(hasItem(DEFAULT_MEASURMENTDATE.toString())));
    }

    @Test
    public void getVsSpo2() throws Exception {
        // Initialize the database
        vsSpo2Repository.save(vsSpo2);

        // Get the vsSpo2
        restVsSpo2MockMvc.perform(get("/api/vs-spo-2-s/{id}", vsSpo2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vsSpo2.getId()))
            .andExpect(jsonPath("$.userid").value(DEFAULT_USERID.toString()))
            .andExpect(jsonPath("$.percent").value(DEFAULT_PERCENT.doubleValue()))
            .andExpect(jsonPath("$.measurmentdate").value(DEFAULT_MEASURMENTDATE.toString()));
    }

    @Test
    public void getNonExistingVsSpo2() throws Exception {
        // Get the vsSpo2
        restVsSpo2MockMvc.perform(get("/api/vs-spo-2-s/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateVsSpo2() throws Exception {
        // Initialize the database
        vsSpo2Repository.save(vsSpo2);
        int databaseSizeBeforeUpdate = vsSpo2Repository.findAll().size();

        // Update the vsSpo2
        VsSpo2 updatedVsSpo2 = vsSpo2Repository.findOne(vsSpo2.getId());
        updatedVsSpo2
            .userid(UPDATED_USERID)
            .percent(UPDATED_PERCENT)
            .measurmentdate(UPDATED_MEASURMENTDATE);
        VsSpo2DTO vsSpo2DTO = vsSpo2Mapper.toDto(updatedVsSpo2);

        restVsSpo2MockMvc.perform(put("/api/vs-spo-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsSpo2DTO)))
            .andExpect(status().isOk());

        // Validate the VsSpo2 in the database
        List<VsSpo2> vsSpo2List = vsSpo2Repository.findAll();
        assertThat(vsSpo2List).hasSize(databaseSizeBeforeUpdate);
        VsSpo2 testVsSpo2 = vsSpo2List.get(vsSpo2List.size() - 1);
        assertThat(testVsSpo2.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testVsSpo2.getPercent()).isEqualTo(UPDATED_PERCENT);
        assertThat(testVsSpo2.getMeasurmentdate()).isEqualTo(UPDATED_MEASURMENTDATE);
    }

    @Test
    public void updateNonExistingVsSpo2() throws Exception {
        int databaseSizeBeforeUpdate = vsSpo2Repository.findAll().size();

        // Create the VsSpo2
        VsSpo2DTO vsSpo2DTO = vsSpo2Mapper.toDto(vsSpo2);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVsSpo2MockMvc.perform(put("/api/vs-spo-2-s")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vsSpo2DTO)))
            .andExpect(status().isCreated());

        // Validate the VsSpo2 in the database
        List<VsSpo2> vsSpo2List = vsSpo2Repository.findAll();
        assertThat(vsSpo2List).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteVsSpo2() throws Exception {
        // Initialize the database
        vsSpo2Repository.save(vsSpo2);
        int databaseSizeBeforeDelete = vsSpo2Repository.findAll().size();

        // Get the vsSpo2
        restVsSpo2MockMvc.perform(delete("/api/vs-spo-2-s/{id}", vsSpo2.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VsSpo2> vsSpo2List = vsSpo2Repository.findAll();
        assertThat(vsSpo2List).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VsSpo2.class);
        VsSpo2 vsSpo21 = new VsSpo2();
        vsSpo21.setId("id1");
        VsSpo2 vsSpo22 = new VsSpo2();
        vsSpo22.setId(vsSpo21.getId());
        assertThat(vsSpo21).isEqualTo(vsSpo22);
        vsSpo22.setId("id2");
        assertThat(vsSpo21).isNotEqualTo(vsSpo22);
        vsSpo21.setId(null);
        assertThat(vsSpo21).isNotEqualTo(vsSpo22);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VsSpo2DTO.class);
        VsSpo2DTO vsSpo2DTO1 = new VsSpo2DTO();
        vsSpo2DTO1.setId("id1");
        VsSpo2DTO vsSpo2DTO2 = new VsSpo2DTO();
        assertThat(vsSpo2DTO1).isNotEqualTo(vsSpo2DTO2);
        vsSpo2DTO2.setId(vsSpo2DTO1.getId());
        assertThat(vsSpo2DTO1).isEqualTo(vsSpo2DTO2);
        vsSpo2DTO2.setId("id2");
        assertThat(vsSpo2DTO1).isNotEqualTo(vsSpo2DTO2);
        vsSpo2DTO1.setId(null);
        assertThat(vsSpo2DTO1).isNotEqualTo(vsSpo2DTO2);
    }
}
