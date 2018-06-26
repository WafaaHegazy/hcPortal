package com.sirtts.web.rest;

import com.sirtts.HcPortalApp;

import com.sirtts.domain.BloodTest;
import com.sirtts.repository.BloodTestRepository;
import com.sirtts.service.BloodTestService;
import com.sirtts.service.dto.BloodTestDTO;
import com.sirtts.service.mapper.BloodTestMapper;
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
 * Test class for the BloodTestResource REST controller.
 *
 * @see BloodTestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HcPortalApp.class)
public class BloodTestResourceIntTest {

    private static final String DEFAULT_USERID = "AAAAAAAAAA";
    private static final String UPDATED_USERID = "BBBBBBBBBB";

    private static final Double DEFAULT_HYDROXYPROGESTERONE_17 = 1D;
    private static final Double UPDATED_HYDROXYPROGESTERONE_17 = 2D;

    private static final Double DEFAULT_HYDROXYVITAMIN_D_25 = 1D;
    private static final Double UPDATED_HYDROXYVITAMIN_D_25 = 2D;

    private static final Double DEFAULT_ACETOACETATE = 1D;
    private static final Double UPDATED_ACETOACETATE = 2D;

    private static final Double DEFAULT_ACIDITY = 1D;
    private static final Double UPDATED_ACIDITY = 2D;

    private static final Double DEFAULT_ALCOHOL = 1D;
    private static final Double UPDATED_ALCOHOL = 2D;

    private static final Double DEFAULT_AMMONIA = 1D;
    private static final Double UPDATED_AMMONIA = 2D;

    private static final Double DEFAULT_AMYLASE = 1D;
    private static final Double UPDATED_AMYLASE = 2D;

    private static final Double DEFAULT_ASCORBIC_ACID = 1D;
    private static final Double UPDATED_ASCORBIC_ACID = 2D;

    private static final Double DEFAULT_BICARBONATE = 1D;
    private static final Double UPDATED_BICARBONATE = 2D;

    private static final Double DEFAULT_BILIRUBIN = 1D;
    private static final Double UPDATED_BILIRUBIN = 2D;

    private static final Double DEFAULT_BLOOD_VOLUME = 1D;
    private static final Double UPDATED_BLOOD_VOLUME = 2D;

    private static final Double DEFAULT_CALCIUM = 1D;
    private static final Double UPDATED_CALCIUM = 2D;

    private static final Double DEFAULT_CARBON_DIOXIDE_PRESSURE = 1D;
    private static final Double UPDATED_CARBON_DIOXIDE_PRESSURE = 2D;

    private static final Double DEFAULT_CARBON_MONOXIDE = 1D;
    private static final Double UPDATED_CARBON_MONOXIDE = 2D;

    private static final Double DEFAULT_C_D_4_CELL_COUNT = 1D;
    private static final Double UPDATED_C_D_4_CELL_COUNT = 2D;

    private static final Double DEFAULT_CERULOPLASMIN = 1D;
    private static final Double UPDATED_CERULOPLASMIN = 2D;

    private static final Double DEFAULT_CHLORIDE = 1D;
    private static final Double UPDATED_CHLORIDE = 2D;

    private static final Double DEFAULT_COMPLETE_BLOOD_CELL_COUNT = 1D;
    private static final Double UPDATED_COMPLETE_BLOOD_CELL_COUNT = 2D;

    private static final Double DEFAULT_COPPER = 1D;
    private static final Double UPDATED_COPPER = 2D;

    private static final Double DEFAULT_CREATINE_KINASE = 1D;
    private static final Double UPDATED_CREATINE_KINASE = 2D;

    private static final Double DEFAULT_CREATINE_KINASE_ISOENZYMES = 1D;
    private static final Double UPDATED_CREATINE_KINASE_ISOENZYMES = 2D;

    private static final Double DEFAULT_CREATININE = 1D;
    private static final Double UPDATED_CREATININE = 2D;

    private static final Double DEFAULT_ELECTROLYTES = 1D;
    private static final Double UPDATED_ELECTROLYTES = 2D;

    private static final Double DEFAULT_ERYTHROCYTE_SEDIMENTATION_RATE = 1D;
    private static final Double UPDATED_ERYTHROCYTE_SEDIMENTATION_RATE = 2D;

    private static final Double DEFAULT_GLUCOSE = 1D;
    private static final Double UPDATED_GLUCOSE = 2D;

    private static final Double DEFAULT_HEMATOCRIT = 1D;
    private static final Double UPDATED_HEMATOCRIT = 2D;

    private static final Double DEFAULT_HEMOGLOBIN = 1D;
    private static final Double UPDATED_HEMOGLOBIN = 2D;

    private static final Double DEFAULT_IRON = 1D;
    private static final Double UPDATED_IRON = 2D;

    private static final Double DEFAULT_IRON_BINDING_CAPACITY = 1D;
    private static final Double UPDATED_IRON_BINDING_CAPACITY = 2D;

    private static final Double DEFAULT_LACTATE = 1D;
    private static final Double UPDATED_LACTATE = 2D;

    private static final Double DEFAULT_LACTIC_DEHYDROGENASE = 1D;
    private static final Double UPDATED_LACTIC_DEHYDROGENASE = 2D;

    private static final Double DEFAULT_LEAD = 1D;
    private static final Double UPDATED_LEAD = 2D;

    private static final Double DEFAULT_LIPASE = 1D;
    private static final Double UPDATED_LIPASE = 2D;

    private static final Double DEFAULT_ZINC = 1D;
    private static final Double UPDATED_ZINC = 2D;

    private static final Double DEFAULT_LIPIDS_CHOLESTEROL = 1D;
    private static final Double UPDATED_LIPIDS_CHOLESTEROL = 2D;

    private static final Double DEFAULT_LIPIDS_TRIGLYCERIDES = 1D;
    private static final Double UPDATED_LIPIDS_TRIGLYCERIDES = 2D;

    private static final Double DEFAULT_MAGNESIUM = 1D;
    private static final Double UPDATED_MAGNESIUM = 2D;

    private static final Double DEFAULT_MEAN_CORPUSCULAR_HEMOGLOBIN = 1D;
    private static final Double UPDATED_MEAN_CORPUSCULAR_HEMOGLOBIN = 2D;

    private static final Double DEFAULT_MEAN_CORPUSCULAR_HEMOGLOBIN_CONCENTRATION = 1D;
    private static final Double UPDATED_MEAN_CORPUSCULAR_HEMOGLOBIN_CONCENTRATION = 2D;

    private static final Double DEFAULT_MEAN_CORPUSCULAR_VOLUME = 1D;
    private static final Double UPDATED_MEAN_CORPUSCULAR_VOLUME = 2D;

    private static final Double DEFAULT_OSMOLALITY = 1D;
    private static final Double UPDATED_OSMOLALITY = 2D;

    private static final Double DEFAULT_OXYGEN_PRESSURE = 1D;
    private static final Double UPDATED_OXYGEN_PRESSURE = 2D;

    private static final Double DEFAULT_OXYGEN_SATURATION = 1D;
    private static final Double UPDATED_OXYGEN_SATURATION = 2D;

    private static final Double DEFAULT_PHOSPHATASE_PROSTATIC = 1D;
    private static final Double UPDATED_PHOSPHATASE_PROSTATIC = 2D;

    private static final Double DEFAULT_PHOSPHATASE = 1D;
    private static final Double UPDATED_PHOSPHATASE = 2D;

    private static final Double DEFAULT_PHOSPHORUS = 1D;
    private static final Double UPDATED_PHOSPHORUS = 2D;

