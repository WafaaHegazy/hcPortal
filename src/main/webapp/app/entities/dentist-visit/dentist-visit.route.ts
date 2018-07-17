import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {UserRouteAccessService} from '../../shared';
import {DentistVisitComponent} from './dentist-visit.component';
import {DentistVisitDetailComponent} from './dentist-visit-detail.component';
import {DentistVisitPopupComponent} from './dentist-visit-dialog.component';
import {DentistVisitDeletePopupComponent} from './dentist-visit-delete-dialog.component';
import {DentistVisitInfoPopupComponent} from './dentist-visit-info.component';

@Injectable()
export class DentistVisitResolvePagingParams implements Resolve<any> {

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

export const dentistVisitRoute: Routes = [
    {
        path: 'dentist-visit',
        component: DentistVisitComponent,
        resolve: {
            'pagingParams': DentistVisitResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.dentistVisit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'dentist-visit/:id',
        component: DentistVisitDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.dentistVisit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const dentistVisitPopupRoute: Routes = [
    {
        path: 'dentist-visit-new',
        component: DentistVisitPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.dentistVisit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dentist-visit/:id/edit',
        component: DentistVisitPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.dentistVisit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dentist-visit/:id/delete',
        component: DentistVisitDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.dentistVisit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'dentist-visit-info',
        component: DentistVisitInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.dentistVisit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
