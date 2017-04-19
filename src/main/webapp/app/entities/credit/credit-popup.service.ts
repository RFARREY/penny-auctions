import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Credit } from './credit.model';
import { CreditService } from './credit.service';
@Injectable()
export class CreditPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private creditService: CreditService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.creditService.find(id).subscribe((credit) => {
                credit.timestamp = this.datePipe
                    .transform(credit.timestamp, 'yyyy-MM-ddThh:mm');
                this.creditModalRef(component, credit);
            });
        } else {
            return this.creditModalRef(component, new Credit());
        }
    }

    creditModalRef(component: Component, credit: Credit): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.credit = credit;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
