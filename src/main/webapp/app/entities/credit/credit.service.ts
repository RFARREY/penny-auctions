import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Credit } from './credit.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()

export class CreditService {

    private resourceUrl = 'api/credits';
    private resourceSearchUrl = 'api/_search/credits';
    private resourceBalanceUrl = 'api/account/balance';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    // todo remove, this should come from payments gateway, for now we mock it from front-end.
    create(credit: Credit): Observable<Credit> {
        const copy: Credit = Object.assign({}, credit);
        credit.timestamp = "2017-01-01T01:00";
        copy.timestamp = this.dateUtils.toDate(credit.timestamp);
        copy.price = 0.1;
        copy.userId = 4;
        copy.status = "paid";
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Credit> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.timestamp = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.timestamp);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    search(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    // todo implement some angular caching solution, or just a service to hold the value
    // todo update cache value when a bid is made or a credits purchase
    balance(): Observable<Response> {
        return this.http.get(this.resourceBalanceUrl)
            .map((res: any) => res)
    }

    private convertResponse(res: any): any {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].timestamp = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].timestamp);
        }
        res._body = jsonResponse;
        return res;
    }

    private createRequestOption(req?: any): BaseRequestOptions {
        const options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            const params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
