import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { VsBloodPressure } from './vs-blood-pressure.model';
import { VsBloodPressureService } from './vs-blood-pressure.service';
import {DatePipe} from "@angular/common";

@Injectable()
export class VsBloodPressurePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private vsBloodPressureService: VsBloodPressureService

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
                this.vsBloodPressureService.find(id)
                    .subscribe((vsBloodPressureResponse: HttpResponse<VsBloodPressure>) => {
                        const vsBloodPressure: VsBloodPressure = vsBloodPressureResponse.body;
                        vsBloodPressure.measurmentdate = this.datePipe
                            .transform(vsBloodPressure.measurmentdate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.vsBloodPressureModalRef(component, vsBloodPressure);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.vsBloodPressureModalRef(component, new VsBloodPressure());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    vsBloodPressureModalRef(component: Component, vsBloodPressure: VsBloodPressure): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.vsBloodPressure = vsBloodPressure;
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
