import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { VsSpo2 } from './vs-spo-2.model';
import { VsSpo2Service } from './vs-spo-2.service';
import {DatePipe} from "@angular/common";

@Injectable()
export class VsSpo2PopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private vsSpo2Service: VsSpo2Service

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
                this.vsSpo2Service.find(id)
                    .subscribe((vsSpo2Response: HttpResponse<VsSpo2>) => {
                        const vsSpo2: VsSpo2 = vsSpo2Response.body;
                        vsSpo2.measurmentdate = this.datePipe
                            .transform(vsSpo2.measurmentdate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.vsSpo2ModalRef(component, vsSpo2);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.vsSpo2ModalRef(component, new VsSpo2());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    vsSpo2ModalRef(component: Component, vsSpo2: VsSpo2): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.vsSpo2 = vsSpo2;
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
