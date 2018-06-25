import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DentistVisit } from './dentist-visit.model';
import { DentistVisitService } from './dentist-visit.service';
import {DatePipe} from "@angular/common";

@Injectable()
export class DentistVisitPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private dentistVisitService: DentistVisitService

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
                this.dentistVisitService.find(id)
                    .subscribe((dentistVisitResponse: HttpResponse<DentistVisit>) => {
                        const dentistVisit: DentistVisit = dentistVisitResponse.body;
                        dentistVisit.measurmentdate = this.datePipe
                            .transform(dentistVisit.measurmentdate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.dentistVisitModalRef(component, dentistVisit);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.dentistVisitModalRef(component, new DentistVisit());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    dentistVisitModalRef(component: Component, dentistVisit: DentistVisit): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.dentistVisit = dentistVisit;
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
