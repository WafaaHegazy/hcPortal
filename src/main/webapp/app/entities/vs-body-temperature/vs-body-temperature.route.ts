import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {UserRouteAccessService} from '../../shared';
import {VsBodyTemperatureComponent} from './vs-body-temperature.component';
import {VsBodyTemperatureDetailComponent} from './vs-body-temperature-detail.component';
import {VsBodyTemperaturePopupComponent} from './vs-body-temperature-dialog.component';
import {VsBodyTemperatureDeletePopupComponent} from './vs-body-temperature-delete-dialog.component';
import {VsBodyTemperatureInfoPopupComponent} from './vs-body-temperature-info.component';

@Injectable()
export class VsBodyTemperatureResolvePagingParams implements Resolve<any> {

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

export const vsBodyTemperatureRoute: Routes = [
    {
        path: 'vs-body-temperature',
        component: VsBodyTemperatureComponent,
        resolve: {
            'pagingParams': VsBodyTemperatureResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsBodyTemperature.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vs-body-temperature/:id',
        component: VsBodyTemperatureDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsBodyTemperature.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vs-body-temperature/patient/vs-body-temperature/:id',
        component: VsBodyTemperatureDetailComponent,
        data: {
            authorities: ['ROLE_DOCTOR'],
            pageTitle: 'hcPortalApp.vsBodyTemperature.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vs-body-temperature/patient/:login',
        component: VsBodyTemperatureComponent,
        resolve: {
            'pagingParams': VsBodyTemperatureResolvePagingParams
        },
        data: {
            authorities: ['ROLE_DOCTOR'],
            pageTitle: 'hcPortalApp.vsBodyTemperature.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vsBodyTemperaturePopupRoute: Routes = [
    {
        path: 'vs-body-temperature-new',
        component: VsBodyTemperaturePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsBodyTemperature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vs-body-temperature/:id/edit',
        component: VsBodyTemperaturePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsBodyTemperature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vs-body-temperature/:id/delete',
        component: VsBodyTemperatureDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsBodyTemperature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vs-body-temperature-info',
        component: VsBodyTemperatureInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsBodyTemperature.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
