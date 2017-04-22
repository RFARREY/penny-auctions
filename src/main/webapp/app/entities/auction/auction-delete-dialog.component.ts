import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Auction } from './auction.model';
import { AuctionPopupService } from './auction-popup.service';
import { AuctionService } from './auction.service';

@Component({
    selector: 'jhi-auction-delete-dialog',
    templateUrl: './auction-delete-dialog.component.html'
})
export class AuctionDeleteDialogComponent {

    auction: Auction;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private auctionService: AuctionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['auction', 'auctionStatus']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.auctionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'auctionListModification',
                content: 'Deleted an auction'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-auction-delete-popup',
    template: ''
})
export class AuctionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private auctionPopupService: AuctionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.auctionPopupService
                .open(AuctionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
