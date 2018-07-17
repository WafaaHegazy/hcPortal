import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule} from '@angular/router';

import {HcPortalSharedModule} from '../../shared';
import {
    VsSpo2Service,
    VsSpo2PopupService,
    VsSpo2Component,
    VsSpo2DetailComponent,
    VsSpo2DialogComponent,
    VsSpo2PopupComponent,
    VsSpo2DeletePopupComponent,
    VsSpo2DeleteDialogComponent,
    vsSpo2Route,
    vsSpo2PopupRoute,
    VsSpo2ResolvePagingParams,
    VsSpo2InfoComponent,
    VsSpo2InfoPopupComponent
} from './';

const ENTITY_STATES = [
    ...vsSpo2Route,
    ...vsSpo2PopupRoute,
];

@NgModule({
    imports: [
        HcPortalSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        VsSpo2Component,
        VsSpo2DetailComponent,
        VsSpo2DialogComponent,
        VsSpo2DeleteDialogComponent,
        VsSpo2PopupComponent,
        VsSpo2DeletePopupComponent,
        VsSpo2InfoComponent,
        VsSpo2InfoPopupComponent
    ],
    entryComponents: [
        VsSpo2Component,
        VsSpo2DialogComponent,
        VsSpo2PopupComponent,
        VsSpo2DeleteDialogComponent,
        VsSpo2DeletePopupComponent,
        VsSpo2InfoComponent,
        VsSpo2InfoPopupComponent
    ],
    providers: [
        VsSpo2Service,
        VsSpo2PopupService,
        VsSpo2ResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HcPortalVsSpo2Module {
}
