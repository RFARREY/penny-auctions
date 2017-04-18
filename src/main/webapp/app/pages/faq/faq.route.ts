import { Route } from '@angular/router';

import { FaqComponent } from './faq.component';

export const faqRoute: Route = {
    path: 'faq',
    component: FaqComponent,
    data: {
        pageTitle: 'pages.faq.title'
    }
};
