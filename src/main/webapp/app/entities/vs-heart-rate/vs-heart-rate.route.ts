import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {UserRouteAccessService} from '../../shared';
import {VsHeartRateComponent} from './vs-heart-rate.component';
import {VsHeartRateDetailComponent} from './vs-heart-rate-detail.component';
import {VsHeartRatePopupComponent} from './vs-heart-rate-dialog.component';
import {VsHeartRateDeletePopupComponent} from './vs-heart-rate-delete-dialog.component';
import {VsHeartRateInfoPopupComponent} from './vs-heart-rate-info.component';

@Injectable()
export class VsHeartRateResolvePagingParams implements Resolve<any> {

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

export const vsHeartRateRoute: Routes = [
    {
        path: 'vs-heart-rate',
        component: VsHeartRateComponent,
        resolve: {
            'pagingParams': VsHeartRateResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsHeartRate.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'vs-heart-rate/:id',
        component: VsHeartRateDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsHeartRate.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vsHeartRatePopupRoute: Routes = [
    {
        path: 'vs-heart-rate-new',
        component: VsHeartRatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsHeartRate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vs-heart-rate/:id/edit',
        component: VsHeartRatePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsHeartRate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vs-heart-rate/:id/delete',
        component: VsHeartRateDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsHeartRate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vs-heart-rate-info',
        component: VsHeartRateInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.vsHeartRate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
