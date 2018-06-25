import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { BloodTest } from './blood-test.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<BloodTest>;

@Injectable()
export class BloodTestService {

    private resourceUrl =  SERVER_API_URL + 'api/blood-tests';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(bloodTest: BloodTest): Observable<EntityResponseType> {
        const copy = this.convert(bloodTest);
        return this.http.post<BloodTest>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(bloodTest: BloodTest): Observable<EntityResponseType> {
        const copy = this.convert(bloodTest);
        return this.http.put<BloodTest>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<BloodTest>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<BloodTest[]>> {
        const options = createRequestOption(req);
        return this.http.get<BloodTest[]>(`${this.resourceUrl}/byUserid/`, { params: options, observe: 'response' })
            .map((res: HttpResponse<BloodTest[]>) => this.convertArrayResponse(res));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: BloodTest = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<BloodTest[]>): HttpResponse<BloodTest[]> {
        const jsonResponse: BloodTest[] = res.body;
        const body: BloodTest[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to BloodTest.
     */
    private convertItemFromServer(bloodTest: BloodTest): BloodTest {
        const copy: BloodTest = Object.assign({}, bloodTest);
        copy.measurmentdate = this.dateUtils
            .convertDateTimeFromServer(bloodTest.measurmentdate);
        return copy;
    }

    /**
     * Convert a BloodTest to a JSON which can be sent to the server.
     */
    private convert(bloodTest: BloodTest): BloodTest {
        const copy: BloodTest = Object.assign({}, bloodTest);
        return copy;
    }
}
