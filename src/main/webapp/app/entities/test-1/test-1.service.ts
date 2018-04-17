import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { forkJoin } from 'rxjs/observable/forkJoin';
import { SERVER_API_URL, FILE_UPLOAD_URL, STATIC_SERVER_URL } from '../../app.constants';

import { Test1 } from './test-1.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Test1>;

class FileCallbackModel {
    constructor(
        public fileUrl: string,
        public fileName?: string,
        public extensionName?: string,
        public key?: string
    ){ }
}

@Injectable()
export class Test1Service {

    private fileUploadUrl = FILE_UPLOAD_URL;
    private resourceUrl =  SERVER_API_URL + 'api/test-1-s';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/test-1-s';
    constructor(private http: HttpClient) { }

    create(test1: Test1): Observable<EntityResponseType> {
        const copy = this.convert(test1);
        const filePostArr = [];
        let idx = 0;
        for (const key in copy) {
            const entityFileName = key.substring(0, key.indexOf('FileSource'));
            if (key.includes('FileSource') && copy[key]) {
                const formData = new FormData();
                if (copy[key] && copy[key].files) {
                    formData.append('file', copy[key].files[0]);
                    formData.append('key', entityFileName);
                    filePostArr[idx++] = this.http.post(this.fileUploadUrl, formData);
                }
            }
            if (key.includes('Base64Data')) {
                copy[key] = '';
            }

        }
        if (filePostArr.length <= 0) {
            return this.http.post<Test1>(this.resourceUrl, copy, { observe: 'response' })
                .map((res: EntityResponseType) => this.convertResponse(res));
        }
        return forkJoin(...filePostArr).concatMap((results: FileCallbackModel[]) => {
            results.forEach((element: FileCallbackModel) => {
                copy[element.key] = element.fileName;
                copy[element.key + 'Url'] = element.fileUrl;
            });
            return this.http.post<Test1>(this.resourceUrl, copy, { observe: 'response' });
        }).map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(test1: Test1): Observable<EntityResponseType> {
        const copy = this.convert(test1);

        const filePostArr = [];
        let idx = 0;
        const reg = /^(?:([A-Za-z]+):)?(\/{0,3})([0-9.\-A-Za-z]+)(?::(\d+))?(?:\/([^?#]*))?(?:\?([^#]*))?(?:#(.*))?$/;
        for (const key in copy) {
            const entityFileName = key.substring(0, key.indexOf('FileSource'));
            if (key.includes('FileSource') && copy[key]) {
                const formData = new FormData();
                if (copy[key] && copy[key].files) {
                    formData.append('file', copy[key].files[0]);
                    formData.append('key', entityFileName);
                    filePostArr[idx++] = this.http.post(this.fileUploadUrl, formData);
                }
            }
            if (key.includes('Base64Data')) {
                copy[key] = '';
            }
            if (key.includes('Url') && copy[key]) {
                const exec = reg.exec(copy[key]);
                copy[key] = exec[5];
            }
        }
        if (filePostArr.length <= 0) {
            return this.http.put<Test1>(this.resourceUrl, copy, { observe: 'response' })
                .map((res: EntityResponseType) => this.convertResponse(res));
        }
        return forkJoin(...filePostArr).concatMap((results: FileCallbackModel[]) => {
            results.forEach((element: FileCallbackModel) => {
                copy[element.key] = element.fileName;
                copy[element.key + 'Url'] = element.fileUrl;
            });
            return this.http.put<Test1>(this.resourceUrl, copy, { observe: 'response' });
        }).map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Test1>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Test1[]>> {
        const options = createRequestOption(req);
        return this.http.get<Test1[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Test1[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Test1[]>> {
        const options = createRequestOption(req);
        return this.http.get<Test1[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Test1[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Test1 = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Test1[]>): HttpResponse<Test1[]> {
        const jsonResponse: Test1[] = res.body;
        const body: Test1[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Test1.
     */
    private convertItemFromServer(test1: Test1): Test1 {
        const copy: Test1 = Object.assign({}, test1);
        for (const key in copy) {
            if (key.includes('Url') && copy[key]) {
               copy[key] = STATIC_SERVER_URL + '/' + copy[key];
            }
        }
        return copy;
    }

    /**
     * Convert a Test1 to a JSON which can be sent to the server.
     */
    private convert(test1: Test1): Test1 {
        const copy: Test1 = Object.assign({}, test1);
        return copy;
    }
}
