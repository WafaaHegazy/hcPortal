import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {DiabetesSugarTestPopupService} from './diabetes-sugar-test-popup.service';

@Component({
    selector: 'jhi-diabetes-sugar-test-info',
    templateUrl: './diabetes-sugar-test-info.component.html'
})
export class DiabetesSugarTestInfoComponent implements OnInit {

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
    selector: 'jhi-diabetes-sugar-test-info-popup',
    template: ''
})
export class DiabetesSugarTestInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private diabetesSugarTestPopupService: DiabetesSugarTestPopupService
    ) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(() => {
            this.diabetesSugarTestPopupService.open(DiabetesSugarTestInfoComponent as Component);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
