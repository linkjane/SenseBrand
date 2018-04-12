import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Owner } from './owner.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Owner>;

@Injectable()
export class OwnerService {

    private resourceUrl =  SERVER_API_URL + 'api/owners';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/owners';

    constructor(private http: HttpClient) { }

    create(owner: Owner): Observable<EntityResponseType> {
        const copy = this.convert(owner);
        return this.http.post<Owner>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(owner: Owner): Observable<EntityResponseType> {
        const copy = this.convert(owner);
        return this.http.put<Owner>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Owner>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Owner[]>> {
        const options = createRequestOption(req);
        return this.http.get<Owner[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Owner[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Owner[]>> {
        const options = createRequestOption(req);
        return this.http.get<Owner[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Owner[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Owner = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Owner[]>): HttpResponse<Owner[]> {
        const jsonResponse: Owner[] = res.body;
        const body: Owner[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Owner.
     */
    private convertItemFromServer(owner: Owner): Owner {
        const copy: Owner = Object.assign({}, owner);
        return copy;
    }

    /**
     * Convert a Owner to a JSON which can be sent to the server.
     */
    private convert(owner: Owner): Owner {
        const copy: Owner = Object.assign({}, owner);
        return copy;
    }
}
