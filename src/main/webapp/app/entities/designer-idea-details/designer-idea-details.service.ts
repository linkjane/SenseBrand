import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { forkJoin } from 'rxjs/observable/forkJoin';
import { SERVER_API_URL, FILE_UPLOAD_URL, STATIC_SERVER_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { DesignerIdeaDetails } from './designer-idea-details.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DesignerIdeaDetails>;

class FileCallbackModel {
    constructor(
        public fileUrl: string,
        public fileName?: string,
        public extensionName?: string,
        public key?: string
    ){ }
}

@Injectable()
export class DesignerIdeaDetailsService {

    private fileUploadUrl = FILE_UPLOAD_URL;
    private resourceUrl =  SERVER_API_URL + 'api/designer-idea-details';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/designer-idea-details';
    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(designerIdeaDetails: DesignerIdeaDetails): Observable<EntityResponseType> {
        const copy = this.convert(designerIdeaDetails);
        const filePostArr = [];
        let idx = 0;
        for (const key in copy) {
            const entityFileName = key.substring(0, key.indexOf('FileSource'));
            if (key.endsWith('FileSource') && copy[key]) {
                const formData = new FormData();
                if (copy[key] && copy[key].files) {
                    formData.append('file', copy[key].files[0]);
                    formData.append('key', entityFileName);
                    filePostArr[idx++] = this.http.post(this.fileUploadUrl, formData);
                }
            }
            if (key.endsWith('Base64Data')) {
                copy[key] = '';
            }

        }
        if (filePostArr.length <= 0) {
            return this.http.post<DesignerIdeaDetails>(this.resourceUrl, copy, { observe: 'response' })
                .map((res: EntityResponseType) => this.convertResponse(res));
        }
        return forkJoin(...filePostArr).concatMap((results: FileCallbackModel[]) => {
            results.forEach((element: FileCallbackModel) => {
                copy[element.key] = element.fileName;
                copy[element.key + 'Url'] = element.fileUrl;
            });
            return this.http.post<DesignerIdeaDetails>(this.resourceUrl, copy, { observe: 'response' });
        }).map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(designerIdeaDetails: DesignerIdeaDetails): Observable<EntityResponseType> {
        const copy = this.convert(designerIdeaDetails);

        const filePostArr = [];
        let idx = 0;
        const reg = /^(?:([A-Za-z]+):)?(\/{0,3})([0-9.\-A-Za-z]+)(?::(\d+))?(?:\/([^?#]*))?(?:\?([^#]*))?(?:#(.*))?$/;
        for (const key in copy) {
            const entityFileName = key.substring(0, key.indexOf('FileSource'));
            if (key.endsWith('FileSource') && copy[key]) {
                const formData = new FormData();
                if (copy[key] && copy[key].files) {
                    formData.append('file', copy[key].files[0]);
                    formData.append('key', entityFileName);
                    filePostArr[idx++] = this.http.post(this.fileUploadUrl, formData);
                }
            }
            if (key.endsWith('Base64Data')) {
                copy[key] = '';
            }
            if (key.endsWith('Url') && copy[key]) {
                const exec = reg.exec(copy[key]);
                copy[key] = exec[5];
            }
        }
        if (filePostArr.length <= 0) {
            return this.http.put<DesignerIdeaDetails>(this.resourceUrl, copy, { observe: 'response' })
                .map((res: EntityResponseType) => this.convertResponse(res));
        }
        return forkJoin(...filePostArr).concatMap((results: FileCallbackModel[]) => {
            results.forEach((element: FileCallbackModel) => {
                copy[element.key] = element.fileName;
                copy[element.key + 'Url'] = element.fileUrl;
            });
            return this.http.put<DesignerIdeaDetails>(this.resourceUrl, copy, { observe: 'response' });
        }).map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DesignerIdeaDetails>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DesignerIdeaDetails[]>> {
        const options = createRequestOption(req);
        return this.http.get<DesignerIdeaDetails[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DesignerIdeaDetails[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<DesignerIdeaDetails[]>> {
        const options = createRequestOption(req);
        return this.http.get<DesignerIdeaDetails[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DesignerIdeaDetails[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DesignerIdeaDetails = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DesignerIdeaDetails[]>): HttpResponse<DesignerIdeaDetails[]> {
        const jsonResponse: DesignerIdeaDetails[] = res.body;
        const body: DesignerIdeaDetails[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DesignerIdeaDetails.
     */
    private convertItemFromServer(designerIdeaDetails: DesignerIdeaDetails): DesignerIdeaDetails {
        const copy: DesignerIdeaDetails = Object.assign({}, designerIdeaDetails);
        copy.shareTime = this.dateUtils
            .convertLocalDateFromServer(designerIdeaDetails.shareTime);
        for (const key in copy) {
            if (key.endsWith('Url') && copy[key]) {
               copy[key] = STATIC_SERVER_URL + '/' + copy[key];
            }
        }
        return copy;
    }

    /**
     * Convert a DesignerIdeaDetails to a JSON which can be sent to the server.
     */
    private convert(designerIdeaDetails: DesignerIdeaDetails): DesignerIdeaDetails {
        const copy: DesignerIdeaDetails = Object.assign({}, designerIdeaDetails);
        copy.shareTime = this.dateUtils
            .convertLocalDateToServer(designerIdeaDetails.shareTime);
        return copy;
    }
}
