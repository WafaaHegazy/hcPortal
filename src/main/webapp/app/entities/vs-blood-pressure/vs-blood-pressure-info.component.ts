import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {VsBloodPressurePopupService} from './vs-blood-pressure-popup.service';

@Component({
    selector: 'jhi-vs-blood-pressure-info',
    templateUrl: './vs-blood-pressure-info.component.html'
})
export class VsBloodPressureInfoComponent implements OnInit {

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
    selector: 'jhi-vs-blood-pressure-info-popup',
    template: ''
})
export class VsBloodPressureInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vsBloodPressurePopupService: VsBloodPressurePopupService
    ) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(() => {
            this.vsBloodPressurePopupService.open(VsBloodPressureInfoComponent as Component);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
