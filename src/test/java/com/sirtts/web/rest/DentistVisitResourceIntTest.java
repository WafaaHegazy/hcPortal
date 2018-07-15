package com.sirtts.web.rest;

import com.sirtts.HcPortalApp;

import com.sirtts.domain.DentistVisit;
import com.sirtts.repository.DentistVisitRepository;
import com.sirtts.service.DentistNextVisitService;
import com.sirtts.service.DentistVisitService;
import com.sirtts.service.dto.DentistVisitDTO;
import com.sirtts.service.mapper.DentistVisitMapper;
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
 * Test class for the DentistVisitResource REST controller.
 *
 * @see DentistVisitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcPortalApp.class)
public class DentistVisitResourceIntTest {

    private static final String DEFAULT_USERID = "AAAAAAAAAA";
    private static final String UPDATED_USERID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TEETHCLEANING = false;
    private static final Boolean UPDATED_TEETHCLEANING = true;

    private static final Boolean DEFAULT_WHITENING = false;
    private static final Boolean UPDATED_WHITENING = true;

    private static final Boolean DEFAULT_RESTORATION = false;
    private static final Boolean UPDATED_RESTORATION = true;

    private static final Boolean DEFAULT_CROWNS = false;
    private static final Boolean UPDATED_CROWNS = true;

    private static final Boolean DEFAULT_BRIDGES = false;
    private static final Boolean UPDATED_BRIDGES = true;

    private static final Boolean DEFAULT_BRACES = false;
    private static final Boolean UPDATED_BRACES = true;

    private static final Boolean DEFAULT_ENDODONTICTHERAPY = false;
    private static final Boolean UPDATED_ENDODONTICTHERAPY = true;

    private static final Boolean DEFAULT_PERIODONTALTHERAPY = false;
    private static final Boolean UPDATED_PERIODONTALTHERAPY = true;

    private static final Boolean DEFAULT_EXTRACTION = false;
    private static final Boolean UPDATED_EXTRACTION = true;

