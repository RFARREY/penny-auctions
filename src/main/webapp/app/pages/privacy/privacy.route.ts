import { Route } from '@angular/router';

import { PrivacyComponent } from './privacy.component';

export const privacyRoute: Route = {
    path: 'privacy',
    component: PrivacyComponent,
    data: {
        pageTitle: 'pages.privacy.title'
    }
};
