
import { Routes } from '@angular/router';

import {
    privacyRoute,
    termsRoute,
    faqRoute
} from './';

const PAGE_ROUTES = [
    privacyRoute,
    termsRoute,
    faqRoute
];

export const pageState: Routes = [{
    path: '',
    children: PAGE_ROUTES
}];
