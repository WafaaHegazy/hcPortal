import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {UserRouteAccessService} from '../../shared';
import {DrPatientsComponent} from './dr-patients.component';
import {DrPatientsDetailComponent, DrPatientsDetailPopupComponent} from './dr-patients-detail.component';
import {DrPatientsPopupComponent} from './dr-patients-dialog.component';
import {DrPatientsRemovePopupComponent} from './dr-patients-remove-dialog.component';
import {DrPatientsInfoPopupComponent} from './dr-patients-info.component';

@Injectable()
export class DrPatientsResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
        };
    }
}

export const DrPatientsRoute: Routes = [
    {
        path: 'dr-patients',
        component: DrPatientsComponent,
        resolve: {
            'pagingParams': DrPatientsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_DOCTOR'],
            pageTitle: 'hcPortalApp.drPatients.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dr-patients/:id',
        component: DrPatientsDetailComponent,
        data: {
            authorities: ['ROLE_DOCTOR'],
            pageTitle: 'hcPortalApp.drPatients.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const DrPatientsPopupRoute: Routes = [
    {
        path: 'dr-patients-new',
        component: DrPatientsPopupComponent,
        data: {
            authorities: ['ROLE_DOCTOR'],
            pageTitle: 'hcPortalApp.drPatients.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dr-patients/:id/edit',
        component: DrPatientsPopupComponent,
        data: {
            authorities: ['ROLE_DOCTOR'],
            pageTitle: 'hcPortalApp.drPatients.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dr-patients/:id/delete',
        component: DrPatientsRemovePopupComponent,
        data: {
            authorities: ['ROLE_DOCTOR'],
            pageTitle: 'hcPortalApp.drPatients.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dr-patients-info',
        component: DrPatientsInfoPopupComponent,
        data: {
            authorities: ['ROLE_DOCTOR'],
            pageTitle: 'hcPortalApp.drPatients.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dr-patients-detail/:id/:name',
        component: DrPatientsDetailPopupComponent,
        data: {
            authorities: ['ROLE_DOCTOR'],
            pageTitle: 'hcPortalApp.drPatients.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
