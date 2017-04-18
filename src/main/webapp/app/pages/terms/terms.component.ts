import { Component } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';

@Component({
    selector: 'jhi-terms',
    templateUrl: './terms.component.html'
})
export class TermsComponent {
    constructor(
        private jhiLanguageService: JhiLanguageService
    ) {
        this.jhiLanguageService.setLocations(['pages']);
    }
}
