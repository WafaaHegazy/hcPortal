import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DiabetesSugarTest } from './diabetes-sugar-test.model';
import { DiabetesSugarTestService } from './diabetes-sugar-test.service';
import {DatePipe} from '@angular/common';

@Injectable()
export class DiabetesSugarTestPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private diabetesSugarTestService: DiabetesSugarTestService

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
                this.diabetesSugarTestService.find(id)
                    .subscribe((diabetesSugarTestResponse: HttpResponse<DiabetesSugarTest>) => {
                        const diabetesSugarTest: DiabetesSugarTest = diabetesSugarTestResponse.body;
                        diabetesSugarTest.measurmentdate = this.datePipe
                            .transform(diabetesSugarTest.measurmentdate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.diabetesSugarTestModalRef(component, diabetesSugarTest);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.diabetesSugarTestModalRef(component, new DiabetesSugarTest());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    diabetesSugarTestModalRef(component: Component, diabetesSugarTest: DiabetesSugarTest): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.diabetesSugarTest = diabetesSugarTest;
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
