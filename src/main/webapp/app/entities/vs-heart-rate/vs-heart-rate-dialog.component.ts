import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VsHeartRate } from './vs-heart-rate.model';
import { VsHeartRatePopupService } from './vs-heart-rate-popup.service';
import { VsHeartRateService } from './vs-heart-rate.service';

@Component({
    selector: 'jhi-vs-heart-rate-dialog',
    templateUrl: './vs-heart-rate-dialog.component.html'
})
export class VsHeartRateDialogComponent implements OnInit {

    vsHeartRate: VsHeartRate;
    isSaving: boolean;
    measurmentdateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private vsHeartRateService: VsHeartRateService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.vsHeartRate.id !== undefined) {

            this.subscribeToSaveResponse(
                this.vsHeartRateService.update(this.vsHeartRate));
        } else {
            this.subscribeToSaveResponse(
                this.vsHeartRateService.create(this.vsHeartRate));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<VsHeartRate>>) {
        result.subscribe((res: HttpResponse<VsHeartRate>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: VsHeartRate) {
        this.eventManager.broadcast({ name: 'vsHeartRateListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-vs-heart-rate-popup',
    template: ''
})
export class VsHeartRatePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private vsHeartRatePopupService: VsHeartRatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.vsHeartRatePopupService
                    .open(VsHeartRateDialogComponent as Component, params['id']);
            } else {
                this.vsHeartRatePopupService
                    .open(VsHeartRateDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
