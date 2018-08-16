import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {UserRouteAccessService} from '../../shared';
import {VsRespiratoryRateComponent} from './vs-respiratory-rate.component';
import {VsRespiratoryRateDetailComponent} from './vs-respiratory-rate-detail.component';
import {VsRespiratoryRatePopupComponent} from './vs-respiratory-rate-dialog.component';
import {VsRespiratoryRateDeletePopupComponent} from './vs-respiratory-rate-delete-dialog.component';
import {VsRespiratoryRateInfoPopupComponent} from './vs-respiratory-rate-info.component';

@Injectable()
export class VsRespiratoryRateResolvePagingParams implements Resolve<any> {

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

export const vsRespiratoryRateRoute: Routes = [
    {
        path: 'vs-respiratory-rate',
        component: VsRespiratoryRateComponent,
        resolve: {
            'pagingParams': VsRespiratoryRateResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsRespiratoryRate.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vs-respiratory-rate/:id',
        component: VsRespiratoryRateDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsRespiratoryRate.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vs-respiratory-rate/patient/vs-respiratory-rate/:id',
        component: VsRespiratoryRateDetailComponent,
        data: {
            authorities: ['ROLE_DOCTOR'],
            pageTitle: 'hcPortalApp.vsRespiratoryRate.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vs-respiratory-rate/patient/:login',
        component: VsRespiratoryRateComponent,
        resolve: {
            'pagingParams': VsRespiratoryRateResolvePagingParams
        },
        data: {
            authorities: ['ROLE_DOCTOR'],
            pageTitle: 'hcPortalApp.vsRespiratoryRate.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vsRespiratoryRatePopupRoute: Routes = [
    {
        path: 'vs-respiratory-rate-new',
        component: VsRespiratoryRatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsRespiratoryRate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vs-respiratory-rate/:id/edit',
        component: VsRespiratoryRatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsRespiratoryRate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vs-respiratory-rate/:id/delete',
        component: VsRespiratoryRateDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsRespiratoryRate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vs-respiratory-rate-info',
        component: VsRespiratoryRateInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsRespiratoryRate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
