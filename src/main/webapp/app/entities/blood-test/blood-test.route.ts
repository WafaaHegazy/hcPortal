import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {UserRouteAccessService} from '../../shared';
import {BloodTestComponent} from './blood-test.component';
import {BloodTestDetailComponent} from './blood-test-detail.component';
import {BloodTestPopupComponent} from './blood-test-dialog.component';
import {BloodTestDeletePopupComponent} from './blood-test-delete-dialog.component';
import {BloodTestInfoPopupComponent} from './blood-test-info.component';

@Injectable()
export class BloodTestResolvePagingParams implements Resolve<any> {

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

export const bloodTestRoute: Routes = [
    {
        path: 'blood-test',
        component: BloodTestComponent,
        resolve: {
            'pagingParams': BloodTestResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.bloodTest.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'blood-test/:id',
        component: BloodTestDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.bloodTest.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bloodTestPopupRoute: Routes = [
    {
        path: 'blood-test-new',
        component: BloodTestPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.bloodTest.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'blood-test/:id/edit',
        component: BloodTestPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.bloodTest.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'blood-test/:id/delete',
        component: BloodTestDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.bloodTest.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'blood-test-info',
        component: BloodTestInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.bloodTest.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
