import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule} from '@angular/router';

import {HcPortalSharedModule} from '../../shared';
import {
    MenstrualCycleService,
    MenstrualCyclePopupService,
    MenstrualCycleComponent,
    MenstrualCycleDetailComponent,
    MenstrualCycleDialogComponent,
    MenstrualCyclePopupComponent,
    MenstrualCycleDeletePopupComponent,
    MenstrualCycleDeleteDialogComponent,
    menstrualCycleRoute,
    menstrualCyclePopupRoute,
    MenstrualCycleResolvePagingParams,
    MenstrualCycleInfoComponent,
    MenstrualCycleInfoPopupComponent
} from './';

const ENTITY_STATES = [
    ...menstrualCycleRoute,
    ...menstrualCyclePopupRoute,
];

@NgModule({
    imports: [
        HcPortalSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MenstrualCycleComponent,
        MenstrualCycleDetailComponent,
        MenstrualCycleDialogComponent,
        MenstrualCycleDeleteDialogComponent,
        MenstrualCyclePopupComponent,
        MenstrualCycleDeletePopupComponent,
        MenstrualCycleInfoComponent,
        MenstrualCycleInfoPopupComponent
    ],
    entryComponents: [
        MenstrualCycleComponent,
        MenstrualCycleDialogComponent,
        MenstrualCyclePopupComponent,
        MenstrualCycleDeleteDialogComponent,
        MenstrualCycleDeletePopupComponent,
        MenstrualCycleInfoComponent,
        MenstrualCycleInfoPopupComponent
    ],
    providers: [
        MenstrualCycleService,
        MenstrualCyclePopupService,
        MenstrualCycleResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HcPortalMenstrualCycleModule {
}
