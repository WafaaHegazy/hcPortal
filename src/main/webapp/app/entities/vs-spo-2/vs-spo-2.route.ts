import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {UserRouteAccessService} from '../../shared';
import {VsSpo2Component} from './vs-spo-2.component';
import {VsSpo2DetailComponent} from './vs-spo-2-detail.component';
import {VsSpo2PopupComponent} from './vs-spo-2-dialog.component';
import {VsSpo2DeletePopupComponent} from './vs-spo-2-delete-dialog.component';
import {VsSpo2InfoPopupComponent} from './vs-spo-2-info.component';

@Injectable()
export class VsSpo2ResolvePagingParams implements Resolve<any> {

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

export const vsSpo2Route: Routes = [
    {
        path: 'vs-spo-2',
        component: VsSpo2Component,
        resolve: {
            'pagingParams': VsSpo2ResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsSpo2.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vs-spo-2/:id',
        component: VsSpo2DetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsSpo2.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vs-spo-2/patient/vs-spo-2/:id',
        component: VsSpo2DetailComponent,
        data: {
            authorities: ['ROLE_DOCTOR'],
            pageTitle: 'hcPortalApp.vsSpo2.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'vs-spo-2/patient/:login',
        component: VsSpo2Component,
        resolve: {
            'pagingParams': VsSpo2ResolvePagingParams
        },
        data: {
            authorities: ['ROLE_DOCTOR'],
            pageTitle: 'hcPortalApp.vsSpo2.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vsSpo2PopupRoute: Routes = [
    {
        path: 'vs-spo-2-new',
        component: VsSpo2PopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsSpo2.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vs-spo-2/:id/edit',
        component: VsSpo2PopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsSpo2.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vs-spo-2/:id/delete',
        component: VsSpo2DeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsSpo2.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vs-spo-2-info',
        component: VsSpo2InfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsSpo2.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