    private static final Double DEFAULT_PLATELET_COUNT = 1D;
    private static final Double UPDATED_PLATELET_COUNT = 2D;

    private static final Double DEFAULT_POTASSIUM = 1D;
    private static final Double UPDATED_POTASSIUM = 2D;

    private static final Double DEFAULT_PROSTATE_SPECIFIC_ANTIGEN = 1D;
    private static final Double UPDATED_PROSTATE_SPECIFIC_ANTIGEN = 2D;

    private static final Double DEFAULT_PROTEINS_TOTAL = 1D;
    private static final Double UPDATED_PROTEINS_TOTAL = 2D;

    private static final Double DEFAULT_PROTEINS_ALBUMIN = 1D;
    private static final Double UPDATED_PROTEINS_ALBUMIN = 2D;

    private static final Double DEFAULT_PROTEINS_GLOBULIN = 1D;
    private static final Double UPDATED_PROTEINS_GLOBULIN = 2D;

    private static final Double DEFAULT_PROTHROMBIN = 1D;
    private static final Double UPDATED_PROTHROMBIN = 2D;

    private static final Double DEFAULT_PYRUVIC_ACID = 1D;
    private static final Double UPDATED_PYRUVIC_ACID = 2D;

    private static final Double DEFAULT_RED_BLOOD_CELL_COUNT = 1D;
    private static final Double UPDATED_RED_BLOOD_CELL_COUNT = 2D;

    private static final Double DEFAULT_SODIUM = 1D;
    private static final Double UPDATED_SODIUM = 2D;

    private static final Double DEFAULT_THYROID_STIMULATING_HORMONE = 1D;
    private static final Double UPDATED_THYROID_STIMULATING_HORMONE = 2D;

    private static final Double DEFAULT_TRANSAMINASE_ALANINE = 1D;
    private static final Double UPDATED_TRANSAMINASE_ALANINE = 2D;

    private static final Double DEFAULT_TRANSAMINASE_ASPARTATE = 1D;
    private static final Double UPDATED_TRANSAMINASE_ASPARTATE = 2D;

    private static final Double DEFAULT_UREA_NITROGEN = 1D;
    private static final Double UPDATED_UREA_NITROGEN = 2D;

    private static final Double DEFAULT_B_UN_CREATININE_RATIO = 1D;
    private static final Double UPDATED_B_UN_CREATININE_RATIO = 2D;

    private static final Double DEFAULT_URIC_ACID = 1D;
    private static final Double UPDATED_URIC_ACID = 2D;

    private static final Double DEFAULT_VITAMIN_A = 1D;
    private static final Double UPDATED_VITAMIN_A = 2D;

    private static final Double DEFAULT_W_BC = 1D;
    private static final Double UPDATED_W_BC = 2D;

    private static final Double DEFAULT_WHITE_BLOOD_CELL_COUNT = 1D;
    private static final Double UPDATED_WHITE_BLOOD_CELL_COUNT = 2D;