    private static final Boolean DEFAULT_ORALSURGERY = false;
    private static final Boolean UPDATED_ORALSURGERY = true;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final LocalDateTime DEFAULT_MEASURMENTDATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);
    private static final LocalDateTime UPDATED_MEASURMENTDATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);

    @Autowired
    private DentistVisitRepository dentistVisitRepository;

    @Autowired
    private DentistVisitMapper dentistVisitMapper;

    @Autowired
    private DentistVisitService dentistVisitService;

    @Autowired
    private DentistNextVisitService dentistNextVisitService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restDentistVisitMockMvc;

    private DentistVisit dentistVisit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DentistVisitResource dentistVisitResource = new DentistVisitResource(dentistVisitService,dentistNextVisitService);
        this.restDentistVisitMockMvc = MockMvcBuilders.standaloneSetup(dentistVisitResource)
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
    public static DentistVisit createEntity() {
        DentistVisit dentistVisit = new DentistVisit()
            .userid(DEFAULT_USERID)
            .teethcleaning(DEFAULT_TEETHCLEANING)
            .whitening(DEFAULT_WHITENING)
            .restoration(DEFAULT_RESTORATION)
            .crowns(DEFAULT_CROWNS)
            .bridges(DEFAULT_BRIDGES)
            .braces(DEFAULT_BRACES)
            .endodontictherapy(DEFAULT_ENDODONTICTHERAPY)
            .periodontaltherapy(DEFAULT_PERIODONTALTHERAPY)
            .extraction(DEFAULT_EXTRACTION)
            .oralsurgery(DEFAULT_ORALSURGERY)
            .notes(DEFAULT_NOTES)
            .measurmentdate(DEFAULT_MEASURMENTDATE);
        return dentistVisit;
    }

    @Before
    public void initTest() {
        dentistVisitRepository.deleteAll();
        dentistVisit = createEntity();
    }

    @Test
    public void createDentistVisit() throws Exception {
        int databaseSizeBeforeCreate = dentistVisitRepository.findAll().size();

        // Create the DentistVisit
        DentistVisitDTO dentistVisitDTO = dentistVisitMapper.toDto(dentistVisit);
        restDentistVisitMockMvc.perform(post("/api/dentist-visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dentistVisitDTO)))
            .andExpect(status().isCreated());

        // Validate the DentistVisit in the database
        List<DentistVisit> dentistVisitList = dentistVisitRepository.findAll();
        assertThat(dentistVisitList).hasSize(databaseSizeBeforeCreate + 1);
        DentistVisit testDentistVisit = dentistVisitList.get(dentistVisitList.size() - 1);
        assertThat(testDentistVisit.getUserid()).isEqualTo(DEFAULT_USERID);
        assertThat(testDentistVisit.isTeethcleaning()).isEqualTo(DEFAULT_TEETHCLEANING);
        assertThat(testDentistVisit.isWhitening()).isEqualTo(DEFAULT_WHITENING);
        assertThat(testDentistVisit.isRestoration()).isEqualTo(DEFAULT_RESTORATION);
        assertThat(testDentistVisit.isCrowns()).isEqualTo(DEFAULT_CROWNS);
        assertThat(testDentistVisit.isBridges()).isEqualTo(DEFAULT_BRIDGES);
        assertThat(testDentistVisit.isBraces()).isEqualTo(DEFAULT_BRACES);
        assertThat(testDentistVisit.isEndodontictherapy()).isEqualTo(DEFAULT_ENDODONTICTHERAPY);
        assertThat(testDentistVisit.isPeriodontaltherapy()).isEqualTo(DEFAULT_PERIODONTALTHERAPY);
        assertThat(testDentistVisit.isExtraction()).isEqualTo(DEFAULT_EXTRACTION);
        assertThat(testDentistVisit.isOralsurgery()).isEqualTo(DEFAULT_ORALSURGERY);
        assertThat(testDentistVisit.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testDentistVisit.getMeasurmentdate()).isEqualTo(DEFAULT_MEASURMENTDATE);
    }

    @Test
    public void createDentistVisitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dentistVisitRepository.findAll().size();

        // Create the DentistVisit with an existing ID
        dentistVisit.setId("existing_id");
        DentistVisitDTO dentistVisitDTO = dentistVisitMapper.toDto(dentistVisit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDentistVisitMockMvc.perform(post("/api/dentist-visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dentistVisitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DentistVisit in the database
        List<DentistVisit> dentistVisitList = dentistVisitRepository.findAll();
        assertThat(dentistVisitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllDentistVisits() throws Exception {
        // Initialize the database
        dentistVisitRepository.save(dentistVisit);

        // Get all the dentistVisitList
        restDentistVisitMockMvc.perform(get("/api/dentist-visits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dentistVisit.getId())))
            .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID.toString())))
            .andExpect(jsonPath("$.[*].teethcleaning").value(hasItem(DEFAULT_TEETHCLEANING.booleanValue())))
            .andExpect(jsonPath("$.[*].whitening").value(hasItem(DEFAULT_WHITENING.booleanValue())))
            .andExpect(jsonPath("$.[*].restoration").value(hasItem(DEFAULT_RESTORATION.booleanValue())))
            .andExpect(jsonPath("$.[*].crowns").value(hasItem(DEFAULT_CROWNS.booleanValue())))
            .andExpect(jsonPath("$.[*].bridges").value(hasItem(DEFAULT_BRIDGES.booleanValue())))
            .andExpect(jsonPath("$.[*].braces").value(hasItem(DEFAULT_BRACES.booleanValue())))
            .andExpect(jsonPath("$.[*].endodontictherapy").value(hasItem(DEFAULT_ENDODONTICTHERAPY.booleanValue())))
            .andExpect(jsonPath("$.[*].periodontaltherapy").value(hasItem(DEFAULT_PERIODONTALTHERAPY.booleanValue())))
            .andExpect(jsonPath("$.[*].extraction").value(hasItem(DEFAULT_EXTRACTION.booleanValue())))
            .andExpect(jsonPath("$.[*].oralsurgery").value(hasItem(DEFAULT_ORALSURGERY.booleanValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES.toString())))
            .andExpect(jsonPath("$.[*].measurmentdate").value(hasItem(DEFAULT_MEASURMENTDATE.toString())));
    }

    @Test
    public void getDentistVisit() throws Exception {
        // Initialize the database
        dentistVisitRepository.save(dentistVisit);

        // Get the dentistVisit
        restDentistVisitMockMvc.perform(get("/api/dentist-visits/{id}", dentistVisit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dentistVisit.getId()))
            .andExpect(jsonPath("$.userid").value(DEFAULT_USERID.toString()))
            .andExpect(jsonPath("$.teethcleaning").value(DEFAULT_TEETHCLEANING.booleanValue()))
            .andExpect(jsonPath("$.whitening").value(DEFAULT_WHITENING.booleanValue()))
            .andExpect(jsonPath("$.restoration").value(DEFAULT_RESTORATION.booleanValue()))
            .andExpect(jsonPath("$.crowns").value(DEFAULT_CROWNS.booleanValue()))
            .andExpect(jsonPath("$.bridges").value(DEFAULT_BRIDGES.booleanValue()))
            .andExpect(jsonPath("$.braces").value(DEFAULT_BRACES.booleanValue()))
            .andExpect(jsonPath("$.endodontictherapy").value(DEFAULT_ENDODONTICTHERAPY.booleanValue()))
            .andExpect(jsonPath("$.periodontaltherapy").value(DEFAULT_PERIODONTALTHERAPY.booleanValue()))
            .andExpect(jsonPath("$.extraction").value(DEFAULT_EXTRACTION.booleanValue()))
            .andExpect(jsonPath("$.oralsurgery").value(DEFAULT_ORALSURGERY.booleanValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES.toString()))
            .andExpect(jsonPath("$.measurmentdate").value(DEFAULT_MEASURMENTDATE.toString()));
    }

    @Test
    public void getNonExistingDentistVisit() throws Exception {
        // Get the dentistVisit
        restDentistVisitMockMvc.perform(get("/api/dentist-visits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDentistVisit() throws Exception {
        // Initialize the database
        dentistVisitRepository.save(dentistVisit);
        int databaseSizeBeforeUpdate = dentistVisitRepository.findAll().size();

        // Update the dentistVisit
        DentistVisit updatedDentistVisit = dentistVisitRepository.findOne(dentistVisit.getId());
        updatedDentistVisit
            .userid(UPDATED_USERID)
            .teethcleaning(UPDATED_TEETHCLEANING)
            .whitening(UPDATED_WHITENING)
            .restoration(UPDATED_RESTORATION)
            .crowns(UPDATED_CROWNS)
            .bridges(UPDATED_BRIDGES)
            .braces(UPDATED_BRACES)
            .endodontictherapy(UPDATED_ENDODONTICTHERAPY)
            .periodontaltherapy(UPDATED_PERIODONTALTHERAPY)
            .extraction(UPDATED_EXTRACTION)
            .oralsurgery(UPDATED_ORALSURGERY)
            .notes(UPDATED_NOTES)
            .measurmentdate(UPDATED_MEASURMENTDATE);
        DentistVisitDTO dentistVisitDTO = dentistVisitMapper.toDto(updatedDentistVisit);

        restDentistVisitMockMvc.perform(put("/api/dentist-visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dentistVisitDTO)))
            .andExpect(status().isOk());

        // Validate the DentistVisit in the database
        List<DentistVisit> dentistVisitList = dentistVisitRepository.findAll();
        assertThat(dentistVisitList).hasSize(databaseSizeBeforeUpdate);
        DentistVisit testDentistVisit = dentistVisitList.get(dentistVisitList.size() - 1);
        assertThat(testDentistVisit.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testDentistVisit.isTeethcleaning()).isEqualTo(UPDATED_TEETHCLEANING);
        assertThat(testDentistVisit.isWhitening()).isEqualTo(UPDATED_WHITENING);
        assertThat(testDentistVisit.isRestoration()).isEqualTo(UPDATED_RESTORATION);
        assertThat(testDentistVisit.isCrowns()).isEqualTo(UPDATED_CROWNS);
        assertThat(testDentistVisit.isBridges()).isEqualTo(UPDATED_BRIDGES);
        assertThat(testDentistVisit.isBraces()).isEqualTo(UPDATED_BRACES);
        assertThat(testDentistVisit.isEndodontictherapy()).isEqualTo(UPDATED_ENDODONTICTHERAPY);
        assertThat(testDentistVisit.isPeriodontaltherapy()).isEqualTo(UPDATED_PERIODONTALTHERAPY);
        assertThat(testDentistVisit.isExtraction()).isEqualTo(UPDATED_EXTRACTION);
        assertThat(testDentistVisit.isOralsurgery()).isEqualTo(UPDATED_ORALSURGERY);
        assertThat(testDentistVisit.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testDentistVisit.getMeasurmentdate()).isEqualTo(UPDATED_MEASURMENTDATE);
    }

    @Test
    public void updateNonExistingDentistVisit() throws Exception {
        int databaseSizeBeforeUpdate = dentistVisitRepository.findAll().size();

        // Create the DentistVisit
        DentistVisitDTO dentistVisitDTO = dentistVisitMapper.toDto(dentistVisit);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDentistVisitMockMvc.perform(put("/api/dentist-visits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dentistVisitDTO)))
            .andExpect(status().isCreated());

        // Validate the DentistVisit in the database
        List<DentistVisit> dentistVisitList = dentistVisitRepository.findAll();
        assertThat(dentistVisitList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteDentistVisit() throws Exception {
        // Initialize the database
        dentistVisitRepository.save(dentistVisit);
        int databaseSizeBeforeDelete = dentistVisitRepository.findAll().size();

        // Get the dentistVisit
        restDentistVisitMockMvc.perform(delete("/api/dentist-visits/{id}", dentistVisit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DentistVisit> dentistVisitList = dentistVisitRepository.findAll();
        assertThat(dentistVisitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DentistVisit.class);
        DentistVisit dentistVisit1 = new DentistVisit();
        dentistVisit1.setId("id1");
        DentistVisit dentistVisit2 = new DentistVisit();
        dentistVisit2.setId(dentistVisit1.getId());
        assertThat(dentistVisit1).isEqualTo(dentistVisit2);
        dentistVisit2.setId("id2");
        assertThat(dentistVisit1).isNotEqualTo(dentistVisit2);
        dentistVisit1.setId(null);
        assertThat(dentistVisit1).isNotEqualTo(dentistVisit2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DentistVisitDTO.class);
        DentistVisitDTO dentistVisitDTO1 = new DentistVisitDTO();
        dentistVisitDTO1.setId("id1");
        DentistVisitDTO dentistVisitDTO2 = new DentistVisitDTO();
        assertThat(dentistVisitDTO1).isNotEqualTo(dentistVisitDTO2);
        dentistVisitDTO2.setId(dentistVisitDTO1.getId());
        assertThat(dentistVisitDTO1).isEqualTo(dentistVisitDTO2);
        dentistVisitDTO2.setId("id2");
        assertThat(dentistVisitDTO1).isNotEqualTo(dentistVisitDTO2);
        dentistVisitDTO1.setId(null);
        assertThat(dentistVisitDTO1).isNotEqualTo(dentistVisitDTO2);
    }
}
