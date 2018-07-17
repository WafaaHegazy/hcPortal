import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {UserRouteAccessService} from '../../shared';
import {DiabetesSugarTestComponent} from './diabetes-sugar-test.component';
import {DiabetesSugarTestDetailComponent} from './diabetes-sugar-test-detail.component';
import {DiabetesSugarTestPopupComponent} from './diabetes-sugar-test-dialog.component';
import {DiabetesSugarTestDeletePopupComponent} from './diabetes-sugar-test-delete-dialog.component';
import {DiabetesSugarTestInfoPopupComponent} from './diabetes-sugar-test-info.component';

@Injectable()
export class DiabetesSugarTestResolvePagingParams implements Resolve<any> {

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

export const diabetesSugarTestRoute: Routes = [
    {
        path: 'diabetes-sugar-test',
        component: DiabetesSugarTestComponent,
        resolve: {
            'pagingParams': DiabetesSugarTestResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.diabetesSugarTest.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'diabetes-sugar-test/:id',
        component: DiabetesSugarTestDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.diabetesSugarTest.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const diabetesSugarTestPopupRoute: Routes = [
    {
        path: 'diabetes-sugar-test-new',
        component: DiabetesSugarTestPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.diabetesSugarTest.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'diabetes-sugar-test/:id/edit',
        component: DiabetesSugarTestPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.diabetesSugarTest.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'diabetes-sugar-test/:id/delete',
        component: DiabetesSugarTestDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.diabetesSugarTest.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'diabetes-sugar-test-info',
        component: DiabetesSugarTestInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.diabetesSugarTest.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
