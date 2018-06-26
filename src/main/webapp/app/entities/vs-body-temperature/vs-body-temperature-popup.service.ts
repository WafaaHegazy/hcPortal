import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { VsBodyTemperature } from './vs-body-temperature.model';
import { VsBodyTemperatureService } from './vs-body-temperature.service';
import {DatePipe} from '@angular/common';

@Injectable()
export class VsBodyTemperaturePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private vsBodyTemperatureService: VsBodyTemperatureService

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
                this.vsBodyTemperatureService.find(id)
                    .subscribe((vsBodyTemperatureResponse: HttpResponse<VsBodyTemperature>) => {
                        const vsBodyTemperature: VsBodyTemperature = vsBodyTemperatureResponse.body;
                        vsBodyTemperature.measurmentdate = this.datePipe
                            .transform(vsBodyTemperature.measurmentdate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.vsBodyTemperatureModalRef(component, vsBodyTemperature);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.vsBodyTemperatureModalRef(component, new VsBodyTemperature());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    vsBodyTemperatureModalRef(component: Component, vsBodyTemperature: VsBodyTemperature): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.vsBodyTemperature = vsBodyTemperature;
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
