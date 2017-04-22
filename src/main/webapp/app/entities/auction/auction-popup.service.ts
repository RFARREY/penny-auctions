import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Auction } from './auction.model';
import { AuctionService } from './auction.service';
@Injectable()
export class AuctionPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private auctionService: AuctionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.auctionService.find(id).subscribe((auction) => {
                auction.starting_at = this.datePipe
                    .transform(auction.starting_at, 'yyyy-MM-ddThh:mm');
                this.auctionModalRef(component, auction);
            });
        } else {
            return this.auctionModalRef(component, new Auction());
        }
    }

    auctionModalRef(component: Component, auction: Auction): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.auction = auction;
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
