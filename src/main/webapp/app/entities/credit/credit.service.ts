import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Credit } from './credit.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()

export class CreditService {

    private resourceUrl = 'api/credits';
    private resourceSearchUrl = 'api/_search/credits';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    // todo credits remove this
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

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
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
