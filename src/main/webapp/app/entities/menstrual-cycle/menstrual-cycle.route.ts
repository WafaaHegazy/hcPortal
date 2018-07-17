import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {UserRouteAccessService} from '../../shared';
import {MenstrualCycleComponent} from './menstrual-cycle.component';
import {MenstrualCycleDetailComponent} from './menstrual-cycle-detail.component';
import {MenstrualCyclePopupComponent} from './menstrual-cycle-dialog.component';
import {MenstrualCycleDeletePopupComponent} from './menstrual-cycle-delete-dialog.component';
import {MenstrualCycleInfoPopupComponent} from './menstrual-cycle-info.component';

@Injectable()
export class MenstrualCycleResolvePagingParams implements Resolve<any> {

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

export const menstrualCycleRoute: Routes = [
    {
        path: 'menstrual-cycle',
        component: MenstrualCycleComponent,
        resolve: {
            'pagingParams': MenstrualCycleResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.menstrualCycle.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'menstrual-cycle/:id',
        component: MenstrualCycleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.menstrualCycle.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const menstrualCyclePopupRoute: Routes = [
    {
        path: 'menstrual-cycle-new',
        component: MenstrualCyclePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.menstrualCycle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'menstrual-cycle/:id/edit',
        component: MenstrualCyclePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.menstrualCycle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'menstrual-cycle/:id/delete',
        component: MenstrualCycleDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.menstrualCycle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'menstrual-cycle-info',
        component: MenstrualCycleInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.menstrualCycle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
