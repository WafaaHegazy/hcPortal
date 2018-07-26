import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule} from '@angular/router';
import {ChartsModule} from 'ng2-charts';

import {HcPortalSharedModule} from '../../shared';
import {
    VsHeartRateService,
    VsHeartRatePopupService,
    VsHeartRateComponent,
    VsHeartRateDetailComponent,
    VsHeartRateDialogComponent,
    VsHeartRatePopupComponent,
    VsHeartRateDeletePopupComponent,
    VsHeartRateDeleteDialogComponent,
    vsHeartRateRoute,
    vsHeartRatePopupRoute,
    VsHeartRateResolvePagingParams,
    VsHeartRateInfoComponent,
    VsHeartRateInfoPopupComponent,
} from './';

const ENTITY_STATES = [
    ...vsHeartRateRoute,
    ...vsHeartRatePopupRoute,
];

@NgModule({
    imports: [
        HcPortalSharedModule,
        ChartsModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        VsHeartRateComponent,
        VsHeartRateDetailComponent,
        VsHeartRateDialogComponent,
        VsHeartRateDeleteDialogComponent,
        VsHeartRatePopupComponent,
        VsHeartRateDeletePopupComponent,
        VsHeartRateInfoComponent,
        VsHeartRateInfoPopupComponent,
    ],
    entryComponents: [
        VsHeartRateComponent,
        VsHeartRateDialogComponent,
        VsHeartRatePopupComponent,
        VsHeartRateDeleteDialogComponent,
        VsHeartRateDeletePopupComponent,
        VsHeartRateInfoComponent,
        VsHeartRateInfoPopupComponent,
    ],
    providers: [
        VsHeartRateService,
        VsHeartRatePopupService,
        VsHeartRateResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HcPortalVsHeartRateModule {
}
