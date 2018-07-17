import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {VsBodyTemperaturePopupService} from './vs-body-temperature-popup.service';

@Component({
    selector: 'jhi-vs-body-temperature-info',
    templateUrl: './vs-body-temperature-info.component.html'
})
export class VsBodyTemperatureInfoComponent implements OnInit {

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
    selector: 'jhi-vs-body-temperature-info-popup',
    template: ''
})
export class VsBodyTemperatureInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vsBodyTemperaturePopupService: VsBodyTemperaturePopupService
    ) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(() => {
            this.vsBodyTemperaturePopupService.open(VsBodyTemperatureInfoComponent as Component);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
