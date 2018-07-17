import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {VsHeartRatePopupService} from './vs-heart-rate-popup.service';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'jhi-vs-heart-rate-info',
    templateUrl: './vs-heart-rate-info.component.html'
})
export class VsHeartRateInfoComponent implements OnInit {

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
    selector: 'jhi-vs-heart-rate-info-popup',
    template: ''
})
export class VsHeartRateInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vsHeartRatePopupService: VsHeartRatePopupService
    ) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(() => {
            this.vsHeartRatePopupService
                .open(VsHeartRateInfoComponent as Component);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
