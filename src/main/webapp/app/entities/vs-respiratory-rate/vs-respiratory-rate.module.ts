import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule} from '@angular/router';

import {HcPortalSharedModule} from '../../shared';
import {
    VsRespiratoryRateService,
    VsRespiratoryRatePopupService,
    VsRespiratoryRateComponent,
    VsRespiratoryRateDetailComponent,
    VsRespiratoryRateDialogComponent,
    VsRespiratoryRatePopupComponent,
    VsRespiratoryRateDeletePopupComponent,
    VsRespiratoryRateDeleteDialogComponent,
    vsRespiratoryRateRoute,
    vsRespiratoryRatePopupRoute,
    VsRespiratoryRateResolvePagingParams,
    VsRespiratoryRateInfoComponent,
    VsRespiratoryRateInfoPopupComponent
} from './';

const ENTITY_STATES = [
    ...vsRespiratoryRateRoute,
    ...vsRespiratoryRatePopupRoute,
];

@NgModule({
    imports: [
        HcPortalSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        VsRespiratoryRateComponent,
        VsRespiratoryRateDetailComponent,
        VsRespiratoryRateDialogComponent,
        VsRespiratoryRateDeleteDialogComponent,
        VsRespiratoryRatePopupComponent,
        VsRespiratoryRateDeletePopupComponent,
        VsRespiratoryRateInfoComponent,
        VsRespiratoryRateInfoPopupComponent
    ],
    entryComponents: [
        VsRespiratoryRateComponent,
        VsRespiratoryRateDialogComponent,
        VsRespiratoryRatePopupComponent,
        VsRespiratoryRateDeleteDialogComponent,
        VsRespiratoryRateDeletePopupComponent,
        VsRespiratoryRateInfoComponent,
        VsRespiratoryRateInfoPopupComponent
    ],
    providers: [
        VsRespiratoryRateService,
        VsRespiratoryRatePopupService,
        VsRespiratoryRateResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HcPortalVsRespiratoryRateModule {
}
