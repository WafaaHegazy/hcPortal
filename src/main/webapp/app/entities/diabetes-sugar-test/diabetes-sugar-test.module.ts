import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {RouterModule} from '@angular/router';

import {HcPortalSharedModule} from '../../shared';
import {
    DiabetesSugarTestService,
    DiabetesSugarTestPopupService,
    DiabetesSugarTestComponent,
    DiabetesSugarTestDetailComponent,
    DiabetesSugarTestDialogComponent,
    DiabetesSugarTestPopupComponent,
    DiabetesSugarTestDeletePopupComponent,
    DiabetesSugarTestDeleteDialogComponent,
    diabetesSugarTestRoute,
    diabetesSugarTestPopupRoute,
    DiabetesSugarTestResolvePagingParams,
    DiabetesSugarTestInfoComponent,
    DiabetesSugarTestInfoPopupComponent
} from './';

const ENTITY_STATES = [
    ...diabetesSugarTestRoute,
    ...diabetesSugarTestPopupRoute,
];

@NgModule({
    imports: [
        HcPortalSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        DiabetesSugarTestComponent,
        DiabetesSugarTestDetailComponent,
        DiabetesSugarTestDialogComponent,
        DiabetesSugarTestDeleteDialogComponent,
        DiabetesSugarTestPopupComponent,
        DiabetesSugarTestDeletePopupComponent,
        DiabetesSugarTestInfoComponent,
        DiabetesSugarTestInfoPopupComponent
    ],
    entryComponents: [
        DiabetesSugarTestComponent,
        DiabetesSugarTestDialogComponent,
        DiabetesSugarTestPopupComponent,
        DiabetesSugarTestDeleteDialogComponent,
        DiabetesSugarTestDeletePopupComponent,
        DiabetesSugarTestInfoComponent,
        DiabetesSugarTestInfoPopupComponent
    ],
    providers: [
        DiabetesSugarTestService,
        DiabetesSugarTestPopupService,
        DiabetesSugarTestResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HcPortalDiabetesSugarTestModule {
}
