import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule} from '@angular/router';

import {HcPortalSharedModule} from '../../shared';
import {
    DrPatientsService,
    DrPatientsPopupService,
    DrPatientsComponent,
    DrPatientsDetailComponent,
    DrPatientsDetailPopupComponent,
    DrPatientsDialogComponent,
    DrPatientsPopupComponent,
    DrPatientsRemovePopupComponent,
    DrPatientsRemoveDialogComponent,
    DrPatientsRoute,
    DrPatientsPopupRoute,
    DrPatientsResolvePagingParams,
    DrPatientsInfoComponent,
    DrPatientsInfoPopupComponent
} from './';

const ENTITY_STATES = [
    ...DrPatientsRoute,
    ...DrPatientsPopupRoute,
];

@NgModule({
    imports: [
        HcPortalSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DrPatientsComponent,
        DrPatientsDetailComponent,
        DrPatientsDialogComponent,
        DrPatientsRemoveDialogComponent,
        DrPatientsPopupComponent,
        DrPatientsRemovePopupComponent,
        DrPatientsInfoComponent,
        DrPatientsInfoPopupComponent,
        DrPatientsDetailPopupComponent
    ],
    entryComponents: [
        DrPatientsComponent,
        DrPatientsDialogComponent,
        DrPatientsPopupComponent,
        DrPatientsRemoveDialogComponent,
        DrPatientsRemovePopupComponent,
        DrPatientsInfoComponent,
        DrPatientsInfoPopupComponent,
        DrPatientsDetailPopupComponent
    ],
    providers: [
        DrPatientsService,
        DrPatientsPopupService,
        DrPatientsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HcPortalDrPatientsModule {
}
