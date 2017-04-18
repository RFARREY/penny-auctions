import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { NinjabidSharedModule, UserRouteAccessService } from './shared';
import { NinjabidHomeModule } from './home/home.module';
import { NinjabidAdminModule } from './admin/admin.module';
import { NinjabidAccountModule } from './account/account.module';
import { NinjabidEntityModule } from './entities/entity.module';
import { NinjabidPageModule } from './pages/page.module';

import { LayoutRoutingModule } from './layouts';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        NinjabidSharedModule,
        NinjabidHomeModule,
        NinjabidAdminModule,
        NinjabidAccountModule,
        NinjabidEntityModule,
        NinjabidPageModule
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class NinjabidAppModule {}
