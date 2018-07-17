import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {VsRespiratoryRatePopupService} from './vs-respiratory-rate-popup.service';

@Component({
    selector: 'jhi-vs-respiratory-rate-info',
    templateUrl: './vs-respiratory-rate-info.component.html'
})
export class VsRespiratoryRateInfoComponent implements OnInit {

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
    selector: 'jhi-vs-respiratory-rate-info-popup',
    template: ''
})
export class VsRespiratoryRateInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vsRespiratoryRatePopupService: VsRespiratoryRatePopupService
    ) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(() => {
            this.vsRespiratoryRatePopupService
                .open(VsRespiratoryRateInfoComponent as Component);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
