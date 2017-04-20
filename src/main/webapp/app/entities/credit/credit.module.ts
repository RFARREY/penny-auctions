import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { NinjabidSharedModule } from '../../shared';
import { NinjabidAdminModule } from '../../admin/admin.module';
import {
    CreditService,
    CreditPopupService,
    CreditComponent,
    CreditDetailComponent,
    CreditDialogComponent,
    CreditPopupComponent,
    creditRoute,
    creditPopupRoute,
} from './';

const ENTITY_STATES = [
    ...creditRoute,
    ...creditPopupRoute,
];

@NgModule({
    imports: [
        NinjabidSharedModule,
        NinjabidAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CreditComponent,
        CreditDetailComponent,
        CreditDialogComponent,
        CreditPopupComponent,
    ],
    entryComponents: [
        CreditComponent,
        CreditDialogComponent,
        CreditPopupComponent,
    ],
    providers: [
        CreditService,
        CreditPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NinjabidCreditModule {}
