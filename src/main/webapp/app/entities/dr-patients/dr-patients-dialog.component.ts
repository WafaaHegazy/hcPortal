import { Component, OnInit, OnDestroy, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DrPatients } from './dr-patients.model';
import { DrPatientsPopupService } from './dr-patients-popup.service';
import { DrPatientsService } from './dr-patients.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-dr-patients-dialog',
    templateUrl: './dr-patients-dialog.component.html'
})
export class DrPatientsDialogComponent implements OnInit {

    drPatients: DrPatients;
    isSaving: boolean;
    @Input() email: string;

    constructor(
        public activeModal: NgbActiveModal,
        private drPatientsService: DrPatientsService,
        private principal: Principal,
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
        this.subscribeToSaveResponse(this.drPatientsService.add(this.principal.getId(), this.email));
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DrPatients>>) {
        result.subscribe((res: HttpResponse<DrPatients>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DrPatients) {
        this.eventManager.broadcast({ name: 'DrPatientsListModification', content: 'OK' });
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-dr-patients-popup',
    template: ''
})
export class DrPatientsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private drPatientsPopupService: DrPatientsPopupService
    ) { }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.drPatientsPopupService
                    .open(DrPatientsDialogComponent as Component, params['id']);
            } else {
                this.drPatientsPopupService
                    .open(DrPatientsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
