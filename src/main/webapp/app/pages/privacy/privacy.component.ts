import { Component } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';

@Component({
    selector: 'jhi-privacy',
    templateUrl: './privacy.component.html'
})
export class PrivacyComponent {
    constructor(
        private jhiLanguageService: JhiLanguageService
    ) {
        this.jhiLanguageService.setLocations(['pages']);
    }
}
