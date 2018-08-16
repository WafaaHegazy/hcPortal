import {Component, OnInit, OnDestroy} from '@angular/core';
import {HttpResponse, HttpErrorResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Subscription';
import {JhiEventManager, JhiParseLinks, JhiAlertService} from 'ng-jhipster';

import {VsBloodPressure} from './vs-blood-pressure.model';
import {VsBloodPressureService} from './vs-blood-pressure.service';
import {ITEMS_PER_PAGE, Principal} from '../../shared';

@Component({
    selector: 'jhi-vs-blood-pressure',
    templateUrl: './vs-blood-pressure.component.html'
})
export class VsBloodPressureComponent implements OnInit, OnDestroy {

    currentAccount: any;
    vsBloodPressures: VsBloodPressure[];
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
    patient: string;
    hideChart: boolean;
    lineChartData: Array<object> = [
        {
            data: [],
            label: 'Blood Pressure (Systolic)'
        },
        {
            data: [],
            label: 'Blood Pressure (Diastolic)'
        },
        {
            data: [],
            label: 'Systolic BP (Mean)'
        },
        {
            data: [],
            label: 'Diastolic BP (Mean)'
        },
        {
            data: [],
            label: 'Systolic BP (Median)'
        },
        {
            data: [],
            label: 'Diastolic BP (Median)'
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
        { // dark grey
            backgroundColor: 'rgba(77,83,96,0.2)',
            borderColor: 'rgba(77,83,96,1)',
            pointBackgroundColor: 'rgba(77,83,96,1)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgba(77,83,96,1)'
        },
        { // red red
            backgroundColor: 'transparent',
            borderColor: 'rgb(255, 0, 0)',
            pointBackgroundColor: 'rgb(255, 0, 0)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: 'rgb(255, 0, 0)',
            pointHoverBorderColor: 'rgba(77,83,96,1)'
        },
        { // dark red
            backgroundColor: 'transparent',
            borderColor: 'rgb(168, 0, 0)',
            pointBackgroundColor: 'rgb(168, 0, 0)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: 'rgb(168, 0, 0)',
            pointHoverBorderColor: 'rgba(77,83,96,1)'
        },
        { // blue blue
            backgroundColor: 'transparent',
            borderColor: 'rgb(0, 0, 255)',
            pointBackgroundColor: 'rgb(0, 0, 255)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: 'rgb(0, 0, 255)',
            pointHoverBorderColor: 'rgba(77,83,96,1)'
        },
        { // dark blue
            backgroundColor: 'transparent',
            borderColor: 'rgb(0, 0, 122)',
            pointBackgroundColor: 'rgb(0, 0, 122)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: 'rgb(0, 0, 122)',
            pointHoverBorderColor: 'rgba(77,83,96,1)'
        }
    ];
    lineChartLegend = true;
    lineChartType = 'line';

    constructor(
        private vsBloodPressureService: VsBloodPressureService,
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
        this.activatedRoute.params.subscribe((params) => {
            this.patient = params['login'];
        });
        this.hideChart = true;
    }

    loadAll() {
        if (this.patient) {
            this.vsBloodPressureService.query({
                userids: this.patient,
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            }).subscribe(
                (res: HttpResponse<VsBloodPressure[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        } else {
            this.vsBloodPressureService.query({
                userids: this.principal.getLogin(),
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            }).subscribe(
                (res: HttpResponse<VsBloodPressure[]>) => this.onSuccess(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        }

    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/vs-blood-pressure'], {
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
        this.router.navigate(['/vs-blood-pressure', {
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
        this.registerChangeInVsBloodPressures();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: VsBloodPressure) {
        return item.id;
    }

    registerChangeInVsBloodPressures() {
        this.eventSubscriber = this.eventManager.subscribe('vsBloodPressureListModification', (response) => this.loadAll());
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
        this.vsBloodPressures = data;
        for (let i = 0; i < this.vsBloodPressures.length; i++) {
            this.lineChartData[0]['data'].push(this.vsBloodPressures[i].systolic);
            this.lineChartData[1]['data'].push(this.vsBloodPressures[i].diastolic);
            this.lineChartLabels.push(this.vsBloodPressures[i].measurmentdate.toString().substr(0, 21));
        }

        const sysSum = this.lineChartData[0]['data'].reduce((a, b) => a + b, 0);
        const diasSum = this.lineChartData[1]['data'].reduce((a, b) => a + b, 0);
        let sysMedian = 0;
        let diasMedian = 0;
        const sortedSysArr = this.lineChartData[0]['data'].sort((a, b) => a - b);
        const sortedDiasArr = this.lineChartData[1]['data'].sort((a, b) => a - b);

        if (sortedSysArr.length % 2 !== 0) {
            sysMedian = sortedSysArr[(sortedSysArr.length - 1) / 2];
            diasMedian = sortedDiasArr[(sortedDiasArr.length - 1) / 2];
        } else {
            sysMedian = (sortedSysArr[sortedSysArr.length / 2] + sortedSysArr[(sortedSysArr.length / 2) - 1]) / 2;
            diasMedian = (sortedDiasArr[sortedDiasArr.length / 2] + sortedDiasArr[(sortedDiasArr.length / 2) - 1]) / 2;
        }

        for (let i = 0; i < this.vsBloodPressures.length; i++) {
            this.lineChartData[2]['data'].push(sysSum / this.lineChartData[0]['data'].length);
            this.lineChartData[3]['data'].push(diasSum / this.lineChartData[0]['data'].length);
            this.lineChartData[4]['data'].push(sysMedian);
            this.lineChartData[5]['data'].push(diasMedian);
        }

    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
