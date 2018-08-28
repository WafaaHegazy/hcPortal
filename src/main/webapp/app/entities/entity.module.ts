import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { HcPortalVsSpo2Module } from './vs-spo-2/vs-spo-2.module';
import { HcPortalVsRespiratoryRateModule } from './vs-respiratory-rate/vs-respiratory-rate.module';
import { HcPortalVsHeartRateModule } from './vs-heart-rate/vs-heart-rate.module';
import { HcPortalVsBodyTemperatureModule } from './vs-body-temperature/vs-body-temperature.module';
import { HcPortalVsBloodPressureModule } from './vs-blood-pressure/vs-blood-pressure.module';
import { HcPortalMenstrualCycleModule } from './menstrual-cycle/menstrual-cycle.module';
import { HcPortalDiabetesSugarTestModule } from './diabetes-sugar-test/diabetes-sugar-test.module';
import { HcPortalBloodTestModule } from './blood-test/blood-test.module';
import { HcPortalDentistVisitModule } from './dentist-visit/dentist-visit.module';
import { HcPortalDentistNextVisitModule } from './dentist-next-visit/dentist-next-visit.module';
import {HcPortalDrPatientsModule} from './dr-patients/dr-patients.module';
import {HcPortalPtDoctorsModule} from './pt-doctors/pt-doctors.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        HcPortalVsSpo2Module,
        HcPortalVsRespiratoryRateModule,
        HcPortalVsHeartRateModule,
        HcPortalVsBodyTemperatureModule,
        HcPortalVsBloodPressureModule,
        HcPortalMenstrualCycleModule,
        HcPortalDiabetesSugarTestModule,
        HcPortalBloodTestModule,
        HcPortalDentistVisitModule,
        HcPortalDentistNextVisitModule,
        HcPortalDrPatientsModule,
        HcPortalPtDoctorsModule
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HcPortalEntityModule {}
