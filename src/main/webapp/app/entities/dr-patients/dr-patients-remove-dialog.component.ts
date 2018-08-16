import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DrPatients } from './dr-patients.model';
import { DrPatientsPopupService } from './dr-patients-popup.service';
import { DrPatientsService } from './dr-patients.service';

@Component({
    selector: 'jhi-dr-patients-remove-dialog',
    templateUrl: './dr-patients-remove-dialog.component.html'
})
export class DrPatientsRemoveDialogComponent {

    DrPatients: DrPatients;

    constructor(
        private drPatientsService: DrPatientsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.drPatientsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'DrPatientsListModification',
                content: 'Deleted an DrPatients'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dr-patients-delete-popup',
    template: ''
})
export class DrPatientsRemovePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private drPatientsPopupService: DrPatientsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.drPatientsPopupService
                .open(DrPatientsRemoveDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
