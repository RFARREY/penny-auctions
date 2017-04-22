import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NinjabidSharedModule } from '../../shared';
import {
    AuctionService,
    AuctionPopupService,
    AuctionComponent,
    AuctionDetailComponent,
    AuctionDialogComponent,
    AuctionPopupComponent,
    AuctionDeletePopupComponent,
    AuctionDeleteDialogComponent,
    auctionRoute,
    auctionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...auctionRoute,
    ...auctionPopupRoute,
];

@NgModule({
    imports: [
        NinjabidSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AuctionComponent,
        AuctionDetailComponent,
        AuctionDialogComponent,
        AuctionDeleteDialogComponent,
        AuctionPopupComponent,
        AuctionDeletePopupComponent,
    ],
    entryComponents: [
        AuctionComponent,
        AuctionDialogComponent,
        AuctionPopupComponent,
        AuctionDeleteDialogComponent,
        AuctionDeletePopupComponent,
    ],
    providers: [
        AuctionService,
        AuctionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NinjabidAuctionModule {}