    private static final LocalDateTime DEFAULT_MEASURMENTDATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);
    private static final LocalDateTime UPDATED_MEASURMENTDATE = LocalDateTime.ofEpochSecond(1L,0,ZoneOffset.UTC);

    @Autowired
    private BloodTestRepository bloodTestRepository;

    @Autowired
    private BloodTestMapper bloodTestMapper;

    @Autowired
    private BloodTestService bloodTestService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restBloodTestMockMvc;

    private BloodTest bloodTest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BloodTestResource bloodTestResource = new BloodTestResource(bloodTestService);
        this.restBloodTestMockMvc = MockMvcBuilders.standaloneSetup(bloodTestResource)
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
    public static BloodTest createEntity() {
        BloodTest bloodTest = new BloodTest()
            .userid(DEFAULT_USERID)
            .hydroxyprogesterone17(DEFAULT_HYDROXYPROGESTERONE_17)
            .hydroxyvitaminD25(DEFAULT_HYDROXYVITAMIN_D_25)
            .acetoacetate(DEFAULT_ACETOACETATE)
            .acidity(DEFAULT_ACIDITY)
            .alcohol(DEFAULT_ALCOHOL)
            .ammonia(DEFAULT_AMMONIA)
            .amylase(DEFAULT_AMYLASE)
            .ascorbicAcid(DEFAULT_ASCORBIC_ACID)
            .bicarbonate(DEFAULT_BICARBONATE)
            .bilirubin(DEFAULT_BILIRUBIN)
            .bloodVolume(DEFAULT_BLOOD_VOLUME)
            .calcium(DEFAULT_CALCIUM)
            .carbonDioxidePressure(DEFAULT_CARBON_DIOXIDE_PRESSURE)
            .carbonMonoxide(DEFAULT_CARBON_MONOXIDE)
            .cD4CellCount(DEFAULT_C_D_4_CELL_COUNT)
            .ceruloplasmin(DEFAULT_CERULOPLASMIN)
            .chloride(DEFAULT_CHLORIDE)
            .completeBloodCellCount(DEFAULT_COMPLETE_BLOOD_CELL_COUNT)
            .copper(DEFAULT_COPPER)
            .creatineKinase(DEFAULT_CREATINE_KINASE)
            .creatineKinaseIsoenzymes(DEFAULT_CREATINE_KINASE_ISOENZYMES)
            .creatinine(DEFAULT_CREATININE)
            .electrolytes(DEFAULT_ELECTROLYTES)
            .erythrocyteSedimentationRate(DEFAULT_ERYTHROCYTE_SEDIMENTATION_RATE)
            .glucose(DEFAULT_GLUCOSE)
            .hematocrit(DEFAULT_HEMATOCRIT)
            .hemoglobin(DEFAULT_HEMOGLOBIN)
            .iron(DEFAULT_IRON)
            .ironBindingCapacity(DEFAULT_IRON_BINDING_CAPACITY)
            .lactate(DEFAULT_LACTATE)
            .lacticDehydrogenase(DEFAULT_LACTIC_DEHYDROGENASE)
            .lead(DEFAULT_LEAD)
            .lipase(DEFAULT_LIPASE)
            .zinc(DEFAULT_ZINC)
            .lipidsCholesterol(DEFAULT_LIPIDS_CHOLESTEROL)
            .lipidsTriglycerides(DEFAULT_LIPIDS_TRIGLYCERIDES)
            .magnesium(DEFAULT_MAGNESIUM)
            .meanCorpuscularHemoglobin(DEFAULT_MEAN_CORPUSCULAR_HEMOGLOBIN)
            .meanCorpuscularHemoglobinConcentration(DEFAULT_MEAN_CORPUSCULAR_HEMOGLOBIN_CONCENTRATION)
            .meanCorpuscularVolume(DEFAULT_MEAN_CORPUSCULAR_VOLUME)
            .osmolality(DEFAULT_OSMOLALITY)
            .oxygenPressure(DEFAULT_OXYGEN_PRESSURE)
            .oxygenSaturation(DEFAULT_OXYGEN_SATURATION)
            .phosphataseProstatic(DEFAULT_PHOSPHATASE_PROSTATIC)
            .phosphatase(DEFAULT_PHOSPHATASE)
            .phosphorus(DEFAULT_PHOSPHORUS)
            .plateletCount(DEFAULT_PLATELET_COUNT)
            .potassium(DEFAULT_POTASSIUM)
            .prostateSpecificAntigen(DEFAULT_PROSTATE_SPECIFIC_ANTIGEN)
            .proteinsTotal(DEFAULT_PROTEINS_TOTAL)
            .proteinsAlbumin(DEFAULT_PROTEINS_ALBUMIN)
            .proteinsGlobulin(DEFAULT_PROTEINS_GLOBULIN)
            .prothrombin(DEFAULT_PROTHROMBIN)
            .pyruvicAcid(DEFAULT_PYRUVIC_ACID)
            .redBloodCellCount(DEFAULT_RED_BLOOD_CELL_COUNT)
            .sodium(DEFAULT_SODIUM)
            .thyroidStimulatingHormone(DEFAULT_THYROID_STIMULATING_HORMONE)
            .transaminaseAlanine(DEFAULT_TRANSAMINASE_ALANINE)
            .transaminaseAspartate(DEFAULT_TRANSAMINASE_ASPARTATE)
            .ureaNitrogen(DEFAULT_UREA_NITROGEN)
            .bUNCreatinineRatio(DEFAULT_B_UN_CREATININE_RATIO)
            .uricAcid(DEFAULT_URIC_ACID)
            .vitaminA(DEFAULT_VITAMIN_A)
            .wBC(DEFAULT_W_BC)
            .whiteBloodCellCount(DEFAULT_WHITE_BLOOD_CELL_COUNT)
            .measurmentdate(DEFAULT_MEASURMENTDATE);
        return bloodTest;
    }

    @Before
    public void initTest() {
        bloodTestRepository.deleteAll();
        bloodTest = createEntity();
    }

    @Test
    public void createBloodTest() throws Exception {
        int databaseSizeBeforeCreate = bloodTestRepository.findAll().size();

        // Create the BloodTest
        BloodTestDTO bloodTestDTO = bloodTestMapper.toDto(bloodTest);
        restBloodTestMockMvc.perform(post("/api/blood-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bloodTestDTO)))
            .andExpect(status().isCreated());

        // Validate the BloodTest in the database
        List<BloodTest> bloodTestList = bloodTestRepository.findAll();
        assertThat(bloodTestList).hasSize(databaseSizeBeforeCreate + 1);
        BloodTest testBloodTest = bloodTestList.get(bloodTestList.size() - 1);
        assertThat(testBloodTest.getUserid()).isEqualTo(DEFAULT_USERID);
        assertThat(testBloodTest.getHydroxyprogesterone17()).isEqualTo(DEFAULT_HYDROXYPROGESTERONE_17);
        assertThat(testBloodTest.getHydroxyvitaminD25()).isEqualTo(DEFAULT_HYDROXYVITAMIN_D_25);
        assertThat(testBloodTest.getAcetoacetate()).isEqualTo(DEFAULT_ACETOACETATE);
        assertThat(testBloodTest.getAcidity()).isEqualTo(DEFAULT_ACIDITY);
        assertThat(testBloodTest.getAlcohol()).isEqualTo(DEFAULT_ALCOHOL);
        assertThat(testBloodTest.getAmmonia()).isEqualTo(DEFAULT_AMMONIA);
        assertThat(testBloodTest.getAmylase()).isEqualTo(DEFAULT_AMYLASE);
        assertThat(testBloodTest.getAscorbicAcid()).isEqualTo(DEFAULT_ASCORBIC_ACID);
        assertThat(testBloodTest.getBicarbonate()).isEqualTo(DEFAULT_BICARBONATE);
        assertThat(testBloodTest.getBilirubin()).isEqualTo(DEFAULT_BILIRUBIN);
        assertThat(testBloodTest.getBloodVolume()).isEqualTo(DEFAULT_BLOOD_VOLUME);
        assertThat(testBloodTest.getCalcium()).isEqualTo(DEFAULT_CALCIUM);
        assertThat(testBloodTest.getCarbonDioxidePressure()).isEqualTo(DEFAULT_CARBON_DIOXIDE_PRESSURE);
        assertThat(testBloodTest.getCarbonMonoxide()).isEqualTo(DEFAULT_CARBON_MONOXIDE);
        assertThat(testBloodTest.getcD4CellCount()).isEqualTo(DEFAULT_C_D_4_CELL_COUNT);
        assertThat(testBloodTest.getCeruloplasmin()).isEqualTo(DEFAULT_CERULOPLASMIN);
        assertThat(testBloodTest.getChloride()).isEqualTo(DEFAULT_CHLORIDE);
        assertThat(testBloodTest.getCompleteBloodCellCount()).isEqualTo(DEFAULT_COMPLETE_BLOOD_CELL_COUNT);
        assertThat(testBloodTest.getCopper()).isEqualTo(DEFAULT_COPPER);
        assertThat(testBloodTest.getCreatineKinase()).isEqualTo(DEFAULT_CREATINE_KINASE);
        assertThat(testBloodTest.getCreatineKinaseIsoenzymes()).isEqualTo(DEFAULT_CREATINE_KINASE_ISOENZYMES);
        assertThat(testBloodTest.getCreatinine()).isEqualTo(DEFAULT_CREATININE);
        assertThat(testBloodTest.getElectrolytes()).isEqualTo(DEFAULT_ELECTROLYTES);
        assertThat(testBloodTest.getErythrocyteSedimentationRate()).isEqualTo(DEFAULT_ERYTHROCYTE_SEDIMENTATION_RATE);
        assertThat(testBloodTest.getGlucose()).isEqualTo(DEFAULT_GLUCOSE);
        assertThat(testBloodTest.getHematocrit()).isEqualTo(DEFAULT_HEMATOCRIT);
        assertThat(testBloodTest.getHemoglobin()).isEqualTo(DEFAULT_HEMOGLOBIN);
        assertThat(testBloodTest.getIron()).isEqualTo(DEFAULT_IRON);
        assertThat(testBloodTest.getIronBindingCapacity()).isEqualTo(DEFAULT_IRON_BINDING_CAPACITY);
        assertThat(testBloodTest.getLactate()).isEqualTo(DEFAULT_LACTATE);
        assertThat(testBloodTest.getLacticDehydrogenase()).isEqualTo(DEFAULT_LACTIC_DEHYDROGENASE);
        assertThat(testBloodTest.getLead()).isEqualTo(DEFAULT_LEAD);
        assertThat(testBloodTest.getLipase()).isEqualTo(DEFAULT_LIPASE);
        assertThat(testBloodTest.getZinc()).isEqualTo(DEFAULT_ZINC);
        assertThat(testBloodTest.getLipidsCholesterol()).isEqualTo(DEFAULT_LIPIDS_CHOLESTEROL);
        assertThat(testBloodTest.getLipidsTriglycerides()).isEqualTo(DEFAULT_LIPIDS_TRIGLYCERIDES);
        assertThat(testBloodTest.getMagnesium()).isEqualTo(DEFAULT_MAGNESIUM);
        assertThat(testBloodTest.getMeanCorpuscularHemoglobin()).isEqualTo(DEFAULT_MEAN_CORPUSCULAR_HEMOGLOBIN);
        assertThat(testBloodTest.getMeanCorpuscularHemoglobinConcentration()).isEqualTo(DEFAULT_MEAN_CORPUSCULAR_HEMOGLOBIN_CONCENTRATION);
        assertThat(testBloodTest.getMeanCorpuscularVolume()).isEqualTo(DEFAULT_MEAN_CORPUSCULAR_VOLUME);
        assertThat(testBloodTest.getOsmolality()).isEqualTo(DEFAULT_OSMOLALITY);
        assertThat(testBloodTest.getOxygenPressure()).isEqualTo(DEFAULT_OXYGEN_PRESSURE);
        assertThat(testBloodTest.getOxygenSaturation()).isEqualTo(DEFAULT_OXYGEN_SATURATION);
        assertThat(testBloodTest.getPhosphataseProstatic()).isEqualTo(DEFAULT_PHOSPHATASE_PROSTATIC);
        assertThat(testBloodTest.getPhosphatase()).isEqualTo(DEFAULT_PHOSPHATASE);
        assertThat(testBloodTest.getPhosphorus()).isEqualTo(DEFAULT_PHOSPHORUS);
        assertThat(testBloodTest.getPlateletCount()).isEqualTo(DEFAULT_PLATELET_COUNT);
        assertThat(testBloodTest.getPotassium()).isEqualTo(DEFAULT_POTASSIUM);
        assertThat(testBloodTest.getProstateSpecificAntigen()).isEqualTo(DEFAULT_PROSTATE_SPECIFIC_ANTIGEN);
        assertThat(testBloodTest.getProteinsTotal()).isEqualTo(DEFAULT_PROTEINS_TOTAL);
        assertThat(testBloodTest.getProteinsAlbumin()).isEqualTo(DEFAULT_PROTEINS_ALBUMIN);
        assertThat(testBloodTest.getProteinsGlobulin()).isEqualTo(DEFAULT_PROTEINS_GLOBULIN);
        assertThat(testBloodTest.getProthrombin()).isEqualTo(DEFAULT_PROTHROMBIN);
        assertThat(testBloodTest.getPyruvicAcid()).isEqualTo(DEFAULT_PYRUVIC_ACID);
        assertThat(testBloodTest.getRedBloodCellCount()).isEqualTo(DEFAULT_RED_BLOOD_CELL_COUNT);
        assertThat(testBloodTest.getSodium()).isEqualTo(DEFAULT_SODIUM);
        assertThat(testBloodTest.getThyroidStimulatingHormone()).isEqualTo(DEFAULT_THYROID_STIMULATING_HORMONE);
        assertThat(testBloodTest.getTransaminaseAlanine()).isEqualTo(DEFAULT_TRANSAMINASE_ALANINE);
        assertThat(testBloodTest.getTransaminaseAspartate()).isEqualTo(DEFAULT_TRANSAMINASE_ASPARTATE);
        assertThat(testBloodTest.getUreaNitrogen()).isEqualTo(DEFAULT_UREA_NITROGEN);
        assertThat(testBloodTest.getbUNCreatinineRatio()).isEqualTo(DEFAULT_B_UN_CREATININE_RATIO);
        assertThat(testBloodTest.getUricAcid()).isEqualTo(DEFAULT_URIC_ACID);
        assertThat(testBloodTest.getVitaminA()).isEqualTo(DEFAULT_VITAMIN_A);
        assertThat(testBloodTest.getwBC()).isEqualTo(DEFAULT_W_BC);
        assertThat(testBloodTest.getWhiteBloodCellCount()).isEqualTo(DEFAULT_WHITE_BLOOD_CELL_COUNT);
        assertThat(testBloodTest.getMeasurmentdate()).isEqualTo(DEFAULT_MEASURMENTDATE);
    }

    @Test
    public void createBloodTestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bloodTestRepository.findAll().size();

        // Create the BloodTest with an existing ID
        bloodTest.setId("existing_id");
        BloodTestDTO bloodTestDTO = bloodTestMapper.toDto(bloodTest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBloodTestMockMvc.perform(post("/api/blood-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bloodTestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BloodTest in the database
        List<BloodTest> bloodTestList = bloodTestRepository.findAll();
        assertThat(bloodTestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllBloodTests() throws Exception {
        // Initialize the database
        bloodTestRepository.save(bloodTest);

        // Get all the bloodTestList
        restBloodTestMockMvc.perform(get("/api/blood-tests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bloodTest.getId())))
            .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID.toString())))
            .andExpect(jsonPath("$.[*].hydroxyprogesterone17").value(hasItem(DEFAULT_HYDROXYPROGESTERONE_17.doubleValue())))
            .andExpect(jsonPath("$.[*].hydroxyvitaminD25").value(hasItem(DEFAULT_HYDROXYVITAMIN_D_25.doubleValue())))
            .andExpect(jsonPath("$.[*].acetoacetate").value(hasItem(DEFAULT_ACETOACETATE.doubleValue())))
            .andExpect(jsonPath("$.[*].acidity").value(hasItem(DEFAULT_ACIDITY.doubleValue())))
            .andExpect(jsonPath("$.[*].alcohol").value(hasItem(DEFAULT_ALCOHOL.doubleValue())))
            .andExpect(jsonPath("$.[*].ammonia").value(hasItem(DEFAULT_AMMONIA.doubleValue())))
            .andExpect(jsonPath("$.[*].amylase").value(hasItem(DEFAULT_AMYLASE.doubleValue())))
            .andExpect(jsonPath("$.[*].ascorbicAcid").value(hasItem(DEFAULT_ASCORBIC_ACID.doubleValue())))
            .andExpect(jsonPath("$.[*].bicarbonate").value(hasItem(DEFAULT_BICARBONATE.doubleValue())))
            .andExpect(jsonPath("$.[*].bilirubin").value(hasItem(DEFAULT_BILIRUBIN.doubleValue())))
            .andExpect(jsonPath("$.[*].bloodVolume").value(hasItem(DEFAULT_BLOOD_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].calcium").value(hasItem(DEFAULT_CALCIUM.doubleValue())))
            .andExpect(jsonPath("$.[*].carbonDioxidePressure").value(hasItem(DEFAULT_CARBON_DIOXIDE_PRESSURE.doubleValue())))
            .andExpect(jsonPath("$.[*].carbonMonoxide").value(hasItem(DEFAULT_CARBON_MONOXIDE.doubleValue())))
            .andExpect(jsonPath("$.[*].cD4CellCount").value(hasItem(DEFAULT_C_D_4_CELL_COUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].ceruloplasmin").value(hasItem(DEFAULT_CERULOPLASMIN.doubleValue())))
            .andExpect(jsonPath("$.[*].chloride").value(hasItem(DEFAULT_CHLORIDE.doubleValue())))
            .andExpect(jsonPath("$.[*].completeBloodCellCount").value(hasItem(DEFAULT_COMPLETE_BLOOD_CELL_COUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].copper").value(hasItem(DEFAULT_COPPER.doubleValue())))
            .andExpect(jsonPath("$.[*].creatineKinase").value(hasItem(DEFAULT_CREATINE_KINASE.doubleValue())))
            .andExpect(jsonPath("$.[*].creatineKinaseIsoenzymes").value(hasItem(DEFAULT_CREATINE_KINASE_ISOENZYMES.doubleValue())))
            .andExpect(jsonPath("$.[*].creatinine").value(hasItem(DEFAULT_CREATININE.doubleValue())))
            .andExpect(jsonPath("$.[*].electrolytes").value(hasItem(DEFAULT_ELECTROLYTES.doubleValue())))
            .andExpect(jsonPath("$.[*].erythrocyteSedimentationRate").value(hasItem(DEFAULT_ERYTHROCYTE_SEDIMENTATION_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].glucose").value(hasItem(DEFAULT_GLUCOSE.doubleValue())))
            .andExpect(jsonPath("$.[*].hematocrit").value(hasItem(DEFAULT_HEMATOCRIT.doubleValue())))
            .andExpect(jsonPath("$.[*].hemoglobin").value(hasItem(DEFAULT_HEMOGLOBIN.doubleValue())))
            .andExpect(jsonPath("$.[*].iron").value(hasItem(DEFAULT_IRON.doubleValue())))
            .andExpect(jsonPath("$.[*].ironBindingCapacity").value(hasItem(DEFAULT_IRON_BINDING_CAPACITY.doubleValue())))
            .andExpect(jsonPath("$.[*].lactate").value(hasItem(DEFAULT_LACTATE.doubleValue())))
            .andExpect(jsonPath("$.[*].lacticDehydrogenase").value(hasItem(DEFAULT_LACTIC_DEHYDROGENASE.doubleValue())))
            .andExpect(jsonPath("$.[*].lead").value(hasItem(DEFAULT_LEAD.doubleValue())))
            .andExpect(jsonPath("$.[*].lipase").value(hasItem(DEFAULT_LIPASE.doubleValue())))
            .andExpect(jsonPath("$.[*].zinc").value(hasItem(DEFAULT_ZINC.doubleValue())))
            .andExpect(jsonPath("$.[*].lipidsCholesterol").value(hasItem(DEFAULT_LIPIDS_CHOLESTEROL.doubleValue())))
            .andExpect(jsonPath("$.[*].lipidsTriglycerides").value(hasItem(DEFAULT_LIPIDS_TRIGLYCERIDES.doubleValue())))
            .andExpect(jsonPath("$.[*].magnesium").value(hasItem(DEFAULT_MAGNESIUM.doubleValue())))
            .andExpect(jsonPath("$.[*].meanCorpuscularHemoglobin").value(hasItem(DEFAULT_MEAN_CORPUSCULAR_HEMOGLOBIN.doubleValue())))
            .andExpect(jsonPath("$.[*].meanCorpuscularHemoglobinConcentration").value(hasItem(DEFAULT_MEAN_CORPUSCULAR_HEMOGLOBIN_CONCENTRATION.doubleValue())))
            .andExpect(jsonPath("$.[*].meanCorpuscularVolume").value(hasItem(DEFAULT_MEAN_CORPUSCULAR_VOLUME.doubleValue())))
            .andExpect(jsonPath("$.[*].osmolality").value(hasItem(DEFAULT_OSMOLALITY.doubleValue())))
            .andExpect(jsonPath("$.[*].oxygenPressure").value(hasItem(DEFAULT_OXYGEN_PRESSURE.doubleValue())))
            .andExpect(jsonPath("$.[*].oxygenSaturation").value(hasItem(DEFAULT_OXYGEN_SATURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].phosphataseProstatic").value(hasItem(DEFAULT_PHOSPHATASE_PROSTATIC.doubleValue())))
            .andExpect(jsonPath("$.[*].phosphatase").value(hasItem(DEFAULT_PHOSPHATASE.doubleValue())))
            .andExpect(jsonPath("$.[*].phosphorus").value(hasItem(DEFAULT_PHOSPHORUS.doubleValue())))
            .andExpect(jsonPath("$.[*].plateletCount").value(hasItem(DEFAULT_PLATELET_COUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].potassium").value(hasItem(DEFAULT_POTASSIUM.doubleValue())))
            .andExpect(jsonPath("$.[*].prostateSpecificAntigen").value(hasItem(DEFAULT_PROSTATE_SPECIFIC_ANTIGEN.doubleValue())))
            .andExpect(jsonPath("$.[*].proteinsTotal").value(hasItem(DEFAULT_PROTEINS_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].proteinsAlbumin").value(hasItem(DEFAULT_PROTEINS_ALBUMIN.doubleValue())))
            .andExpect(jsonPath("$.[*].proteinsGlobulin").value(hasItem(DEFAULT_PROTEINS_GLOBULIN.doubleValue())))
            .andExpect(jsonPath("$.[*].prothrombin").value(hasItem(DEFAULT_PROTHROMBIN.doubleValue())))
            .andExpect(jsonPath("$.[*].pyruvicAcid").value(hasItem(DEFAULT_PYRUVIC_ACID.doubleValue())))
            .andExpect(jsonPath("$.[*].redBloodCellCount").value(hasItem(DEFAULT_RED_BLOOD_CELL_COUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].sodium").value(hasItem(DEFAULT_SODIUM.doubleValue())))
            .andExpect(jsonPath("$.[*].thyroidStimulatingHormone").value(hasItem(DEFAULT_THYROID_STIMULATING_HORMONE.doubleValue())))
            .andExpect(jsonPath("$.[*].transaminaseAlanine").value(hasItem(DEFAULT_TRANSAMINASE_ALANINE.doubleValue())))
            .andExpect(jsonPath("$.[*].transaminaseAspartate").value(hasItem(DEFAULT_TRANSAMINASE_ASPARTATE.doubleValue())))
            .andExpect(jsonPath("$.[*].ureaNitrogen").value(hasItem(DEFAULT_UREA_NITROGEN.doubleValue())))
            .andExpect(jsonPath("$.[*].bUNCreatinineRatio").value(hasItem(DEFAULT_B_UN_CREATININE_RATIO.doubleValue())))
            .andExpect(jsonPath("$.[*].uricAcid").value(hasItem(DEFAULT_URIC_ACID.doubleValue())))
            .andExpect(jsonPath("$.[*].vitaminA").value(hasItem(DEFAULT_VITAMIN_A.doubleValue())))
            .andExpect(jsonPath("$.[*].wBC").value(hasItem(DEFAULT_W_BC.doubleValue())))
            .andExpect(jsonPath("$.[*].whiteBloodCellCount").value(hasItem(DEFAULT_WHITE_BLOOD_CELL_COUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].measurmentdate").value(hasItem(DEFAULT_MEASURMENTDATE.toString())));
    }

    @Test
    public void getBloodTest() throws Exception {
        // Initialize the database
        bloodTestRepository.save(bloodTest);

        // Get the bloodTest
        restBloodTestMockMvc.perform(get("/api/blood-tests/{id}", bloodTest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bloodTest.getId()))
            .andExpect(jsonPath("$.userid").value(DEFAULT_USERID.toString()))
            .andExpect(jsonPath("$.hydroxyprogesterone17").value(DEFAULT_HYDROXYPROGESTERONE_17.doubleValue()))
            .andExpect(jsonPath("$.hydroxyvitaminD25").value(DEFAULT_HYDROXYVITAMIN_D_25.doubleValue()))
            .andExpect(jsonPath("$.acetoacetate").value(DEFAULT_ACETOACETATE.doubleValue()))
            .andExpect(jsonPath("$.acidity").value(DEFAULT_ACIDITY.doubleValue()))
            .andExpect(jsonPath("$.alcohol").value(DEFAULT_ALCOHOL.doubleValue()))
            .andExpect(jsonPath("$.ammonia").value(DEFAULT_AMMONIA.doubleValue()))
            .andExpect(jsonPath("$.amylase").value(DEFAULT_AMYLASE.doubleValue()))
            .andExpect(jsonPath("$.ascorbicAcid").value(DEFAULT_ASCORBIC_ACID.doubleValue()))
            .andExpect(jsonPath("$.bicarbonate").value(DEFAULT_BICARBONATE.doubleValue()))
            .andExpect(jsonPath("$.bilirubin").value(DEFAULT_BILIRUBIN.doubleValue()))
            .andExpect(jsonPath("$.bloodVolume").value(DEFAULT_BLOOD_VOLUME.doubleValue()))
            .andExpect(jsonPath("$.calcium").value(DEFAULT_CALCIUM.doubleValue()))
            .andExpect(jsonPath("$.carbonDioxidePressure").value(DEFAULT_CARBON_DIOXIDE_PRESSURE.doubleValue()))
            .andExpect(jsonPath("$.carbonMonoxide").value(DEFAULT_CARBON_MONOXIDE.doubleValue()))
            .andExpect(jsonPath("$.cD4CellCount").value(DEFAULT_C_D_4_CELL_COUNT.doubleValue()))
            .andExpect(jsonPath("$.ceruloplasmin").value(DEFAULT_CERULOPLASMIN.doubleValue()))
            .andExpect(jsonPath("$.chloride").value(DEFAULT_CHLORIDE.doubleValue()))
            .andExpect(jsonPath("$.completeBloodCellCount").value(DEFAULT_COMPLETE_BLOOD_CELL_COUNT.doubleValue()))
            .andExpect(jsonPath("$.copper").value(DEFAULT_COPPER.doubleValue()))
            .andExpect(jsonPath("$.creatineKinase").value(DEFAULT_CREATINE_KINASE.doubleValue()))
            .andExpect(jsonPath("$.creatineKinaseIsoenzymes").value(DEFAULT_CREATINE_KINASE_ISOENZYMES.doubleValue()))
            .andExpect(jsonPath("$.creatinine").value(DEFAULT_CREATININE.doubleValue()))
            .andExpect(jsonPath("$.electrolytes").value(DEFAULT_ELECTROLYTES.doubleValue()))
            .andExpect(jsonPath("$.erythrocyteSedimentationRate").value(DEFAULT_ERYTHROCYTE_SEDIMENTATION_RATE.doubleValue()))
            .andExpect(jsonPath("$.glucose").value(DEFAULT_GLUCOSE.doubleValue()))
            .andExpect(jsonPath("$.hematocrit").value(DEFAULT_HEMATOCRIT.doubleValue()))
            .andExpect(jsonPath("$.hemoglobin").value(DEFAULT_HEMOGLOBIN.doubleValue()))
            .andExpect(jsonPath("$.iron").value(DEFAULT_IRON.doubleValue()))
            .andExpect(jsonPath("$.ironBindingCapacity").value(DEFAULT_IRON_BINDING_CAPACITY.doubleValue()))
            .andExpect(jsonPath("$.lactate").value(DEFAULT_LACTATE.doubleValue()))
            .andExpect(jsonPath("$.lacticDehydrogenase").value(DEFAULT_LACTIC_DEHYDROGENASE.doubleValue()))
            .andExpect(jsonPath("$.lead").value(DEFAULT_LEAD.doubleValue()))
            .andExpect(jsonPath("$.lipase").value(DEFAULT_LIPASE.doubleValue()))
            .andExpect(jsonPath("$.zinc").value(DEFAULT_ZINC.doubleValue()))
            .andExpect(jsonPath("$.lipidsCholesterol").value(DEFAULT_LIPIDS_CHOLESTEROL.doubleValue()))
            .andExpect(jsonPath("$.lipidsTriglycerides").value(DEFAULT_LIPIDS_TRIGLYCERIDES.doubleValue()))
            .andExpect(jsonPath("$.magnesium").value(DEFAULT_MAGNESIUM.doubleValue()))
            .andExpect(jsonPath("$.meanCorpuscularHemoglobin").value(DEFAULT_MEAN_CORPUSCULAR_HEMOGLOBIN.doubleValue()))
            .andExpect(jsonPath("$.meanCorpuscularHemoglobinConcentration").value(DEFAULT_MEAN_CORPUSCULAR_HEMOGLOBIN_CONCENTRATION.doubleValue()))
            .andExpect(jsonPath("$.meanCorpuscularVolume").value(DEFAULT_MEAN_CORPUSCULAR_VOLUME.doubleValue()))
            .andExpect(jsonPath("$.osmolality").value(DEFAULT_OSMOLALITY.doubleValue()))
            .andExpect(jsonPath("$.oxygenPressure").value(DEFAULT_OXYGEN_PRESSURE.doubleValue()))
            .andExpect(jsonPath("$.oxygenSaturation").value(DEFAULT_OXYGEN_SATURATION.doubleValue()))
            .andExpect(jsonPath("$.phosphataseProstatic").value(DEFAULT_PHOSPHATASE_PROSTATIC.doubleValue()))
            .andExpect(jsonPath("$.phosphatase").value(DEFAULT_PHOSPHATASE.doubleValue()))
            .andExpect(jsonPath("$.phosphorus").value(DEFAULT_PHOSPHORUS.doubleValue()))
            .andExpect(jsonPath("$.plateletCount").value(DEFAULT_PLATELET_COUNT.doubleValue()))
            .andExpect(jsonPath("$.potassium").value(DEFAULT_POTASSIUM.doubleValue()))
            .andExpect(jsonPath("$.prostateSpecificAntigen").value(DEFAULT_PROSTATE_SPECIFIC_ANTIGEN.doubleValue()))
            .andExpect(jsonPath("$.proteinsTotal").value(DEFAULT_PROTEINS_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.proteinsAlbumin").value(DEFAULT_PROTEINS_ALBUMIN.doubleValue()))
            .andExpect(jsonPath("$.proteinsGlobulin").value(DEFAULT_PROTEINS_GLOBULIN.doubleValue()))
            .andExpect(jsonPath("$.prothrombin").value(DEFAULT_PROTHROMBIN.doubleValue()))
            .andExpect(jsonPath("$.pyruvicAcid").value(DEFAULT_PYRUVIC_ACID.doubleValue()))
            .andExpect(jsonPath("$.redBloodCellCount").value(DEFAULT_RED_BLOOD_CELL_COUNT.doubleValue()))
            .andExpect(jsonPath("$.sodium").value(DEFAULT_SODIUM.doubleValue()))
            .andExpect(jsonPath("$.thyroidStimulatingHormone").value(DEFAULT_THYROID_STIMULATING_HORMONE.doubleValue()))
            .andExpect(jsonPath("$.transaminaseAlanine").value(DEFAULT_TRANSAMINASE_ALANINE.doubleValue()))
            .andExpect(jsonPath("$.transaminaseAspartate").value(DEFAULT_TRANSAMINASE_ASPARTATE.doubleValue()))
            .andExpect(jsonPath("$.ureaNitrogen").value(DEFAULT_UREA_NITROGEN.doubleValue()))
            .andExpect(jsonPath("$.bUNCreatinineRatio").value(DEFAULT_B_UN_CREATININE_RATIO.doubleValue()))
            .andExpect(jsonPath("$.uricAcid").value(DEFAULT_URIC_ACID.doubleValue()))
            .andExpect(jsonPath("$.vitaminA").value(DEFAULT_VITAMIN_A.doubleValue()))
            .andExpect(jsonPath("$.wBC").value(DEFAULT_W_BC.doubleValue()))
            .andExpect(jsonPath("$.whiteBloodCellCount").value(DEFAULT_WHITE_BLOOD_CELL_COUNT.doubleValue()))
            .andExpect(jsonPath("$.measurmentdate").value(DEFAULT_MEASURMENTDATE.toString()));
    }

    @Test
    public void getNonExistingBloodTest() throws Exception {
        // Get the bloodTest
        restBloodTestMockMvc.perform(get("/api/blood-tests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBloodTest() throws Exception {
        // Initialize the database
        bloodTestRepository.save(bloodTest);
        int databaseSizeBeforeUpdate = bloodTestRepository.findAll().size();

        // Update the bloodTest
        BloodTest updatedBloodTest = bloodTestRepository.findOne(bloodTest.getId());
        updatedBloodTest
            .userid(UPDATED_USERID)
            .hydroxyprogesterone17(UPDATED_HYDROXYPROGESTERONE_17)
            .hydroxyvitaminD25(UPDATED_HYDROXYVITAMIN_D_25)
            .acetoacetate(UPDATED_ACETOACETATE)
            .acidity(UPDATED_ACIDITY)
            .alcohol(UPDATED_ALCOHOL)
            .ammonia(UPDATED_AMMONIA)
            .amylase(UPDATED_AMYLASE)
            .ascorbicAcid(UPDATED_ASCORBIC_ACID)
            .bicarbonate(UPDATED_BICARBONATE)
            .bilirubin(UPDATED_BILIRUBIN)
            .bloodVolume(UPDATED_BLOOD_VOLUME)
            .calcium(UPDATED_CALCIUM)
            .carbonDioxidePressure(UPDATED_CARBON_DIOXIDE_PRESSURE)
            .carbonMonoxide(UPDATED_CARBON_MONOXIDE)
            .cD4CellCount(UPDATED_C_D_4_CELL_COUNT)
            .ceruloplasmin(UPDATED_CERULOPLASMIN)
            .chloride(UPDATED_CHLORIDE)
            .completeBloodCellCount(UPDATED_COMPLETE_BLOOD_CELL_COUNT)
            .copper(UPDATED_COPPER)
            .creatineKinase(UPDATED_CREATINE_KINASE)
            .creatineKinaseIsoenzymes(UPDATED_CREATINE_KINASE_ISOENZYMES)
            .creatinine(UPDATED_CREATININE)
            .electrolytes(UPDATED_ELECTROLYTES)
            .erythrocyteSedimentationRate(UPDATED_ERYTHROCYTE_SEDIMENTATION_RATE)
            .glucose(UPDATED_GLUCOSE)
            .hematocrit(UPDATED_HEMATOCRIT)
            .hemoglobin(UPDATED_HEMOGLOBIN)
            .iron(UPDATED_IRON)
            .ironBindingCapacity(UPDATED_IRON_BINDING_CAPACITY)
            .lactate(UPDATED_LACTATE)
            .lacticDehydrogenase(UPDATED_LACTIC_DEHYDROGENASE)
            .lead(UPDATED_LEAD)
            .lipase(UPDATED_LIPASE)
            .zinc(UPDATED_ZINC)
            .lipidsCholesterol(UPDATED_LIPIDS_CHOLESTEROL)
            .lipidsTriglycerides(UPDATED_LIPIDS_TRIGLYCERIDES)
            .magnesium(UPDATED_MAGNESIUM)
            .meanCorpuscularHemoglobin(UPDATED_MEAN_CORPUSCULAR_HEMOGLOBIN)
            .meanCorpuscularHemoglobinConcentration(UPDATED_MEAN_CORPUSCULAR_HEMOGLOBIN_CONCENTRATION)
            .meanCorpuscularVolume(UPDATED_MEAN_CORPUSCULAR_VOLUME)
            .osmolality(UPDATED_OSMOLALITY)
            .oxygenPressure(UPDATED_OXYGEN_PRESSURE)
            .oxygenSaturation(UPDATED_OXYGEN_SATURATION)
            .phosphataseProstatic(UPDATED_PHOSPHATASE_PROSTATIC)
            .phosphatase(UPDATED_PHOSPHATASE)
            .phosphorus(UPDATED_PHOSPHORUS)
            .plateletCount(UPDATED_PLATELET_COUNT)
            .potassium(UPDATED_POTASSIUM)
            .prostateSpecificAntigen(UPDATED_PROSTATE_SPECIFIC_ANTIGEN)
            .proteinsTotal(UPDATED_PROTEINS_TOTAL)
            .proteinsAlbumin(UPDATED_PROTEINS_ALBUMIN)
            .proteinsGlobulin(UPDATED_PROTEINS_GLOBULIN)
            .prothrombin(UPDATED_PROTHROMBIN)
            .pyruvicAcid(UPDATED_PYRUVIC_ACID)
            .redBloodCellCount(UPDATED_RED_BLOOD_CELL_COUNT)
            .sodium(UPDATED_SODIUM)
            .thyroidStimulatingHormone(UPDATED_THYROID_STIMULATING_HORMONE)
            .transaminaseAlanine(UPDATED_TRANSAMINASE_ALANINE)
            .transaminaseAspartate(UPDATED_TRANSAMINASE_ASPARTATE)
            .ureaNitrogen(UPDATED_UREA_NITROGEN)
            .bUNCreatinineRatio(UPDATED_B_UN_CREATININE_RATIO)
            .uricAcid(UPDATED_URIC_ACID)
            .vitaminA(UPDATED_VITAMIN_A)
            .wBC(UPDATED_W_BC)
            .whiteBloodCellCount(UPDATED_WHITE_BLOOD_CELL_COUNT)
            .measurmentdate(UPDATED_MEASURMENTDATE);
        BloodTestDTO bloodTestDTO = bloodTestMapper.toDto(updatedBloodTest);

        restBloodTestMockMvc.perform(put("/api/blood-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bloodTestDTO)))
            .andExpect(status().isOk());

        // Validate the BloodTest in the database
        List<BloodTest> bloodTestList = bloodTestRepository.findAll();
        assertThat(bloodTestList).hasSize(databaseSizeBeforeUpdate);
        BloodTest testBloodTest = bloodTestList.get(bloodTestList.size() - 1);
        assertThat(testBloodTest.getUserid()).isEqualTo(UPDATED_USERID);
        assertThat(testBloodTest.getHydroxyprogesterone17()).isEqualTo(UPDATED_HYDROXYPROGESTERONE_17);
        assertThat(testBloodTest.getHydroxyvitaminD25()).isEqualTo(UPDATED_HYDROXYVITAMIN_D_25);
        assertThat(testBloodTest.getAcetoacetate()).isEqualTo(UPDATED_ACETOACETATE);
        assertThat(testBloodTest.getAcidity()).isEqualTo(UPDATED_ACIDITY);
        assertThat(testBloodTest.getAlcohol()).isEqualTo(UPDATED_ALCOHOL);
        assertThat(testBloodTest.getAmmonia()).isEqualTo(UPDATED_AMMONIA);
        assertThat(testBloodTest.getAmylase()).isEqualTo(UPDATED_AMYLASE);
        assertThat(testBloodTest.getAscorbicAcid()).isEqualTo(UPDATED_ASCORBIC_ACID);
        assertThat(testBloodTest.getBicarbonate()).isEqualTo(UPDATED_BICARBONATE);
        assertThat(testBloodTest.getBilirubin()).isEqualTo(UPDATED_BILIRUBIN);
        assertThat(testBloodTest.getBloodVolume()).isEqualTo(UPDATED_BLOOD_VOLUME);
        assertThat(testBloodTest.getCalcium()).isEqualTo(UPDATED_CALCIUM);
        assertThat(testBloodTest.getCarbonDioxidePressure()).isEqualTo(UPDATED_CARBON_DIOXIDE_PRESSURE);
        assertThat(testBloodTest.getCarbonMonoxide()).isEqualTo(UPDATED_CARBON_MONOXIDE);
        assertThat(testBloodTest.getcD4CellCount()).isEqualTo(UPDATED_C_D_4_CELL_COUNT);
        assertThat(testBloodTest.getCeruloplasmin()).isEqualTo(UPDATED_CERULOPLASMIN);
        assertThat(testBloodTest.getChloride()).isEqualTo(UPDATED_CHLORIDE);
        assertThat(testBloodTest.getCompleteBloodCellCount()).isEqualTo(UPDATED_COMPLETE_BLOOD_CELL_COUNT);
        assertThat(testBloodTest.getCopper()).isEqualTo(UPDATED_COPPER);
        assertThat(testBloodTest.getCreatineKinase()).isEqualTo(UPDATED_CREATINE_KINASE);
        assertThat(testBloodTest.getCreatineKinaseIsoenzymes()).isEqualTo(UPDATED_CREATINE_KINASE_ISOENZYMES);
        assertThat(testBloodTest.getCreatinine()).isEqualTo(UPDATED_CREATININE);
        assertThat(testBloodTest.getElectrolytes()).isEqualTo(UPDATED_ELECTROLYTES);
        assertThat(testBloodTest.getErythrocyteSedimentationRate()).isEqualTo(UPDATED_ERYTHROCYTE_SEDIMENTATION_RATE);
        assertThat(testBloodTest.getGlucose()).isEqualTo(UPDATED_GLUCOSE);
        assertThat(testBloodTest.getHematocrit()).isEqualTo(UPDATED_HEMATOCRIT);
        assertThat(testBloodTest.getHemoglobin()).isEqualTo(UPDATED_HEMOGLOBIN);
        assertThat(testBloodTest.getIron()).isEqualTo(UPDATED_IRON);
        assertThat(testBloodTest.getIronBindingCapacity()).isEqualTo(UPDATED_IRON_BINDING_CAPACITY);
        assertThat(testBloodTest.getLactate()).isEqualTo(UPDATED_LACTATE);
        assertThat(testBloodTest.getLacticDehydrogenase()).isEqualTo(UPDATED_LACTIC_DEHYDROGENASE);
        assertThat(testBloodTest.getLead()).isEqualTo(UPDATED_LEAD);
        assertThat(testBloodTest.getLipase()).isEqualTo(UPDATED_LIPASE);
        assertThat(testBloodTest.getZinc()).isEqualTo(UPDATED_ZINC);
        assertThat(testBloodTest.getLipidsCholesterol()).isEqualTo(UPDATED_LIPIDS_CHOLESTEROL);
        assertThat(testBloodTest.getLipidsTriglycerides()).isEqualTo(UPDATED_LIPIDS_TRIGLYCERIDES);
        assertThat(testBloodTest.getMagnesium()).isEqualTo(UPDATED_MAGNESIUM);
        assertThat(testBloodTest.getMeanCorpuscularHemoglobin()).isEqualTo(UPDATED_MEAN_CORPUSCULAR_HEMOGLOBIN);
        assertThat(testBloodTest.getMeanCorpuscularHemoglobinConcentration()).isEqualTo(UPDATED_MEAN_CORPUSCULAR_HEMOGLOBIN_CONCENTRATION);
        assertThat(testBloodTest.getMeanCorpuscularVolume()).isEqualTo(UPDATED_MEAN_CORPUSCULAR_VOLUME);
        assertThat(testBloodTest.getOsmolality()).isEqualTo(UPDATED_OSMOLALITY);
        assertThat(testBloodTest.getOxygenPressure()).isEqualTo(UPDATED_OXYGEN_PRESSURE);
        assertThat(testBloodTest.getOxygenSaturation()).isEqualTo(UPDATED_OXYGEN_SATURATION);
        assertThat(testBloodTest.getPhosphataseProstatic()).isEqualTo(UPDATED_PHOSPHATASE_PROSTATIC);
        assertThat(testBloodTest.getPhosphatase()).isEqualTo(UPDATED_PHOSPHATASE);
        assertThat(testBloodTest.getPhosphorus()).isEqualTo(UPDATED_PHOSPHORUS);
        assertThat(testBloodTest.getPlateletCount()).isEqualTo(UPDATED_PLATELET_COUNT);
        assertThat(testBloodTest.getPotassium()).isEqualTo(UPDATED_POTASSIUM);
        assertThat(testBloodTest.getProstateSpecificAntigen()).isEqualTo(UPDATED_PROSTATE_SPECIFIC_ANTIGEN);
        assertThat(testBloodTest.getProteinsTotal()).isEqualTo(UPDATED_PROTEINS_TOTAL);
        assertThat(testBloodTest.getProteinsAlbumin()).isEqualTo(UPDATED_PROTEINS_ALBUMIN);
        assertThat(testBloodTest.getProteinsGlobulin()).isEqualTo(UPDATED_PROTEINS_GLOBULIN);
        assertThat(testBloodTest.getProthrombin()).isEqualTo(UPDATED_PROTHROMBIN);
        assertThat(testBloodTest.getPyruvicAcid()).isEqualTo(UPDATED_PYRUVIC_ACID);
        assertThat(testBloodTest.getRedBloodCellCount()).isEqualTo(UPDATED_RED_BLOOD_CELL_COUNT);
        assertThat(testBloodTest.getSodium()).isEqualTo(UPDATED_SODIUM);
        assertThat(testBloodTest.getThyroidStimulatingHormone()).isEqualTo(UPDATED_THYROID_STIMULATING_HORMONE);
        assertThat(testBloodTest.getTransaminaseAlanine()).isEqualTo(UPDATED_TRANSAMINASE_ALANINE);
        assertThat(testBloodTest.getTransaminaseAspartate()).isEqualTo(UPDATED_TRANSAMINASE_ASPARTATE);
        assertThat(testBloodTest.getUreaNitrogen()).isEqualTo(UPDATED_UREA_NITROGEN);
        assertThat(testBloodTest.getbUNCreatinineRatio()).isEqualTo(UPDATED_B_UN_CREATININE_RATIO);
        assertThat(testBloodTest.getUricAcid()).isEqualTo(UPDATED_URIC_ACID);
        assertThat(testBloodTest.getVitaminA()).isEqualTo(UPDATED_VITAMIN_A);
        assertThat(testBloodTest.getwBC()).isEqualTo(UPDATED_W_BC);
        assertThat(testBloodTest.getWhiteBloodCellCount()).isEqualTo(UPDATED_WHITE_BLOOD_CELL_COUNT);
        assertThat(testBloodTest.getMeasurmentdate()).isEqualTo(UPDATED_MEASURMENTDATE);
    }

    @Test
    public void updateNonExistingBloodTest() throws Exception {
        int databaseSizeBeforeUpdate = bloodTestRepository.findAll().size();

        // Create the BloodTest
        BloodTestDTO bloodTestDTO = bloodTestMapper.toDto(bloodTest);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBloodTestMockMvc.perform(put("/api/blood-tests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bloodTestDTO)))
            .andExpect(status().isCreated());

        // Validate the BloodTest in the database
        List<BloodTest> bloodTestList = bloodTestRepository.findAll();
        assertThat(bloodTestList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteBloodTest() throws Exception {
        // Initialize the database
        bloodTestRepository.save(bloodTest);
        int databaseSizeBeforeDelete = bloodTestRepository.findAll().size();

        // Get the bloodTest
        restBloodTestMockMvc.perform(delete("/api/blood-tests/{id}", bloodTest.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BloodTest> bloodTestList = bloodTestRepository.findAll();
        assertThat(bloodTestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BloodTest.class);
        BloodTest bloodTest1 = new BloodTest();
        bloodTest1.setId("id1");
        BloodTest bloodTest2 = new BloodTest();
        bloodTest2.setId(bloodTest1.getId());
        assertThat(bloodTest1).isEqualTo(bloodTest2);
        bloodTest2.setId("id2");
        assertThat(bloodTest1).isNotEqualTo(bloodTest2);
        bloodTest1.setId(null);
        assertThat(bloodTest1).isNotEqualTo(bloodTest2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BloodTestDTO.class);
        BloodTestDTO bloodTestDTO1 = new BloodTestDTO();
        bloodTestDTO1.setId("id1");
        BloodTestDTO bloodTestDTO2 = new BloodTestDTO();
        assertThat(bloodTestDTO1).isNotEqualTo(bloodTestDTO2);
        bloodTestDTO2.setId(bloodTestDTO1.getId());
        assertThat(bloodTestDTO1).isEqualTo(bloodTestDTO2);
        bloodTestDTO2.setId("id2");
        assertThat(bloodTestDTO1).isNotEqualTo(bloodTestDTO2);
        bloodTestDTO1.setId(null);
        assertThat(bloodTestDTO1).isNotEqualTo(bloodTestDTO2);
    }
}
