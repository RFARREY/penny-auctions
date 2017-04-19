import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Credit } from './credit.model';
import { CreditService } from './credit.service';

@Component({
    selector: 'jhi-credit-detail',
    templateUrl: './credit-detail.component.html'
})
export class CreditDetailComponent implements OnInit, OnDestroy {

    credit: Credit;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private creditService: CreditService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['credit', 'status']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCredits();
    }

    load(id) {
        this.creditService.find(id).subscribe((credit) => {
            this.credit = credit;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCredits() {
        this.eventSubscriber = this.eventManager.subscribe('creditListModification', (response) => this.load(this.credit.id));
    }
}
