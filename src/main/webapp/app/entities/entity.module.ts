import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { NinjabidCreditModule } from './credit/credit.module';
import { NinjabidAuctionModule } from './auction/auction.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        NinjabidCreditModule,
        NinjabidAuctionModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NinjabidEntityModule {}
