import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MadderbidSharedModule } from '../shared';

import {
    pageState,
    PrivacyComponent,
    TermsComponent,
    FaqComponent,
} from './';

@NgModule({
    imports: [
        MadderbidSharedModule,
        RouterModule.forRoot(pageState, { useHash: true })
    ],
    declarations: [
        PrivacyComponent,
        TermsComponent,
        FaqComponent
    ],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MadderbidPageModule {}
