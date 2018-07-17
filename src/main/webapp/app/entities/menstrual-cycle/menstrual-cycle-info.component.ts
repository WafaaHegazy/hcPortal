import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {MenstrualCyclePopupService} from './menstrual-cycle-popup.service';

@Component({
    selector: 'jhi-menstrual-cycle-info',
    templateUrl: './menstrual-cycle-info.component.html'
})
export class MenstrualCycleInfoComponent implements OnInit {

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
    selector: 'jhi-menstrual-cycle-info-popup',
    template: ''
})
export class MenstrualCycleInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private menstrualCyclePopupService: MenstrualCyclePopupService
    ) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(() => {
            this.menstrualCyclePopupService.open(MenstrualCycleInfoComponent as Component);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
