import { Component } from '@angular/core';
import { JhiLanguageService } from 'ng-jhipster';

@Component({
    selector: 'jhi-faq',
    templateUrl: './faq.component.html'
})
export class FaqComponent {
    constructor(
        private jhiLanguageService: JhiLanguageService
    ) {
        this.jhiLanguageService.setLocations(['pages']);
    }
}
