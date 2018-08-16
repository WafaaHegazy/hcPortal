import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {DrPatientsPopupService} from './dr-patients-popup.service';

@Component({
    selector: 'jhi-dr-patients-detail',
    templateUrl: './dr-patients-detail.component.html'
})
export class DrPatientsDetailComponent implements OnInit {

    detailItems = [];
    login: string;
    name: string;

    constructor(
        public activeModal: NgbActiveModal,
        private router: Router
    ) {
        this.detailItems.push(
            {
                name: 'SpO2',
                link: '../vs-spo-2/patient/'
            },
            {
                name: 'Respiratory Rate',
                link: '../vs-respiratory-rate/patient/'
            },
            {
                name: 'Heart Rate',
                link: '../vs-heart-rate/patient/'
            },
            {
                name: 'Body Temperature',
                link: '../vs-body-temperature/patient/'
            }, {
                name: 'Blood Pressure',
                link: '../vs-blood-pressure/patient/'
            },
            {
                name: 'Menstrual Cycle',
                link: '../menstrual-cycle/patient/'
            },
            {
                name: 'Blood Sugar Tests',
                link: '../diabetes-sugar-test/patient/'
            },
            {
                name: 'Blood Tests',
                link: '../blood-test/patient/'
            }
        );
    }

    ngOnInit() {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    navigate(url) {
        this.router.navigate([url, this.login]);
    }
}

@Component({
    selector: 'jhi-dr-patients-detail-popup',
    template: ''
})
export class DrPatientsDetailPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private drPatientsPopupService: DrPatientsPopupService
    ) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.drPatientsPopupService.open(DrPatientsDetailComponent as Component);
            DrPatientsDetailComponent.prototype.login = params['id'];
            DrPatientsDetailComponent.prototype.name = params['name'];
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
