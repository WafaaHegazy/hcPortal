import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {UserRouteAccessService} from '../../shared';
import {VsBloodPressureComponent} from './vs-blood-pressure.component';
import {VsBloodPressureDetailComponent} from './vs-blood-pressure-detail.component';
import {VsBloodPressurePopupComponent} from './vs-blood-pressure-dialog.component';
import {VsBloodPressureDeletePopupComponent} from './vs-blood-pressure-delete-dialog.component';
import {VsBloodPressureInfoPopupComponent} from './vs-blood-pressure-info.component';

@Injectable()
export class VsBloodPressureResolvePagingParams implements Resolve<any> {

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

export const vsBloodPressureRoute: Routes = [
    {
        path: 'vs-blood-pressure',
        component: VsBloodPressureComponent,
        resolve: {
            'pagingParams': VsBloodPressureResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsBloodPressure.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vs-blood-pressure/:id',
        component: VsBloodPressureDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsBloodPressure.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vs-blood-pressure/patient/vs-blood-pressure/:id',
        component: VsBloodPressureDetailComponent,
        data: {
            authorities: ['ROLE_DOCTOR'],
            pageTitle: 'hcPortalApp.vsBloodPressure.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vs-blood-pressure/patient/:login',
        component: VsBloodPressureComponent,
        resolve: {
            'pagingParams': VsBloodPressureResolvePagingParams
        },
        data: {
            authorities: ['ROLE_DOCTOR'],
            pageTitle: 'hcPortalApp.vsBloodPressure.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vsBloodPressurePopupRoute: Routes = [
    {
        path: 'vs-blood-pressure-new',
        component: VsBloodPressurePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsBloodPressure.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vs-blood-pressure/:id/edit',
        component: VsBloodPressurePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsBloodPressure.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vs-blood-pressure/:id/delete',
        component: VsBloodPressureDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsBloodPressure.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vs-blood-pressure-info',
        component: VsBloodPressureInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsBloodPressure.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
