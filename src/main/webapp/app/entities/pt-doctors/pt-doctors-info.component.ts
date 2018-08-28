import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {PtDoctorsPopupService} from './pt-doctors-popup.service';

@Component({
    selector: 'jhi-pt-doctors-info',
    templateUrl: './pt-doctors-info.component.html'
})
export class PtDoctorsInfoComponent implements OnInit {

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
    selector: 'jhi-pt-doctors-info-popup',
    template: ''
})
export class PtDoctorsInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ptDoctorsPopupService: PtDoctorsPopupService
    ) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(() => {
            this.ptDoctorsPopupService.open(PtDoctorsInfoComponent as Component);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
