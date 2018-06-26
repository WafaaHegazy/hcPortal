package com.sirtts.web.rest;

import com.sirtts.HcPortalApp;

import com.sirtts.domain.DentistNextVisit;
import com.sirtts.repository.DentistNextVisitRepository;
import com.sirtts.service.DentistNextVisitService;
import com.sirtts.service.dto.DentistNextVisitDTO;
import com.sirtts.service.mapper.DentistNextVisitMapper;
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
 * Test class for the DentistNextVisitResource REST controller.
 *
 * @see DentistNextVisitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcPortalApp.class)
public class DentistNextVisitResourceIntTest {

    private static final String DEFAULT_USERID = "AAAAAAAAAA";
    private static final String UPDATED_USERID = "BBBBBBBBBB";

    private static final LocalDateTime DEFAULT_MEASURMENTDATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);
    private static final LocalDateTime UPDATED_MEASURMENTDATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);

    @Autowired
    private DentistNextVisitRepository dentistNextVisitRepository;

    @Autowired
    private DentistNextVisitMapper dentistNextVisitMapper;

    @Autowired
    private DentistNextVisitService dentistNextVisitService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restDentistNextVisitMockMvc;

    private DentistNextVisit dentistNextVisit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DentistNextVisitResource dentistNextVisitResource = new DentistNextVisitResource(dentistNextVisitService);
        this.restDentistNextVisitMockMvc = MockMvcBuilders.standaloneSetup(dentistNextVisitResource)
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
    public static DentistNextVisit createEntity() {
        DentistNextVisit dentistNextVisit = new DentistNextVisit()
            .userid(DEFAULT_USERID)
            .measurmentdate(DEFAULT_MEASURMENTDATE);
        return dentistNextVisit;
    }

    @Before
    public void initTest() {
        dentistNextVisitRepository.deleteAll();
        dentistNextVisit = createEntity();
    }

    @Test
    public void createDentistNextVisit() throws Exception {
        int databaseSizeBeforeCreate = dentistNextVisitRepository.findAll().size();

        // Create the DentistNextVisit
        DentistNextVisitDTO dentistNextVisitDTO = dentistNextVisitMapper.toDto(dentistNextVisit);
        restDentistNextVisitMockMvc.perform(post("/api/dentist-next-visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dentistNextVisitDTO)))
            .andExpect(status().isCreated());

        // Validate the DentistNextVisit in the database
        List<DentistNextVisit> dentistNextVisitList = dentistNextVisitRepository.findAll();
        assertThat(dentistNextVisitList).hasSize(databaseSizeBeforeCreate + 1);
        DentistNextVisit testDentistNextVisit = dentistNextVisitList.get(dentistNextVisitList.size() - 1);
        assertThat(testDentistNextVisit.getUserid()).isEqualTo(DEFAULT_USERID);
        assertThat(testDentistNextVisit.getMeasurmentdate()).isEqualTo(DEFAULT_MEASURMENTDATE);
    }

    @Test
    public void createDentistNextVisitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dentistNextVisitRepository.findAll().size();

        // Create the DentistNextVisit with an existing ID
        dentistNextVisit.setId("existing_id");
        DentistNextVisitDTO dentistNextVisitDTO = dentistNextVisitMapper.toDto(dentistNextVisit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDentistNextVisitMockMvc.perform(post("/api/dentist-next-visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dentistNextVisitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DentistNextVisit in the database
        List<DentistNextVisit> dentistNextVisitList = dentistNextVisitRepository.findAll();
        assertThat(dentistNextVisitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkMeasurmentdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dentistNextVisitRepository.findAll().size();
        // set the field null
        dentistNextVisit.setMeasurmentdate(null);

        // Create the DentistNextVisit, which fails.
        DentistNextVisitDTO dentistNextVisitDTO = dentistNextVisitMapper.toDto(dentistNextVisit);

        restDentistNextVisitMockMvc.perform(post("/api/dentist-next-visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dentistNextVisitDTO)))
            .andExpect(status().isBadRequest());

        List<DentistNextVisit> dentistNextVisitList = dentistNextVisitRepository.findAll();
        assertThat(dentistNextVisitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllDentistNextVisits() throws Exception {
        // Initialize the database
        dentistNextVisitRepository.save(dentistNextVisit);

        // Get all the dentistNextVisitList
        restDentistNextVisitMockMvc.perform(get("/api/dentist-next-visits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dentistNextVisit.getId())))
            .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID.toString())))
            .andExpect(jsonPath("$.[*].measurmentdate").value(hasItem(DEFAULT_MEASURMENTDATE.toString())));
    }

    @Test
    public void getDentistNextVisit() throws Exception {
        // Initialize the database
        dentistNextVisitRepository.save(dentistNextVisit);

        // Get the dentistNextVisit
        restDentistNextVisitMockMvc.perform(get("/api/dentist-next-visits/{id}", dentistNextVisit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dentistNextVisit.getId()))
            .andExpect(jsonPath("$.userid").value(DEFAULT_USERID.toString()))
            .andExpect(jsonPath("$.measurmentdate").value(DEFAULT_MEASURMENTDATE.toString()));
    }

    @Test
    public void getNonExistingDentistNextVisit() throws Exception {
        // Get the dentistNextVisit
        restDentistNextVisitMockMvc.perform(get("/api/dentist-next-visits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDentistNextVisit() throws Exception {
        // Initialize the database
        dentistNextVisitRepository.save(dentistNextVisit);
        int databaseSizeBeforeUpdate = dentistNextVisitRepository.findAll().size();

        // Update the dentistNextVisit
        DentistNextVisit updatedDentistNextVisit = dentistNextVisitRepository.findOne(dentistNextVisit.getId());
        updatedDentistNextVisit
            .userid(UPDATED_USERID)
            .measurmentdate(UPDATED_MEASURMENTDATE);
        DentistNextVisitDTO dentistNextVisitDTO = dentistNextVisitMapper.toDto(updatedDentistNextVisit);

        restDentistNextVisitMockMvc.perform(put("/api/dentist-next-visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dentistNextVisitDTO)))
            .andExpect(status().isOk());

        // Validate the DentistNextVisit in the database
        List<DentistNextVisit> dentistNextVisitList = dentistNextVisitRepository.findAll();
        assertThat(dentistNextVisitList).hasSize(databaseSizeBeforeUpdate);
        DentistNextVisit testDentistNextVisit = dentistNextVisitList.get(dentistNextVisitList.size() - 1);
        assertThat(testDentistNextVisit.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testDentistNextVisit.getMeasurmentdate()).isEqualTo(UPDATED_MEASURMENTDATE);
    }

    @Test
    public void updateNonExistingDentistNextVisit() throws Exception {
        int databaseSizeBeforeUpdate = dentistNextVisitRepository.findAll().size();

        // Create the DentistNextVisit
        DentistNextVisitDTO dentistNextVisitDTO = dentistNextVisitMapper.toDto(dentistNextVisit);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDentistNextVisitMockMvc.perform(put("/api/dentist-next-visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dentistNextVisitDTO)))
            .andExpect(status().isCreated());

        // Validate the DentistNextVisit in the database
        List<DentistNextVisit> dentistNextVisitList = dentistNextVisitRepository.findAll();
        assertThat(dentistNextVisitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteDentistNextVisit() throws Exception {
        // Initialize the database
        dentistNextVisitRepository.save(dentistNextVisit);
        int databaseSizeBeforeDelete = dentistNextVisitRepository.findAll().size();

        // Get the dentistNextVisit
        restDentistNextVisitMockMvc.perform(delete("/api/dentist-next-visits/{id}", dentistNextVisit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DentistNextVisit> dentistNextVisitList = dentistNextVisitRepository.findAll();
        assertThat(dentistNextVisitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DentistNextVisit.class);
        DentistNextVisit dentistNextVisit1 = new DentistNextVisit();
        dentistNextVisit1.setId("id1");
        DentistNextVisit dentistNextVisit2 = new DentistNextVisit();
        dentistNextVisit2.setId(dentistNextVisit1.getId());
        assertThat(dentistNextVisit1).isEqualTo(dentistNextVisit2);
        dentistNextVisit2.setId("id2");
        assertThat(dentistNextVisit1).isNotEqualTo(dentistNextVisit2);
        dentistNextVisit1.setId(null);
        assertThat(dentistNextVisit1).isNotEqualTo(dentistNextVisit2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DentistNextVisitDTO.class);
        DentistNextVisitDTO dentistNextVisitDTO1 = new DentistNextVisitDTO();
        dentistNextVisitDTO1.setId("id1");
        DentistNextVisitDTO dentistNextVisitDTO2 = new DentistNextVisitDTO();
        assertThat(dentistNextVisitDTO1).isNotEqualTo(dentistNextVisitDTO2);
        dentistNextVisitDTO2.setId(dentistNextVisitDTO1.getId());
        assertThat(dentistNextVisitDTO1).isEqualTo(dentistNextVisitDTO2);
        dentistNextVisitDTO2.setId("id2");
        assertThat(dentistNextVisitDTO1).isNotEqualTo(dentistNextVisitDTO2);
        dentistNextVisitDTO1.setId(null);
        assertThat(dentistNextVisitDTO1).isNotEqualTo(dentistNextVisitDTO2);
    }
}
