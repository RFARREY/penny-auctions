import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Auction, AuctionStatus } from './auction.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class AuctionService {

    private resourceUrl = 'api/auctions';
    private resourceSearchUrl = 'api/_search/auctions';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(auction: Auction): Observable<Auction> {
        const copy: Auction = Object.assign({}, auction);
        copy.starting_at = this.dateUtils.toDate(auction.starting_at);
        copy.status = AuctionStatus.upcoming;
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(auction: Auction): Observable<Auction> {
        const copy: Auction = Object.assign({}, auction);

        copy.starting_at = this.dateUtils.toDate(auction.starting_at);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Auction> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.starting_at = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.starting_at);
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
            jsonResponse[i].starting_at = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].starting_at);
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
