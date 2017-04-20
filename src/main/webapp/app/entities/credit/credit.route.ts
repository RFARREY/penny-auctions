import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CreditComponent } from './credit.component';
import { CreditDetailComponent } from './credit-detail.component';
import { CreditPopupComponent } from './credit-dialog.component';

import { Principal } from '../../shared';

export const creditRoute: Routes = [
  {
    path: 'credit',
    component: CreditComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ninjabidApp.credit.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'credit/:id',
    component: CreditDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ninjabidApp.credit.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const creditPopupRoute: Routes = [
  {
    path: 'credit-new',
    component: CreditPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'ninjabidApp.credit.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
