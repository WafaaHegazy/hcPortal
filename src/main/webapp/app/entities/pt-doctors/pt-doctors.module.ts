import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule} from '@angular/router';

import {HcPortalSharedModule} from '../../shared';
import {
    PtDoctorsService,
    PtDoctorsPopupService,
    PtDoctorsComponent,
    PtDoctorsRoute,
    PtDoctorsPopupRoute,
    PtDoctorsResolvePagingParams,
    PtDoctorsInfoComponent,
    PtDoctorsInfoPopupComponent
} from './';

const ENTITY_STATES = [
    ...PtDoctorsRoute,
    ...PtDoctorsPopupRoute,
];

@NgModule({
    imports: [
        HcPortalSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PtDoctorsComponent,
        PtDoctorsInfoComponent,
        PtDoctorsInfoPopupComponent,
    ],
    entryComponents: [
        PtDoctorsComponent,
        PtDoctorsInfoComponent,
        PtDoctorsInfoPopupComponent,
    ],
    providers: [
        PtDoctorsService,
        PtDoctorsPopupService,
        PtDoctorsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HcPortalPtDoctorsModule {
}
