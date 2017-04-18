import { Route } from '@angular/router';

import { TermsComponent } from './terms.component';

export const termsRoute: Route = {
    path: 'terms',
    component: TermsComponent,
    data: {
        pageTitle: 'pages.terms.title'
    }
};
