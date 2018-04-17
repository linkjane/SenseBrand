import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { MyFile } from './my-file.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<MyFile>;

@Injectable()
export class MyFileService {

    private resourceUrl =  SERVER_API_URL + 'api/my-files';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/my-files';

    constructor(private http: HttpClient) { }

    create(myFile: MyFile): Observable<EntityResponseType> {
        const copy = this.convert(myFile);
        return this.http.post<MyFile>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(myFile: MyFile): Observable<EntityResponseType> {
        const copy = this.convert(myFile);
        return this.http.put<MyFile>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<MyFile>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MyFile[]>> {
        const options = createRequestOption(req);
        return this.http.get<MyFile[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MyFile[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<MyFile[]>> {
        const options = createRequestOption(req);
        return this.http.get<MyFile[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MyFile[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MyFile = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<MyFile[]>): HttpResponse<MyFile[]> {
        const jsonResponse: MyFile[] = res.body;
        const body: MyFile[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to MyFile.
     */
    private convertItemFromServer(myFile: MyFile): MyFile {
        const copy: MyFile = Object.assign({}, myFile);
        return copy;
    }

    /**
     * Convert a MyFile to a JSON which can be sent to the server.
     */
    private convert(myFile: MyFile): MyFile {
        const copy: MyFile = Object.assign({}, myFile);
        return copy;
    }
}
