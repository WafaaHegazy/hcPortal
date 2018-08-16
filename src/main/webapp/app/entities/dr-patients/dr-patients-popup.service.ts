import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DrPatients } from './dr-patients.model';
import { DrPatientsService } from './dr-patients.service';
import {DatePipe} from '@angular/common';

@Injectable()
export class DrPatientsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private drPatientsService: DrPatientsService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.drPatientsService.find(id)
                    .subscribe((DrPatientsResponse: HttpResponse<DrPatients>) => {
                        const drPatients: DrPatients = DrPatientsResponse.body;
                        this.ngbModalRef = this.DrPatientsModalRef(component, drPatients);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.DrPatientsModalRef(component, new DrPatients());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    DrPatientsModalRef(component: Component, drPatients: DrPatients): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.DrPatients = drPatients;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
