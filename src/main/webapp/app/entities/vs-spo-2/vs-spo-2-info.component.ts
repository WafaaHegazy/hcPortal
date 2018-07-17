import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {VsSpo2PopupService} from './vs-spo-2-popup.service';

@Component({
    selector: 'jhi-vs-spo-2-info',
    templateUrl: './vs-spo-2-info.component.html'
})
export class VsSpo2InfoComponent implements OnInit {

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
    selector: 'jhi-vs-spo-2-info-popup',
    template: ''
})
export class VsSpo2InfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vsSpo2PopupService: VsSpo2PopupService
    ) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(() => {
            this.vsSpo2PopupService
                .open(VsSpo2InfoComponent as Component);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
