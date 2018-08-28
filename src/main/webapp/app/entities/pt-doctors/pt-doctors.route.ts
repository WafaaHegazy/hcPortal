import {Injectable} from '@angular/core';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes} from '@angular/router';
import {JhiPaginationUtil} from 'ng-jhipster';

import {UserRouteAccessService} from '../../shared';
import {PtDoctorsComponent} from './pt-doctors.component';
import {PtDoctorsInfoPopupComponent} from './pt-doctors-info.component';

@Injectable()
export class PtDoctorsResolvePagingParams implements Resolve<any> {

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

export const PtDoctorsRoute: Routes = [
    {
        path: 'pt-doctors',
        component: PtDoctorsComponent,
        resolve: {
            'pagingParams': PtDoctorsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.ptDoctors.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const PtDoctorsPopupRoute: Routes = [
    {
        path: 'pt-doctors-info',
        component: PtDoctorsInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'hcPortalApp.ptDoctors.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
