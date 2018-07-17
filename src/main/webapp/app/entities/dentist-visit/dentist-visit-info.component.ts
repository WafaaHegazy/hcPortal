import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {DentistVisitPopupService} from './dentist-visit-popup.service';

@Component({
    selector: 'jhi-dentist-visit-info',
    templateUrl: './dentist-visit-info.component.html'
})
export class DentistVisitInfoComponent implements OnInit {

    constructor(
        public activeModal: NgbActiveModal,
    ) {
    }

    ngOnInit() {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }
}

@Component({
    selector: 'jhi-dentist-visit-info-popup',
    template: ''
})
export class DentistVisitInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dentistVisitPopupService: DentistVisitPopupService
    ) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(() => {
            this.dentistVisitPopupService.open(DentistVisitInfoComponent as Component);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
