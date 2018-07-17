import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule} from '@angular/router';

import {HcPortalSharedModule} from '../../shared';
import {
    DentistVisitService,
    DentistVisitPopupService,
    DentistVisitComponent,
    DentistVisitDetailComponent,
    DentistVisitDialogComponent,
    DentistVisitPopupComponent,
    DentistVisitDeletePopupComponent,
    DentistVisitDeleteDialogComponent,
    dentistVisitRoute,
    dentistVisitPopupRoute,
    DentistVisitResolvePagingParams,
    DentistVisitInfoComponent,
    DentistVisitInfoPopupComponent
} from './';

const ENTITY_STATES = [
    ...dentistVisitRoute,
    ...dentistVisitPopupRoute,
];

@NgModule({
    imports: [
        HcPortalSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DentistVisitComponent,
        DentistVisitDetailComponent,
        DentistVisitDialogComponent,
        DentistVisitDeleteDialogComponent,
        DentistVisitPopupComponent,
        DentistVisitDeletePopupComponent,
        DentistVisitInfoComponent,
        DentistVisitInfoPopupComponent
    ],
    entryComponents: [
        DentistVisitComponent,
        DentistVisitDialogComponent,
        DentistVisitPopupComponent,
        DentistVisitDeleteDialogComponent,
        DentistVisitDeletePopupComponent,
        DentistVisitInfoComponent,
        DentistVisitInfoPopupComponent
    ],
    providers: [
        DentistVisitService,
        DentistVisitPopupService,
        DentistVisitResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HcPortalDentistVisitModule {
}
