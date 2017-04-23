import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AuctionComponent } from './auction.component';
import { AuctionDetailComponent } from './auction-detail.component';
import { AuctionPopupComponent } from './auction-dialog.component';
import { AuctionDeletePopupComponent } from './auction-delete-dialog.component';

import { Principal } from '../../shared';

export const auctionRoute: Routes = [
  {
    path: 'auction',
    component: AuctionComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ninjabidApp.auction.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'auction/:id',
    component: AuctionDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ninjabidApp.auction.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const auctionPopupRoute: Routes = [
  {
    path: 'auction-new',
    component: AuctionPopupComponent,
    data: {
        authorities: ['ROLE_ADMIN'],
        pageTitle: 'ninjabidApp.auction.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'auction/:id/edit',
    component: AuctionPopupComponent,
    data: {
        authorities: ['ROLE_ADMIN'],
        pageTitle: 'ninjabidApp.auction.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'auction/:id/delete',
    component: AuctionDeletePopupComponent,
    data: {
        authorities: ['ROLE_ADMIN'],
        pageTitle: 'ninjabidApp.auction.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
