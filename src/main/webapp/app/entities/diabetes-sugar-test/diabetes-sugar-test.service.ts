import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { DiabetesSugarTest } from './diabetes-sugar-test.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DiabetesSugarTest>;

@Injectable()
export class DiabetesSugarTestService {

    private resourceUrl =  SERVER_API_URL + 'api/diabetes-sugar-tests';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(diabetesSugarTest: DiabetesSugarTest): Observable<EntityResponseType> {
        const copy = this.convert(diabetesSugarTest);
        return this.http.post<DiabetesSugarTest>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(diabetesSugarTest: DiabetesSugarTest): Observable<EntityResponseType> {
        const copy = this.convert(diabetesSugarTest);
        return this.http.put<DiabetesSugarTest>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<DiabetesSugarTest>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DiabetesSugarTest[]>> {
        const options = createRequestOption(req);
        return this.http.get<DiabetesSugarTest[]>(`${this.resourceUrl}/byUserid/`, { params: options, observe: 'response' })
            .map((res: HttpResponse<DiabetesSugarTest[]>) => this.convertArrayResponse(res));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DiabetesSugarTest = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DiabetesSugarTest[]>): HttpResponse<DiabetesSugarTest[]> {
        const jsonResponse: DiabetesSugarTest[] = res.body;
        const body: DiabetesSugarTest[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DiabetesSugarTest.
     */
    private convertItemFromServer(diabetesSugarTest: DiabetesSugarTest): DiabetesSugarTest {
        const copy: DiabetesSugarTest = Object.assign({}, diabetesSugarTest);
        copy.measurmentdate = this.dateUtils
            .convertDateTimeFromServer(diabetesSugarTest.measurmentdate);
        return copy;
    }

    /**
     * Convert a DiabetesSugarTest to a JSON which can be sent to the server.
     */
    private convert(diabetesSugarTest: DiabetesSugarTest): DiabetesSugarTest {
        const copy: DiabetesSugarTest = Object.assign({}, diabetesSugarTest);
        return copy;
    }
}
