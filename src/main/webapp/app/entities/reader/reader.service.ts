import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Reader } from './reader.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Reader>;

@Injectable()
export class ReaderService {

    private resourceUrl =  SERVER_API_URL + 'api/readers';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/readers';

    constructor(private http: HttpClient) { }

    create(reader: Reader): Observable<EntityResponseType> {
        const copy = this.convert(reader);
        return this.http.post<Reader>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(reader: Reader): Observable<EntityResponseType> {
        const copy = this.convert(reader);
        return this.http.put<Reader>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Reader>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Reader[]>> {
        const options = createRequestOption(req);
        return this.http.get<Reader[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Reader[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Reader[]>> {
        const options = createRequestOption(req);
        return this.http.get<Reader[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Reader[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Reader = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Reader[]>): HttpResponse<Reader[]> {
        const jsonResponse: Reader[] = res.body;
        const body: Reader[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Reader.
     */
    private convertItemFromServer(reader: Reader): Reader {
        const copy: Reader = Object.assign({}, reader);
        return copy;
    }

    /**
     * Convert a Reader to a JSON which can be sent to the server.
     */
    private convert(reader: Reader): Reader {
        const copy: Reader = Object.assign({}, reader);
        return copy;
    }
}
