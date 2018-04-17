import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ReaderOld } from './reader-old.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ReaderOld>;

@Injectable()
export class ReaderOldService {

    private resourceUrl =  SERVER_API_URL + 'api/reader-olds';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/reader-olds';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(readerOld: ReaderOld): Observable<EntityResponseType> {
        const copy = this.convert(readerOld);
        return this.http.post<ReaderOld>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(readerOld: ReaderOld): Observable<EntityResponseType> {
        const copy = this.convert(readerOld);
        return this.http.put<ReaderOld>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ReaderOld>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ReaderOld[]>> {
        const options = createRequestOption(req);
        return this.http.get<ReaderOld[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ReaderOld[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<ReaderOld[]>> {
        const options = createRequestOption(req);
        return this.http.get<ReaderOld[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ReaderOld[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ReaderOld = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ReaderOld[]>): HttpResponse<ReaderOld[]> {
        const jsonResponse: ReaderOld[] = res.body;
        const body: ReaderOld[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ReaderOld.
     */
    private convertItemFromServer(readerOld: ReaderOld): ReaderOld {
        const copy: ReaderOld = Object.assign({}, readerOld);
        copy.localTime = this.dateUtils
            .convertLocalDateFromServer(readerOld.localTime);
        return copy;
    }

    /**
     * Convert a ReaderOld to a JSON which can be sent to the server.
     */
    private convert(readerOld: ReaderOld): ReaderOld {
        const copy: ReaderOld = Object.assign({}, readerOld);
        copy.localTime = this.dateUtils
            .convertLocalDateToServer(readerOld.localTime);
        return copy;
    }
}
