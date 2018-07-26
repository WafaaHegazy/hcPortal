import {Component, OnInit, OnDestroy} from '@angular/core';
import {HttpResponse, HttpErrorResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Subscription';
import {JhiEventManager, JhiParseLinks, JhiAlertService} from 'ng-jhipster';

import {VsRespiratoryRate} from './vs-respiratory-rate.model';
import {VsRespiratoryRateService} from './vs-respiratory-rate.service';
import {ITEMS_PER_PAGE, Principal} from '../../shared';

@Component({
    selector: 'jhi-vs-respiratory-rate',
    templateUrl: './vs-respiratory-rate.component.html'
})
export class VsRespiratoryRateComponent implements OnInit, OnDestroy {

    currentAccount: any;
    vsRespiratoryRates: VsRespiratoryRate[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    hideChart: boolean;
    lineChartData: Array<object> = [
        {
            data: [],
            label: 'Respiratory Rate (Breaths Per Minute)'
        },
        {
            data: [],
            label: 'Respiratory Rate (Mean)'
        },
        {
            data: [],
            label: 'Respiratory Rate (Median)'
        }
    ];
    lineChartLabels: Array<any> = [];
    lineChartOptions: any = {
        responsive: true
    };
    lineChartColors: Array<any> = [
        { // grey
            backgroundColor: 'rgba(148,159,177,0.2)',
            borderColor: 'rgba(148,159,177,1)',
            pointBackgroundColor: 'rgba(148,159,177,1)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgba(148,159,177,0.8)'
        },
        { // red red
            backgroundColor: 'transparent',
            borderColor: 'rgb(255, 0, 0)',
            pointBackgroundColor: 'rgb(255, 0, 0)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: 'rgb(255, 0, 0)',
            pointHoverBorderColor: 'rgba(77,83,96,1)'
        },
        { // blue blue
            backgroundColor: 'transparent',
            borderColor: 'rgb(0, 0, 255)',
            pointBackgroundColor: 'rgb(0, 0, 255)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: 'rgb(0, 0, 255)',
            pointHoverBorderColor: 'rgba(77,83,96,1)'
        }
    ];
    lineChartLegend = true;
    lineChartType = 'line';

    constructor(
        private vsRespiratoryRateService: VsRespiratoryRateService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe((data) => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
        this.hideChart = true;
    }

    loadAll() {
        this.vsRespiratoryRateService.query({
            userids: this.principal.getLogin(),
            page: this.page - 1,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: HttpResponse<VsRespiratoryRate[]>) => this.onSuccess(res.body, res.headers),
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/vs-respiratory-rate'], {
            queryParams:
                {
                    page: this.page,
                    size: this.itemsPerPage,
                    sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
                }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate(['/vs-respiratory-rate', {
            page: this.page,
            sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
        }]);
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInVsRespiratoryRates();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: VsRespiratoryRate) {
        return item.id;
    }

    registerChangeInVsRespiratoryRates() {
        this.eventSubscriber = this.eventManager.subscribe('vsRespiratoryRateListModification', (response) => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        this.queryCount = this.totalItems;
        // this.page = pagingParams.page;
        this.vsRespiratoryRates = data;
        for (let i = 0; i < this.vsRespiratoryRates.length; i++) {
            this.lineChartData[0]['data'].push(this.vsRespiratoryRates[i].bpm);
            this.lineChartLabels.push(this.vsRespiratoryRates[i].measurmentdate.toString().substr(0, 21));
        }
        const rrSum = this.lineChartData[0]['data'].reduce((a, b) => a + b, 0);
        let median = 0;
        const sortedArr = this.lineChartData[0]['data'].sort((a, b) => a - b);

        if (sortedArr.length % 2 !== 0) {
            median = sortedArr[(sortedArr.length - 1) / 2];
        } else {
            median = (sortedArr[sortedArr.length / 2] + sortedArr[(sortedArr.length / 2) - 1]) / 2;
        }
        for (let i = 0; i < this.vsRespiratoryRates.length; i++) {
            this.lineChartData[1]['data'].push(rrSum / this.lineChartData[0]['data'].length);
            this.lineChartData[2]['data'].push(median);
        }
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
